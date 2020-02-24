from __future__ import unicode_literals
import frappe
from frappe.model.naming import set_name_by_naming_series
from frappe import _, msgprint, throw
import frappe.defaults
from frappe.utils import flt, cint, cstr, today
from frappe.desk.reportview import build_match_conditions, get_filters_cond
from erpnext.utilities.transaction_base import TransactionBase
from erpnext.accounts.party import validate_party_accounts, get_dashboard_info, get_timeline_data # keep this
from frappe.contacts.address_and_contact import load_address_and_contact, delete_contact_and_address
from frappe.model.rename_doc import update_linked_doctypes
from frappe.model.document import Document
import json
import math
import sys
from strawberi.tapiexception import travel_user_exception
from strawberi.taplogs import travel_logging, getlogger
from strawberi import tapiconstants
from strawberi import tapiutils
import requests

logger = getlogger("customer.py")

class api(Document):
	pass

@frappe.whitelist()
def get_customer_details(customer_name,company):
	parent_contact = frappe.db.sql("""select parent from `tabDynamic Link` where link_doctype="Customer" AND parenttype="Contact"  AND  link_name=%s""",(customer_name),as_dict=1)
	parent_address = frappe.db.sql("""select parent from `tabDynamic Link` where link_doctype="Customer" AND parenttype="Address"  AND  link_name=%s""",(customer_name),as_dict=1)
	customer_details=frappe.db.sql("""select * from `tabCustomer` where name=%s""",(customer_name),as_dict=1)
	contact=[]
	for row in parent_contact:
		contact_details=frappe.db.sql("""select * from `tabContact` where name=%s""",(row.parent),as_dict=1)
		if contact_details:
			contact_value = {"first_name":contact_details[0]["first_name"],"middle_name":contact_details[0]["middle_name"],"last_name":contact_details[0]["last_name"],"contact_phone":contact_details[0]["phone"],"mobile":contact_details[0]["mobile_no"], "email_id":contact_details[0]["email_id"],"status":contact_details[0]["status"],"salutation":contact_details[0]["salutation"],"designation":contact_details[0]["designation"],"gender":contact_details[0]["gender"],"alternate_phone_numbers":contact_details[0]["alternate_phone_numbers"]}
			contact.append(contact_value)
	address=[]
	for row in parent_address:
		address_details=frappe.db.sql("""select * from `tabAddress` where name=%s""",(row.parent),as_dict=1)
		if address_details:
			address_value ={"address_title":address_details[0]["address_title"],"city":address_details[0]["city"], "state":address_details[0]["state"],"country":address_details[0]["country"], "zip_code":address_details[0]["pincode"],"address_line1":address_details[0]["address_line1"],"adress_line2":address_details[0]["address_line2"],"email_id":address_details[0]["email_id"],"phone":address_details[0]["phone"],"fax":address_details[0]["fax"],"tax_category":address_details[0]["tax_category"],"party_gstin":address_details[0]["gstin"],"gst_state":address_details[0]["gst_state"]}
			address.append(address_value)

	kyc_details=frappe.db.sql("""select * from `tabKYC Document` where parent=%s""",(customer_name),as_dict=1)
	customer_kyc_details=[]
	for row in kyc_details:
		
		doc_details={"document_type":row.type,"number":row.number,"status":row.status,"document_url":row.document_url}
		customer_kyc_details.append(doc_details)

	customer_details_value=[]
	
	for customer in customer_details:
		customer_details_value = {"customer_id":customer.name,"type":customer.customer_type, "first_name":customer.first_name,"middle_name":customer.middle_name,"last_name":customer.last_name,"date_of_birth":customer.date_of_birth,     "status":customer.status,"marital_status":customer.marital_status, "date_of_marriage":customer.marriage__date, "profession":customer.profession,"contact":contact,"address":address,"kyc_details":customer_kyc_details}
	
	return customer_details_value

@frappe.whitelist()
def get_customer_list():
	total_count = 0
	customer_list=[]
	customer_details_List={}
	
	try:
		reqData = json.loads(frappe.request.data)
		page_limit = (str(reqData.get("page_limit", "0"))).strip()
		page_num = (str(reqData.get("page_num", "0"))).strip()
		sort_by = (str(reqData.get("sort_by",""))).strip()
		sort_by_str = get_sort_str(sort_by)
		
		page_no = tapiutils.validate_convert_int(page_num, "'Page Number'", "customer.py", "get_customer_list")
		int_page_limit = tapiutils.validate_convert_int(page_limit, "'Page Limit'", "customer.py", "get_customer_list")
		if (page_no <= 0):
			page_no = tapiconstants.DEFAULT_PAGE_NUMBER
		if (int_page_limit <= 0):
			int_page_limit = tapiconstants.DEFAULT_PAGE_LIMIT
		offset =  (page_no - 1) * int_page_limit
		
		if (page_no == 1):
			count_result = tapiutils.execute_frappe_select_sql("""select count(*) AS rec_count from `tabCustomer`""",(),"customer.py", "get_customer_list")
			total_count = count_result[0].rec_count
		
		sql_str = "select * from `tabCustomer`" + sort_by_str + " LIMIT %s OFFSET %s"
		customer_details = tapiutils.execute_frappe_select_sql(sql_str, (int_page_limit, offset), "customer.py", "get_customer_list")
		
		total_pages = math.ceil(total_count / int_page_limit)
		for customer in customer_details:
			customer_list_value = {"customer_id":customer.name,"customer_name":customer.name,"type":customer.customer_type, "first_name":customer.first_name,
								"middle_name":customer.middle_name,"last_name":customer.last_name,"date_of_birth":customer.date_of_birth, 
								"status":customer.status,"marital_status":customer.marital_status, "date_of_marriage":customer.marriage__date, "profession":customer.profession}
			customer_list.append(customer_list_value)
		
		customer_details_List = {"cur_page":page_no,"total_count":total_count,"total_page":total_pages,"customer_details":customer_list}
	except travel_user_exception as e:
		logger.exception(e.error_category + ": "+ e.error_code + ": "  + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": "+ tapiconstants.ERR_CODE_500 + ": "  + tapiconstants.ERR_INFO_500 
						+ " DESCRIPTION : Runtime Exception in get_customer_list")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	return customer_details_List

@frappe.whitelist()
def search_customers():
	customer_details={}
	total_count = 0
	
	try:
		reqData = json.loads(frappe.request.data)
		search_str = (str(reqData.get("search_str",""))).strip()
		page_limit = (str(reqData.get("page_limit", "0"))).strip()
		page_num = (str(reqData.get("page_num", "0"))).strip()
		sort_by = (str(reqData.get("sort_by",""))).strip()
		sort_by_str = get_sort_str(sort_by)
		
		tapiutils.validate_empty_string(search_str, "'search_str'", "customer.py", "search_customers")
		page_no = tapiutils.validate_convert_int(page_num, "'page_num'", "customer.py", "search_customers")
		int_page_limit = tapiutils.validate_convert_int(page_limit, "'page_limit'", "customer.py", "search_customers")
		
		if (page_no <= 0):
			page_no = tapiconstants.DEFAULT_PAGE_NUMBER
		if (int_page_limit <= 0):
			int_page_limit = tapiconstants.DEFAULT_PAGE_LIMIT
		offset =  (page_no - 1) * int_page_limit
		search_str = "%" + search_str + "%"
		
		if (page_no == 1):
			count_result = tapiutils.execute_frappe_select_sql("""select count(*) AS rec_count from `tabCustomer` where customer_name like %s OR mobile_no like %s OR email_id like %s""", 
															(search_str, search_str, search_str),"customer.py", "search_customers")
			total_count = count_result[0].rec_count
		
		sql_str = "select * from `tabCustomer` where customer_name like %s OR mobile_no like %s OR email_id like %s" + sort_by_str + " LIMIT %s OFFSET %s"
		customers = tapiutils.execute_frappe_select_sql(sql_str, (search_str, search_str, search_str, int_page_limit, offset), "customer.py", "search_customers")
		
		total_pages = math.ceil(total_count / int_page_limit)
		customer_list = populate_customer_attributes(customers);
		customer_details = {"cur_page":page_no,"total_count":total_count,"total_page":total_pages,"customer_details":customer_list}
	except travel_user_exception as e:
		logger.exception(e.error_category + ": "+ e.error_code + ": "  + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": "+ tapiconstants.ERR_CODE_500 + ": "  + tapiconstants.ERR_INFO_500 
						+ " DESCRIPTION : Runtime Exception in search_customers")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	return customer_details

def populate_customer_attributes(customers):
	customer_list = []
	for customer in customers:
		customer_details = {"customer_id":customer.name,"customer_name":customer.customer_name,"type":customer.customer_type, "first_name":customer.first_name,
							"middle_name":customer.middle_name,"last_name":customer.last_name,"date_of_birth":customer.date_of_birth, 
							"status":customer.status,"marital_status":customer.marital_status, "date_of_marriage":customer.marriage__date, 
							"profession":customer.profession, "mobile_number":customer.mobile_no, "email_id":customer.email_id}
		customer_list.append(customer_details)
	return customer_list


def get_sort_str(sort_by):
	sort_str=""
	sort_list = sort_by.split("-")
	
	if (len(sort_list) >= 2):
		sort_by_str = sort_list[0].strip().lower()
		sort_by_order = sort_list[1].strip().lower()
	elif (len(sort_list) == 1):
		sort_by_str = sort_by.lower()
		sort_by_order = "new"
	
	if not (sort_by_str):
		sort_by_str = "create"
	if not (sort_by_order):
		sort_by_order = "new"
	
	if (sort_by_str == "create"):
		if (sort_by_order == "new"):
			sort_str=" order by creation desc"
		elif (sort_by_order == "old"):
			sort_str=" order by creation asc"
	
	return sort_str