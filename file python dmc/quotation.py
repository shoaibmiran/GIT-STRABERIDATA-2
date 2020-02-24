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
import requests
from frappe.utils.error import make_error_snapshot
import json
import sys
from frappe import utils
from strawberi import tapexception, apidev
from strawberi.tapiexception import travel_user_exception
from strawberi.taplogs import travel_logging, getlogger
from strawberi import tapiconstants
from strawberi import tapiutils
from datetime import datetime
import math
import pdfkit
import codecs
import webbrowser
import urllib.request
import requests
import socket
import datetime
logger = getlogger("quotation.py")

class quotation(Document):
	pass


@frappe.whitelist()
def create_rfq_along_with_bundle():
	try:

		#print ("--fiscal year----------------------",frappe.defaults.get_user_default("fiscal_year"))
		#print ("--fiscal year----------------------",frappe.defaults.get_user_default("year_start_date"))
		#print ("--fiscal year----------------------",frappe.defaults.get_user_default("year_end_date"))
		reqData = json.loads(frappe.request.data)
		company = "Strawberi"
		transaction_date = utils.today()
		notes = reqData.get("notes")
		price_category = reqData.get("price_category")
		customer_name = reqData.get("customer_name")
		sales_person = reqData.get("sales_person")
		traveling_on = reqData.get("traveling_on")
		travelling_end_date = reqData.get("travelling_end_date")
		number_of_children = reqData.get("number_of_children")
		number_of_adult = reqData.get("number_of_adult")
		number_of_infant = reqData.get("number_of_infant")
		message_for_supplier = reqData.get("message_for_supplier")
		fiscal_year = reqData.get("fiscal_year")
		inquiry_id = reqData.get("inquiry_id")
		status = "Draft"
		docstatus = 0
		suppliers = reqData.get("suppliers")

		parent_product_bundle = reqData.get("parent_product_bundle")
		items = reqData.get("items")
		RFQ_ID = reqData.get("RFQ_ID")
		if RFQ_ID:
			product_bundle=""
			#print ("hello i am updated")
			doc = frappe.get_doc("Request for Quotation", RFQ_ID)

			doc.db_set('traveling_on', traveling_on)

			doc.db_set('company', company)
			doc.db_set('notes', notes)
			doc.db_set('transaction_date', transaction_date)
			doc.db_set('price_category', price_category)
			doc.db_set('customer_name', customer_name)
			doc.db_set('number_of_children', number_of_children)
			doc.db_set('number_of_adult', number_of_adult)
			doc.db_set('number_of_infant', number_of_infant)
			doc.db_set('message_for_supplier', message_for_supplier)
			doc.db_set('fiscal_year', fiscal_year)
			doc.db_set('sales_person', sales_person)
			doc.db_set('travelling_end_date', travelling_end_date)
			doc.db_set('opportunity', inquiry_id)
			for sup in doc.suppliers:
				sup.supplier=suppliers[0]["supplier"]
			for item in doc.items:
				#print ("hello i am updated",item.item_code)
				product_bundle=item.item_code
			# save a document to the database
			doc.save()
			if items:
				pd = frappe.get_doc("Product Bundle", product_bundle)
				pd.delete()
				itinerary_detail=[]
				product_bundle_item=[]
				for item in items:
					for itinerary in item["Itinerary"]:
						for product in itinerary["Products"]:
							itinerary_value={"day":item["Day"],"start_date":item["Start_Date"],"end_date":item["End_Date"],"category":itinerary["Category"],"place":product["Place"],
	"product":product["Product_ID"],"qty":product["qty"],"title":product["Title"], "description":product["Description"]}
							itinerary_detail.append(itinerary_value)

							if product["Product_ID"]:
								pb_item={"item_code":product["Product_ID"],"qty":product["qty"],"category":itinerary["Category"],
						"location":product["Place"],"description":""}
								product_bundle_item.append(pb_item)
				product_bundle_json = {
						 "new_item_code": product_bundle,
	   					 "description":"" ,
	   					 "id": "",
	   					 "parent_product_bundle":parent_product_bundle,
	   					 "package_type":"Standard",
	   					 "items": product_bundle_item,
						 "itinerary":itinerary_detail
					      }
				doc = frappe.new_doc("Product Bundle")
				doc.update(product_bundle_json)
				doc.save()
			return "Product bundle and request for quotation has been Updated successfully"
		else:

			item_Details = frappe.db.sql("""select * from `tabItem` where item_code=%s""",(parent_product_bundle),as_dict=1)
			if item_Details:
				item_group=item_Details[0]["item_group"]

			parent_product_bundle_item = parent_product_bundle + " FIT%"
			#print(parent_product_bundle_item)
			last_child_item = frappe.db.sql("""select * from `tabItem` where item_code like %s order by name desc limit 1""",(parent_product_bundle_item),as_dict=1)
			if last_child_item:
				item_last_value=last_child_item[0]["name"][-3:]
				#print(item_last_value)
				try:
					int_item_last_value = int(item_last_value) + 1
					pb_item_code=parent_product_bundle+" FIT-"+str('%03d' % int_item_last_value )
				except ValueError as ve:
					pb_item_code=parent_product_bundle+" FIT-001"
			else:
				pb_item_code=parent_product_bundle+" FIT-001"


			product_bundle_name=""
			#item_code = frappe.db.sql("""select name from `tabProduct Bundle` where name like "MAJESTIC SIKKIM FIT-%%%" order by name desc;""",(product_bundle_name),as_dict=1)
			item_code = frappe.db.sql("""select name from `tabProduct Bundle` where parent_product_bundle=%s order by name desc limit 1""",(parent_product_bundle),as_dict=1)
			if item_code:
				pd_last_value=item_code[0]["name"][-3:]
				pd_last_value=int(pd_last_value)+1
				product_bundle_name=parent_product_bundle+" FIT-"+str('%03d' % pd_last_value )
			else:
				product_bundle_name=parent_product_bundle+" FIT-001"

			item_json={
	    				"item_code": pb_item_code,
	    				"item_name": pb_item_code,
	    				"item_group": item_group,
	   					"stock_uom": "Nos",
	   					"category": "Package",
	    				"is_stock_item": 0,
	   			        "include_item_in_manufacturing": 0,
	    				"price_category": "Budget",
	     				"item_defaults": [{
	      							"company": company
	   						 }]
	  				}
			doc = frappe.new_doc("Item")
			doc.update(item_json)
			doc.save()
			itinerary_detail=[]
			product_bundle_item=[]
			for item in items:
				for itinerary in item["Itinerary"]:
					for product in itinerary["Products"]:
						itinerary_value={"day":item["Day"],"start_date":item["Start_Date"],"end_date":item["End_Date"],"category":itinerary["Category"],"place":product["Place"],
	"product":product["Product_ID"],"qty":product["qty"],"title":product["Title"], "description":product["Description"]}
						itinerary_detail.append(itinerary_value)
						if product["Product_ID"]:
							pb_item={"item_code":product["Product_ID"],"qty":product["qty"],"category":itinerary["Category"],
						"location":product["Place"],"description":""}
							product_bundle_item.append(pb_item)
			product_bundle_json = {
						 "new_item_code": product_bundle_name,
	   					 "description":"" ,
	   					 "id": "",
	   					 "parent_product_bundle":parent_product_bundle,
	   					 "package_type":"Standard",
	   					 "items": product_bundle_item,
						 "itinerary":itinerary_detail
					      }
			doc = frappe.new_doc("Product Bundle")
			doc.update(product_bundle_json)
			doc.save()

			req_json={
					"company":company ,
					"transaction_date":transaction_date,
					"price_category": price_category,
					"customer_name": customer_name,
					"traveling_on": traveling_on,
					"travelling_end_date":travelling_end_date,
					"number_of_children":number_of_children,
					"number_of_adult": number_of_adult,
					"number_of_infant": number_of_infant,
	       			"message_for_supplier":message_for_supplier,
					"fiscal_year": fiscal_year,
					"sales_person":sales_person,
					"status": status,
					"docstatus":docstatus,
					"suppliers": suppliers,
					"notes":notes,
					"opportunity":inquiry_id,
					"items": [{
							"item_code": product_bundle_name,
							"item_name": product_bundle_name,
							"qty": 1.0,
		      				"uom": "Nos",
		      				"description":product_bundle_name
		      				}]
		      		}
			doc = frappe.new_doc("Request for Quotation")
			doc.update(req_json)
			doc.save()
			returnStr = {"RFQ_ID":doc.name}
			return returnStr
			#return "Product bundle and request for quotation has been created successfully"

	except Exception as e:
		#make_error_snapshot(e)
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in create_rfq_along_with_bundle")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json



@frappe.whitelist()
def get_rfq_list():
	try:

		rfq_detail_list = []
		reqData = json.loads(frappe.request.data)
		entities = reqData.get("entities", [])
		search_str = (str(reqData.get("search_str",""))).strip()
		status = (str(reqData.get("status", ""))).strip()
		str_start_date = (str(reqData.get("start_date", ""))).strip()
		str_end_date = (str(reqData.get("end_date", ""))).strip()
		page_limit = (str(reqData.get("page_limit", "0"))).strip()
		page_num =(str( reqData.get("page_num", "0"))).strip()
		sort_by = (str(reqData.get("sort_by",""))).strip()

		int_page_num = tapiutils.validate_convert_int(page_num, "'Page Number'", "quotation.py", "get_rfq_list")
		int_page_limit = tapiutils.validate_convert_int(page_limit, "'Page Limit'", "quotation.py", "get_rfq_list")
		fmt_start_date = tapiutils.validate_convert_date(str_start_date, "%Y-%m-%d", "%Y-%m-%d", "'start_date'", "quotation.py", "get_rfq_list")
		fmt_end_date = tapiutils.validate_convert_date(str_end_date, "%Y-%m-%d", "%Y-%m-%d", "'end_date'", "quotation.py", "get_rfq_list")

		if (int_page_num <= 0):
			int_page_num = tapiconstants.DEFAULT_PAGE_NUMBER
		if (int_page_limit <= 0):
			int_page_limit = tapiconstants.DEFAULT_PAGE_LIMIT
		total_count = 0

		if (int_page_num == 1):
			quotations = fetch_quotations(True, "quotation",search_str, entities,status, None, fmt_start_date, fmt_end_date, sort_by,int_page_limit, int_page_num)
			total_count = quotations[0].rec_count
		total_pages = math.ceil(total_count / int_page_limit)
		rfq_list = fetch_quotations(False, "quotation", search_str, entities, status, None, fmt_start_date, fmt_end_date, sort_by,int_page_limit, int_page_num)
		for rfq in rfq_list:
			supplier = get_rfq_supplier(rfq.name)
			branch = get_branch_for_sp(rfq.sales_person)
			package = get_rfq_package(rfq.name)
			rfq_detail = {"rfq_id":rfq.name, "supplier":supplier, "customer":rfq.customer_name, "inquiry_id":rfq.opportunity,"sales_person":rfq.sales_person,
						"traveling_on":rfq.traveling_on,"travelling_end_date":rfq.travelling_end_date,"number_of_children":rfq.number_of_children,"number_of_adult":rfq.number_of_adult,
						"number_of_infant":rfq.number_of_infant,"package":package, "status":rfq.status, "creation_date":rfq.creation, "branch":branch}
			rfq_detail_list.append(rfq_detail)

		quotation_details = {"cur_page":int_page_num,"total_count":total_count,"total_page":total_pages,"rfq_list":rfq_detail_list}

	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in get_rfq_list")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	return quotation_details

@frappe.whitelist()
def create_update_quotation():
	try:
		message = ""
		reqData = json.loads(frappe.request.data)
		validate_request(reqData)

		quotation_type = (str(reqData.get("for",""))).strip()
		if (quotation_type.lower() == "supplier"):
			doc_type = "Supplier Quotation"
		elif (quotation_type.lower() == "customer"):
			doc_type = "Quotation"
		else:
			raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_503, tapiconstants.ERR_INFO_503 + " for " + quotation_type,
									"Invalid doc type(for) in create_update_quotation. For should be supplier / customer " + quotation_type, None)

		details = reqData.get("details")
		quotation_id = details["quotation_id"]
		if (quotation_id):
			if (update_quotation(doc_type, reqData)):
				message = "Update Quotation Successful"
		else:
			#if(create_quotation(doc_type, reqData)):
				#message = "Create Quotation Successful"
			created_docIds = create_quotation(doc_type, reqData)
			if created_docIds:
				message = {"Quotation_ID":created_docIds}

	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in create_update_quotation")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return message

@frappe.whitelist()
def get_quotation_details():
	quotation_details = {}

	try:
		items_list =[]
		tax_list = []
		offer_list=[]

		reqData = json.loads(frappe.request.data)

		quotation_type = (str(reqData.get("for",""))).strip()
		tapiutils.validate_empty_string(quotation_type, "'for'", "quotation.py", "get_quotation_details")

		if (quotation_type.lower() == "supplier"):
			doc_type = "Supplier Quotation"
		elif (quotation_type.lower() == "customer"):
			doc_type = "Quotation"
		else:
			raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_503, tapiconstants.ERR_INFO_503 + "'for' :" + quotation_type,
									"Invalid doc type(for) in get_quotation_details. For should be supplier / customer " + quotation_type, None)

		quotation_id = reqData.get("quotation_id")
		tapiutils.validate_empty_string(quotation_id, "'quotation_id'", "quotation.py", "get_quotation_details")

		doc = frappe.get_doc(doc_type, quotation_id)

		for item in doc.items:
			item_details = {"item_code":item.item_code,"item_name":item.item_name,"qty":item.qty,"rate":item.rate,
						"amount":item.amount,"description":item.description}
			items_list.append(item_details)

		for offer in doc.offer:
			offer_details = {"offer":offer.offer, "included":offer.included}
			offer_list.append(offer_details)

		for tax_item in doc.taxes:
			tax_details = {"tax_type":tax_item.charge_type, "tax_rate":tax_item.rate, "amount":tax_item.tax_amount}
			tax_list.append(tax_details)

		if (quotation_type.lower()=="customer"):
			customer_name = doc.party_name
		elif (quotation_type.lower()=="supplier"):
			customer_name = doc.customer
		quotation_details = {"for":quotation_type,"rfq_id":doc.request_for_quotation,
							"details": {"quotation_id":quotation_id,"company":doc.company,"price_category":doc.price_category,
							"customer_id":customer_name,"travelling_on":doc.travelling_on,"travelling_end_date":doc.travelling_end_date,"number_of_children":doc.number_of_children,
							"number_of_adult":doc.number_of_adult,"number_of_infant":doc.number_of_infant,"supplier":doc.supplier,"inquiry_id":doc.opportunity,
							"items":items_list,"total":doc.total,"taxes":tax_list,"tax_total":doc.total_taxes_and_charges,
							"grand_total":doc.grand_total,"offers":offer_list,"notes":doc.notes,"creation_date":doc.creation}}

	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in create_update_quotation")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return quotation_details

@frappe.whitelist()
def get_rfq_details():
	rfq_details = {}

	try:
		suppliers =[]
		items_list = []
		itinerary_list=[]
		product_list=[]
		parent_product_bundle=""
		all_catagories = ["Hotel", "Sightseeing", "Transfer"]

		reqData = json.loads(frappe.request.data)
		rfq_id = (str(reqData.get("rfq_id",""))).strip()
		tapiutils.validate_empty_string(rfq_id, "'rfq_id'", "quotation.py", "get_rfq_details")

		doc = frappe.get_doc("Request for Quotation", rfq_id)

		for sup in doc.suppliers:
			sup_details = {"supplier":sup.supplier}
			suppliers.append(sup_details)

		for item in doc.items:
			product_bundle_id = item.item_code
			itin_days = tapiutils.execute_frappe_select_sql("""select distinct(day) from `tabItinerary  Details` where parent=%s and parenttype=%s order by day""",
									(product_bundle_id, "Product Bundle"), "quotation.py", "get_rfq_details")

			for itin_day in itin_days:
				itinerary_list=[]
				for cat in all_catagories:
					day_products = tapiutils.execute_frappe_select_sql("""select * from `tabItinerary  Details` where parent=%s and parenttype=%s and day=%s and category=%s""",
																	(product_bundle_id, "Product Bundle", itin_day.day, cat), "quotation.py", "get_rfq_details")
					product_list=[]
					for product in day_products:
						itin_start_date = product.start_date
						itin_end_date = product.end_date
						product_details = {"Place":product.place,"Product_ID":product.product,"Qty":product.qty, "Title":product.title, "Description":product.description}
						product_list.append(product_details)
					if (len(product_list) > 0):
						category_details = {"Category":cat, "Products":product_list}
						itinerary_list.append(category_details)

				day_details = {"Day":itin_day.day, "Start_Date":itin_start_date, "End_Date":itin_end_date, "Itinerary":itinerary_list}
				items_list.append(day_details)

		parent_product_bundle = get_parent_bundle_id(product_bundle_id)
		rfq_details = {"rfq_id":doc.name,"price_category":doc.price_category,"sales_person":doc.sales_person,"inquiry_id":doc.opportunity,"customer_name":doc.customer_name,
							"traveling_on":doc.traveling_on,"travelling_end_date":doc.travelling_end_date,"number_of_children":doc.number_of_children,
							"number_of_adult":doc.number_of_adult,
							"number_of_infant":doc.number_of_infant,"message_for_supplier":doc.message_for_supplier,"fiscal_year":doc.fiscal_year,
							"suppliers":suppliers,"notes":doc.notes,"bundle_id":product_bundle_id, "parent_product_bundle":parent_product_bundle,"Items":items_list}

	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in get_rfq_details")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return rfq_details

@frappe.whitelist()
def get_quotation_list():
	try:
		quotation_list = []
		fmt_start_date = ""
		fmt_end_date = ""

		reqData = json.loads(frappe.request.data)
		quotation_type = (str(reqData.get("for", ""))).strip()
		search_str = (str(reqData.get("search_str", ""))).strip()
		status = (str(reqData.get("status", ""))).strip()
		rfq_id = (str(reqData.get("rfq_id", ""))).strip()
		entities = reqData.get("entities", [])
		str_start_date = (str(reqData.get("start_date", ""))).strip()
		str_end_date = (str(reqData.get("end_date", ""))).strip()
		page_limit = (str(reqData.get("page_limit", "0"))).strip()
		page_num = (str(reqData.get("page_num", "0"))).strip()
		sort_by = (str(reqData.get("sort_by",""))).strip()

		int_page_num = tapiutils.validate_convert_int(page_num, "'Page Number'", "quotation.py", "get_quotation_list")
		int_page_limit = tapiutils.validate_convert_int(page_limit, "'Page Limit'", "quotation.py", "get_quotation_list")
		tapiutils.validate_empty_string(quotation_type, "'for'", "quotation.py", "get_quotation_list")
		if not ((quotation_type.lower() == "supplier") or (quotation_type.lower() == "customer")):
			raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_503, tapiconstants.ERR_INFO_503 + " for " + quotation_type,
									"Invalid doc type(for) in get_quotation_list. For should be supplier / customer " + quotation_type, None)

		fmt_start_date = tapiutils.validate_convert_date(str_start_date, "%Y-%m-%d", "%Y-%m-%d", "'start_date'", "quotation.py", "get_quotation_list")
		fmt_end_date = tapiutils.validate_convert_date(str_end_date, "%Y-%m-%d", "%Y-%m-%d", "'end_date'", "quotation.py", "get_quotation_list")

		if (int_page_num <= 0):
			int_page_num = tapiconstants.DEFAULT_PAGE_NUMBER
		if (int_page_limit <= 0):
			int_page_limit = tapiconstants.DEFAULT_PAGE_LIMIT
		total_count = 0
		logger.debug(rfq_id)
		if (int_page_num == 1):
			quotations = fetch_quotations(True, quotation_type, search_str, entities, status, rfq_id, fmt_start_date, fmt_end_date, sort_by,int_page_limit,int_page_num)
			total_count = quotations[0].rec_count
		total_pages = math.ceil(total_count / int_page_limit)

		quotations = fetch_quotations(False, quotation_type, search_str, entities, status, rfq_id, fmt_start_date, fmt_end_date, sort_by,int_page_limit,int_page_num)
		for quotation in quotations:
			#supplier = get_rfq_supplier(rfq.name)
			sales_person = get_sp_for_quotation(quotation.request_for_quotation)
			branch = get_branch_for_sp(sales_person)
			package = get_quotation_package(quotation_type,quotation.name)
			customer_name = ""
			if (quotation_type=="customer"):
				customer_name = quotation.party_name
			elif (quotation_type=="supplier"):
				customer_name = quotation.customer
			quotation_detail = {"for":quotation_type, "quotation_id":quotation.name, "supplier":quotation.supplier, "customer":customer_name, "rfq_id":quotation.request_for_quotation,
						"sales_person":sales_person, "traveling_on":quotation.travelling_on,"travelling_end_date":quotation.travelling_end_date,"number_of_children":quotation.number_of_children,
						"number_of_adult":quotation.number_of_adult,"number_of_infant":quotation.number_of_infant,"inquiry_id":quotation.opportunity,"package":package, "status":quotation.status,
						"creation_date":quotation.creation, "branch":branch}
			quotation_list.append(quotation_detail)

		quotation_details = {"cur_page":int_page_num,"total_count":total_count,"total_page":total_pages,"rfq_list":quotation_list}

	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in get_quotation_list")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return quotation_details

@frappe.whitelist()
def get_package_price():

	try:
		package_price = {}
		reqData = json.loads(frappe.request.data)
		bundle_id = (str(reqData.get("bundle_id", ""))).strip()
		traveling_on = (str(reqData.get("traveling_on", ""))).strip()
		currency = (str(reqData.get("currency", ""))).strip()
		package_products = reqData.get("package_products", [])
		product_list = []

		tapiutils.validate_empty_string(bundle_id, "'bundle_id'", "quotation.py", "get_package_price")
		fmt_traveling_on = tapiutils.validate_convert_date(traveling_on, "%Y-%m-%d", "%Y-%m-%d", "'traveling_on'", "quotation.py", "get_package_price")
		tapiutils.validate_empty_list(package_products, "'package_products'", "quotation.py", "get_package_price")

		logger.debug("Input Parameters bundle_id : " + bundle_id + " traveling_on:" + traveling_on + " currency:" + currency)

		if not (currency):
			currency = get_bundle_currency(bundle_id)

		hotel_list = []
		hotel_found = False
		for package_product in package_products:
			day = package_product["day"]
			itinerary_list = package_product["itinerary"]
			for item in itinerary_list:
				category = item["category"]
				logger.debug("Input Parameters category : " + category)
				if (category == "Hotel"):
					hotel_found = True
					hotel_list = item["products"]
				if (hotel_found):
					break
			if (hotel_found):
				break

		logger.debug("get_package_price:Hotel List")
		logger.debug(hotel_list)

		product_list = get_variant_product_list(bundle_id, day, hotel_list)
		logger.debug("get_package_price:Product List")
		logger.debug(product_list)

		est_price = get_estimated_price(bundle_id, traveling_on, currency, product_list)

		#est_price = get_estimated_price(bundle_id, fmt_traveling_on, currency, product_list)

		# As discussed on 27/01 -- Using bundle_id directly for calculating price instead of parent bundle Id
		#parent_bundle_id = get_parent_bundle_id(bundle_id)
		#if (parent_bundle_id):
		#	est_price = get_estimated_price(parent_bundle_id, fmt_traveling_on, currency, package_products)
		#else:
		#	logger.debug("No Parent Product Bundle")

		logger.debug("get_package_price.est_price : " + str(est_price))
		package_price = {"bundle_id":bundle_id, "currency":currency, "estimated_price":est_price}
	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in get_package_price")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return package_price

@frappe.whitelist()
def get_transaction_status():
	try:
		entity_status = {}
		reqData = json.loads(frappe.request.data)
		entity_type = (str(reqData.get("entity_type", ""))).strip()
		entity_id = (str(reqData.get("entity_id", ""))).strip()

		tapiutils.validate_empty_string(entity_type, "'entity_type'", "quotation.py", "get_transaction_status")
		tapiutils.validate_empty_string(entity_id, "'entity_id'", "quotation.py", "get_transaction_status")

		entity_status = get_entity_status(entity_type, entity_id)

	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
		                +" DESCRIPTION : Runtime Exception in get_quotation_list")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return entity_status

@frappe.whitelist()
def update_transaction_status():
	try:
		reqData = json.loads(frappe.request.data)
		entity_type = (str(reqData.get("entity_type", ""))).strip()
		entity_id = (str(reqData.get("entity_id", ""))).strip()
		tap_main_status = (str(reqData.get("tap_main_status", ""))).strip()
		tap_sub_status = (str(reqData.get("tap_sub_status", ""))).strip()

		tapiutils.validate_empty_string(entity_type, "'entity_type'", "quotation.py", "update_transaction_status")
		tapiutils.validate_empty_string(entity_id, "'entity_id'", "quotation.py", "update_transaction_status")
		tapiutils.validate_empty_string(tap_main_status, "'tap_main_status'", "quotation.py", "update_transaction_status")

		if (entity_type == "Inquiry"):
			doctype = "Opportunity"
		elif (entity_type == "RFQ"):
			doctype = "Request for Quotation"
		elif (entity_type == "Customer Quotation"):
			doctype = "Quotation"
		elif (entity_type == "Customer"):
			doctype = "Customer"
		elif (entity_type == "Supplier Quotation"):
			doctype = "Supplier Quotation"
		elif (entity_type == "Package"):
			doctype = "Product Bundle"
		logger.debug("doc type" + doctype)
		doc = frappe.get_doc(doctype, entity_id)
		if (entity_type == "Inquiry"):
			doc.db_set('tap_main_status', tap_main_status)
			doc.db_set('tap_sub_status', tap_sub_status)
			logger.debug("Opportunity Status Update Succesful")
		elif (entity_type == "Package"):
			doc.db_set('tap_status', tap_main_status)
			logger.debug("Package Status Update Succesful")
		else:
			logger.debug("Quotation Status Update Succesful")
			doc.db_set('status', tap_main_status)

		doc.save()
		message = "Update Status Successful"
	except travel_user_exception as e:
		logger.exception(e.error_category + ": " + e.error_code + ": " + e.error_msg + " DESCRIPTION :" + e.error_desc)
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"Error", "Error_code":e.error_code, "Error Message":e.error_msg, "Error Type":e.error_category, "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json
	except Exception as e:
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
		                +" DESCRIPTION : Runtime Exception in update_transaction_status")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json

	return message


def get_parent_bundle_id(bundle_id):
	bundles = tapiutils.execute_frappe_select_sql("""select parent_product_bundle from `tabProduct Bundle` where new_item_code=%s""",
												(bundle_id), "quotation.py", "get_product_price")
	parent_product_bundle = ""
	for bundle in bundles:
		parent_product_bundle = bundle.parent_product_bundle
		break
	return parent_product_bundle

def get_bundle_currency(bundle_id):
	bundles = tapiutils.execute_frappe_select_sql("""select currency from `tabProduct Bundle Attribute` where product_bundle=%s""",
												(bundle_id), "quotation.py", "get_product_price")
	currency = ""
	for bundle in bundles:
		currency = bundle.currency
		break
	return currency

def get_estimated_price(bundle_id, traveling_on, currency, product_list):
	est_price = 0.0
	logger.debug(product_list)
	for product in product_list:
		product_id = product["product_id"]
		qty = product["qty"]
		logger.debug("Input Parameters product_id:" + product_id + " Qty:" + str(qty))
		product_price = get_product_price(bundle_id, product_id, traveling_on, currency, qty)
		est_price = est_price + product_price
	return est_price

def get_product_price(bundle_id, product_id, traveling_on, currency, qty):
	attrib_list = []
	total_product_price = 0.0
	product_variant_attribs = tapiutils.execute_frappe_select_sql("""select * from `tabItem Variant Attribute` where parent=%s""",
																(product_id), "quotation.py", "get_product_price")
	for attrib in product_variant_attribs:
		att_name = attrib.attribute
		att_value = attrib.attribute_value
		attributes = {"att_name":att_name, "att_value":att_value}
		attrib_list.append(attributes)
	logger.debug("get_product_price.ATTRIBUTE LIST : ")
	logger.debug(attrib_list)
	package_variant_id = get_package_variant(bundle_id, attrib_list)
	logger.debug("get_product_price.PACKAGE VARIANT ID:" + package_variant_id)
	price_list = get_price_list(bundle_id, package_variant_id)
	logger.debug("get_product_price.PRICE LIST:")
	logger.debug(price_list)
	product_price = get_item_price(bundle_id, price_list, traveling_on, currency)
	logger.debug("get_product_price.PRODUCT PRICE" + str(product_price))
	total_product_price = product_price * float(qty)
	return total_product_price

def get_package_variant(bundle_id, attrib_list):
	package_variant_id = ""
	value =  []
	sql_str = "select * from `tabVariants` where parent=%s"
	value.append(bundle_id)
	for attrib in attrib_list:
		att_name = attrib["att_name"].strip().lower()
		att_value = attrib["att_value"]
		if (att_name == "age group"):
			sql_str = sql_str + " AND age_group = %s"
			value.append(att_value)
		elif (att_name == "boarding type"):
			sql_str = sql_str + " AND boarding_type = %s"
			value.append(att_value)
		elif (att_name == "category"):
			sql_str = sql_str + " AND category = %s"
			value.append(att_value)
		elif (att_name == "child"):
			sql_str = sql_str + " AND child = %s"
			value.append(att_value)
		elif (att_name == "extra bed"):
			sql_str = sql_str + " AND extra_bed = %s"
			value.append(att_value)
		elif (att_name == "extra night"):
			sql_str = sql_str + " AND extra_night = %s"
			value.append(att_value)
		elif (att_name == "group size"):
			sql_str = sql_str + " AND group_size = %s"
			value.append(att_value)
		elif (att_name == "room service"):
			sql_str = sql_str + " AND room_service = %s"
			value.append(att_value)
		elif (att_name == "sharing mode"):
			sql_str = sql_str + " AND sharing_mode = %s"
			value.append(att_value)
		elif (att_name == "star rating"):
			sql_str = sql_str + " AND star_rating = %s"
			value.append(att_value)
		elif (att_name == "vehicle type"):
			sql_str = sql_str + " AND vehicle_type = %s"
			value.append(att_value)

	logger.debug("Package Variant SQL " + sql_str)
	variants = tapiutils.execute_frappe_select_sql(sql_str, tuple(value), "quotation.py", "get_package_variant")
	if (variants and len(variants) > 0):
		package_variant_id = variants[0].package_variant_id
		logger.debug("Package Variant ID Found" + package_variant_id)
	else:
		logger.debug("No Package Variant ID Found")
	return package_variant_id

def get_price_list(bundle_id, package_variant_id):
	price_list = []

	price_lists = tapiutils.execute_frappe_select_sql("""select * from `tabProduct Bundle Price List` where parent=%s and package_variant_id=%s""",
													(bundle_id,package_variant_id),"quotation.py", "get_price_list")
	for item in price_lists:
		price_list_1 = item.price_list_1
		if (price_list_1 is not None):
			price_list.append(price_list_1)
		price_list_2 = item.price_list_2
		if (price_list_2 is not None):
			price_list.append(price_list_2)
		price_list_3 = item.price_list_3
		if (price_list_3 is not None):
			price_list.append(price_list_3)
		price_list_4 = item.price_list_4
		if (price_list_4 is not None):
			price_list.append(price_list_4)
		price_list_5 = item.price_list_5
		if (price_list_5 is not None):
			price_list.append(price_list_5)
		break
	logger.debug("get_price_list.PRICE LIST")
	logger.debug(price_list)
	return price_list

def get_item_price(bundle_id, price_list, traveling_on, currency):
	item_price = 0.0
	price_list_str = tapiutils.list_to_string(price_list)
	item_price_list = tapiutils.execute_frappe_select_sql("""select * from `tabItem Price` where item_code=%s and currency=%s and price_list in (%s) and valid_from<=%s and (valid_upto is null OR valid_upto>=%s) order by valid_upto desc""",
														(bundle_id, currency, price_list_str, traveling_on, traveling_on),"quotation.py", "get_item_price")
	for item in item_price_list:
		item_price = item.price_list_rate
		break
	logger.debug("get_item_price.PRICE LIST RATE")
	logger.debug(item_price)
	return item_price

def get_quotation_table(doc_type):
	quotation_table = ""
	if (doc_type.lower() == "quotation"):
		quotation_table = "`tabRequest for Quotation`"
	elif (doc_type.lower() == "customer"):
		quotation_table = "`tabQuotation`"
	elif (doc_type.lower() == "supplier"):
		quotation_table = "`tabSupplier Quotation`"
	return quotation_table

def fetch_quotations(get_count, doc_type, search_str, entities, status, rfq_id, fmt_start_date, fmt_end_date, sort_by, page_limit, page_num):
	sql_str = ""
	quotation_list = []
	where_str = ""
	value = []
	offset = (page_num - 1) * page_limit
	quotation_table = get_quotation_table(doc_type)
	if (get_count):
		sql_str = "select count(*) as rec_count from " + quotation_table
	else:
		sql_str = "select *  from " + quotation_table

	if (len(search_str) > 0):
		search_str = "%" + search_str + "%"
		if (doc_type.lower() == "quotation"):
			where_str = where_str + "(customer_name like %s)"
			value.append(search_str)
		elif (doc_type.lower() == "customer"):
			where_str = where_str + "(customer like %s or party_name like %s)"
			value.append(search_str)
			value.append(search_str)
		elif (doc_type.lower() == "supplier"):
			where_str = where_str + "(customer like %s or supplier like %s)"
			value.append(search_str)
			value.append(search_str)

	for entity in entities:
		entity_type = entity["entity_type"].strip()
		entity_id = entity["entity_id"].strip()
		tapiutils.validate_empty_string(entity_type, "'entity_type'", "quotation.py", "fetch_quotations")
		tapiutils.validate_empty_string(entity_id, "'entity_id'", "quotation.py", "fetch_quotations")

		if (entity_type.lower() == "customer"):
			if (where_str):
				where_str = where_str + " AND "
			if (doc_type.lower() == "quotation"):
				where_str = where_str + "customer_name=%s"
				value.append(entity_id)
			elif (doc_type.lower() == "customer"):
				where_str = where_str + "(customer=%s or party_name=%s)"
				value.append(entity_id)
				value.append(entity_id)
			elif (doc_type.lower() == "supplier"):
				where_str = where_str + "customer=%s"
				value.append(entity_id)
		elif (entity_type.lower() == "sales_person"):
			if (where_str):
				where_str = where_str + " AND "
			if (doc_type.lower() == "quotation"):
				where_str = where_str + "sales_person=%s"
			elif (doc_type.lower() == "customer" or doc_type.lower() == "supplier"):
				where_str = where_str + "request_for_quotation in (select name from `tabRequest for Quotation` where sales_person = %s)"
			value.append(entity_id)
		elif (entity_type.lower() == "opportunity"):
			if (where_str):
				where_str = where_str + " AND "
			where_str = where_str + "opportunity=%s"
			value.append(entity_id)
		elif (entity_type.lower() == "supplier"):
			if (where_str):
				where_str = where_str + " AND "
			if (doc_type.lower() == "quotation"):
				where_str = where_str + "name in (select parent from `tabRequest for Quotation Supplier` where parenttype = 'Request For Quotation' and supplier_name = %s)"
				value.append(entity_id)
			elif (doc_type.lower() == "customer" or doc_type.lower() == "supplier"):
				where_str = where_str + "supplier=%s"
				value.append(entity_id)

	if (status):
		if (where_str):
			where_str = where_str + " AND "
		where_str = where_str + "status = %s"
		value.append(status)

	if (rfq_id):
		if (where_str):
			where_str = where_str + " AND "
		where_str = where_str + "request_for_quotation = %s"
		value.append(rfq_id)

	if (fmt_start_date):
		if (where_str):
			where_str = where_str + " AND "
		where_str = where_str + "creation >= %s"
		value.append(fmt_start_date)
	if (fmt_end_date):
		if (where_str):
			where_str = where_str + " AND "
		where_str = where_str + "creation <= %s"
		value.append(fmt_end_date)

	sort_str = get_quotation_sort_str(sort_by)

	if (sql_str):
		if (where_str):
			where_str = " where " + where_str
		if (get_count):
			sql_str = sql_str + where_str
		else:
			sql_str = sql_str + where_str + sort_str + " LIMIT %s OFFSET %s"
			value.append(page_limit)
			value.append(offset)

		quotation_list = tapiutils.execute_frappe_select_sql(sql_str, tuple(value), "quotation.py", "fetch_quotations")

	if (len(quotation_list) <= 0):
		logger.debug("NO Quotations FOUND in Fetch Quotations")

	return quotation_list

def create_quotation(doctype, reqData):
	details = reqData.get("details")
	items=[]
	offers=[]
	doc_id = ""

	travelling_on = details["travelling_on"]
	input_items=details["items"]
	for input_item in input_items:
		bundle_id = input_item["item_code"]
		rfq_id = (str(reqData.get("rfq_id",""))).strip()
		est_price = input_item["rate"]
		amount = input_item["amount"]
		if not (rfq_id):
			is_package, currency = check_is_package(bundle_id)
			logger.debug("IS PACKAGE")
			logger.debug(is_package)
			logger.debug("currency ")
			logger.debug(currency)
			if (is_package):
				product_list = get_product_list(bundle_id)
				logger.debug("PRODUCT LIST")
				logger.debug(product_list)
				est_price = get_estimated_price(bundle_id, travelling_on, currency, product_list)
				logger.debug("Est Price " + str(est_price))

		item= {"item_code":bundle_id,
		"item_name":input_item["item_name"],
		"qty":input_item["qty"],
		"rate":est_price,
		"amount":amount,
		"description":input_item["description"]}

		items.append(item)

	input_offers=details["offers"]
	for input_offer in input_offers:
		offer = {"offer":input_offer["name"], "included":input_offer["included"]}
		offers.append(offer)

	req_json={
		"company": "Strawberi",
		"price_category": details["price_category"],
		"customer": details["customer_id"],
		"party_name": details["customer_id"],
		"travelling_on": details["travelling_on"],
		"travelling_end_date":details["travelling_end_date"],
		"number_of_children":details["number_of_children"],
		"number_of_adult": details["number_of_adult"],
		"number_of_infant": details["number_of_infant"],
		"request_for_quotation": reqData.get("rfq_id"),
		"supplier": details["supplier"],
		"opportunity":details["inquiry_id"],
		"items": items,
		"offer": offers,
		"notes": details["notes"]
	}

	try:
		doc = frappe.new_doc(doctype)
		doc.update(req_json)
		doc.save()
		doc_id = doc.name
	except Exception as e:
		raise travel_user_exception(tapiconstants.FRAPPE_ERROR, tapiconstants.ERR_CODE_505, tapiconstants.ERR_INFO_505,
								"Frappe Exception while creating quotation doctype in create_quotation", e)

	create_success = True
	#return create_success
	return doc_id

def update_quotation(doctype, reqData):
	quotation_type = reqData.get("for")
	details = reqData.get("details")
	quotation_id = details["quotation_id"]

	try:
		if (quotation_type == "supplier"):
			frappe.db.sql("""delete from `tabSupplier Quotation Item` where parenttype = "Supplier Quotation" and parent= %s""", (quotation_id), as_dict=1)
		elif (quotation_type == "customer"):
			frappe.db.sql("""delete from `tabQuotation Item` where parenttype = "Quotation" and parent= %s""", (quotation_id), as_dict=1)
		frappe.db.sql("""delete from `tabOffer` where parenttype in ("Supplier Quotation", "Quotation") and parent= %s""", (quotation_id), as_dict=1)

		doc = frappe.get_doc(doctype, quotation_id)
		doc.company = "Strawberi"
		travelling_on = details["travelling_on"]
		doc.travelling_on=travelling_on
		doc.travelling_end_date = details["travelling_end_date"]
		doc.price_category = details["price_category"]
		doc.customer=details["customer_id"]
		doc.party_name=details["customer_id"]
		doc.number_of_children=details["number_of_children"]
		doc.number_of_adult=details["number_of_adult"]
		doc.number_of_infant=details["number_of_infant"]
		doc.request_for_quotation=reqData.get("rfq_id")
		doc.supplier=details["supplier"]
		doc.opportunity=details["inquiry_id"]
		doc.notes=details["notes"]

		input_items=details["items"]
		for input_item in input_items:
			bundle_id = input_item["item_code"]
			rfq_id = (str(reqData.get("rfq_id",""))).strip()
			est_price = input_item["rate"]
			amount = input_item["amount"]
			if not (rfq_id):
				is_package, currency = check_is_package(bundle_id)
				logger.debug("IS PACKAGE" + str(is_package) + " currency " + currency)
				if (is_package):
					product_list = get_product_list(bundle_id)
					logger.debug("PRODUCT LIST")
					logger.debug(product_list)
					est_price = get_estimated_price(bundle_id, travelling_on, currency, product_list)
					logger.debug("Est Price " + str(est_price))

			doc.append("items", {"item_code":bundle_id,
					"item_name":input_item["item_name"],
					"qty":input_item["qty"],
					"rate":est_price,
					"amount":amount,
					"description":input_item["description"]})

		input_offers=details["offers"]
		for input_offer in input_offers:
			doc.append("offer", {"offer":input_offer["name"], "included":input_offer["included"]})

		doc.save()
	except Exception as e:
		raise travel_user_exception(tapiconstants.FRAPPE_ERROR, tapiconstants.ERR_CODE_506, tapiconstants.ERR_INFO_506,
								"Frappe Exception while updating quotation doctype in update_quotation", e)

	update_success = True
	return update_success

def check_is_package(bundle_id):
	is_package = False
	currency = ""
	bundles=tapiutils.execute_frappe_select_sql("""select * from `tabProduct Bundle` where new_item_code=%s""",
								(bundle_id), "quotation.py", "check_is_package")
	for bundle in bundles:
		is_package = True
		currency = bundle.currency
		break

	return is_package, currency

def get_product_list(bundle_id):
	product_list = []
	first_hotel=tapiutils.execute_frappe_select_sql("""select * from `tabItinerary  Details` where parent=%s and parenttype=%s and category='Hotel' order by day ASC LIMIT 1""",
								(bundle_id, "Product Bundle"), "quotation.py", "product_list")
	for hotel in first_hotel:
		product_details = {"product_id":hotel.product, "qty":hotel.qty}
		day = hotel.day
		hotel_id = hotel.product
		product_list.append(product_details)
		sql_str = "select * from `tabItinerary  Details` where parent=%s and parenttype=%s and category='Hotel' and day=%s and product in (select item_code from tabItem where variant_of in (select variant_of from tabItem where item_code = %s))"
		hotel_variants=tapiutils.execute_frappe_select_sql(sql_str, (bundle_id, "Product Bundle", day, hotel_id), "quotation.py", "get_product_list")
		for hotel_variant in hotel_variants:
			if not (hotel_variant.product == hotel_id):
				product_details = {"product_id":hotel_variant.product, "qty":hotel_variant.qty}
				product_list.append(product_details)

	return product_list



def get_variant_product_list(bundle_id, day, hotel_list):
	product_list = []
	i = 0
	for hotel in hotel_list:
		i = i + 1
		if (i == 1):
			first_hotel_id = hotel["product_id"]
			product_details = {"product_id":first_hotel_id, "qty":hotel["qty"]}
			product_list.append(product_details)
		else:
			hotel_id = hotel["product_id"]
			sql_str = "select item_code from tabItem where item_code = %s and variant_of in (select variant_of from tabItem where item_code = %s)"
			hotel_variants=tapiutils.execute_frappe_select_sql(sql_str, (hotel_id, first_hotel_id), "quotation.py", "get_product_list")
			for hotel_variant in hotel_variants:
				product_details = {"product_id":hotel_id, "qty":hotel["qty"]}
				product_list.append(product_details)
				break

	return product_list


def get_entity_status(entity_type, entity_id):
	sql_str = ""
	entity_sub_status =""
	entity_main_status =""
	entity_details = {}

	if (entity_type == "Inquiry"):
		sql_str = "select * from `tabOpportunity` where name=%s"
	elif (entity_type == "RFQ"):
		sql_str = "select * from `tabRequest for Quotation` where name=%s"
	elif (entity_type == "Customer Quotation"):
		sql_str = "select * from `tabQuotation` where name=%s"
	elif (entity_type == "Customer"):
		sql_str = "Select * from `tabCustomer` where name=%s"
	elif (entity_type == "Supplier Quotation"):
		sql_str = "select * from `tabSupplier Quotation` where name=%s"
	elif (entity_type == "Package"):
		sql_str = "select * from `tabProduct Bundle` where new_item_code=%s"

	if (sql_str):
		entity_list=tapiutils.execute_frappe_select_sql(sql_str,(entity_id), "quotation.py", "get_entity_status")
		for entity in entity_list:
			if (entity_type == "Inquiry"):
				entity_main_status = entity.tap_main_status
				entity_sub_status = entity.tap_sub_status
			elif (entity_type == "Package"):
				entity_main_status = entity.tap_status
				entity_sub_status = ""
			else:
				entity_main_status = entity.status
				entity_sub_status = ""
			entity_details =  {"transaction_type": entity_type, "transaction_id": entity_id, "tap_main_status":entity_main_status, "tap_sub_status": entity_sub_status}
			break

	return entity_details



def fetch_rfqs(entities, fmt_start_date, fmt_end_date):
	#sql_str = "select * from `tabRequest for Quotation`"
	sql_str = "select * from `tabRequest for Quotation`"
	where_str = ""
	value = []
	for entity in entities:
		entity_type = entity["entity_type"]
		entity_id = entity["entity_id"]
		if not (entity_type):
			raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_501, "entity_type:" + tapiconstants.ERR_INFO_501,
									"Invalid Input entity_type in the list of entities in fetch_rfqs", None)
		if not (entity_id):
			raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_501, "entity_id for entity_type" + tapiconstants.ERR_INFO_501,
									"Invalid Input entity_id for entity_type in fetch_rfqs" + entity_type, None)
		if (entity_type.lower() == "customer"):
			if (where_str):
				where_str = where_str + " AND "
			where_str = where_str + "customer_name=%s"
			value.append(entity_id)
		elif (entity_type.lower() == "sales_person"):
			if (where_str):
				where_str = where_str + " AND "
			where_str = where_str + "sales_person=%s"
			value.append(entity_id)
		elif (entity_type.lower() == "opportunity"):
			if (where_str):
				where_str = where_str + " AND "
			where_str = where_str + "opportunity=%s"
			value.append(entity_id)
		elif (entity_type.lower() == "supplier"):
			if (where_str):
				where_str = where_str + " AND "
			where_str = where_str + "name in (select parent from `tabRequest for Quotation Supplier` where parenttype = 'Request For Quotation' and supplier_name = %s)"
			value.append(entity_id)

	if (fmt_start_date):
		if (where_str):
			where_str = where_str + " AND "
		where_str = where_str + " creation >= %s"
		value.append(fmt_start_date)
	if (fmt_end_date):
		if (where_str):
			where_str = where_str + " AND "
		where_str = where_str + " creation <= %s"
		value.append(fmt_end_date)

	if (where_str):
		where_str = " where " + where_str
	sql_str = sql_str + where_str + " order by creation desc"
	logger.debug("SQL STR In Fetch RFQs" + sql_str)
	try:
		rfq_list = frappe.db.sql(sql_str, tuple(value), as_dict=1)
	except Exception as de:
		raise travel_user_exception(tapiconstants.DB_ERROR, tapiconstants.ERR_CODE_502, tapiconstants.ERR_INFO_502,
								"Database Exception while fetching RFQs in fetch_rfqs for ", de)
	if (len(rfq_list) <= 0):
		logger.debug("NO RFQS FOUND in Fetch RFQs")
	return rfq_list

def get_rfq_supplier(rfq_id):
	supplier_name=""
	suppliers=tapiutils.execute_frappe_select_sql("""select supplier_name from `tabRequest for Quotation Supplier` where parenttype='Request For Quotation' AND parent=%s""",
								(rfq_id), "quotation.py", "get_rfq_supplier")
	for supplier in suppliers:
		supplier_name = supplier.supplier_name
		break
	return supplier_name

def get_branch_for_sp(sp_name):
	sp_branch=""
	if (sp_name):
		sales_persons = tapiutils.execute_frappe_select_sql("""select emp.branch as emp_branch from `tabEmployee` emp JOIN `tabSales Person` sp
		on emp.name=sp.employee where sp.sales_person_name=%s""", (sp_name), "quotation.py", "get_branch_for_sp")
		for person in sales_persons:
			sp_branch = person.emp_branch
			break
	return sp_branch

def get_sp_for_quotation(rfq_id):
	sp_name=""
	if (rfq_id):
		sales_persons = tapiutils.execute_frappe_select_sql("""select sales_person from `tabRequest for Quotation` where name=%s""",(rfq_id),
														"quotation.py", "get_sp_for_quotation")
		for person in sales_persons:
			sp_name = person.sales_person
			break;

	return sp_name

def get_rfq_package(rfq_id):
	package_name=""
	packages=tapiutils.execute_frappe_select_sql("""select item_code from `tabRequest for Quotation Item` where parenttype='Request For Quotation' AND parent=%s""",
								(rfq_id),"quotation.py", "get_rfq_package")
	for package in packages:
		package_name = package.item_code
		break
	return package_name

def get_quotation_package(doc_type, quotation_id):
	package_name=""
	if (doc_type.lower() == "customer"):
		packages = tapiutils.execute_frappe_select_sql("""select item_code from `tabQuotation Item` where parenttype='Quotation' AND parent=%s""",
							(quotation_id),"quotation.py", "get_quotation_package")
	elif (doc_type.lower() == "supplier"):
		packages = tapiutils.execute_frappe_select_sql("""select item_code from `tabSupplier Quotation Item` where parenttype='Supplier Quotation' AND parent=%s""",
							(quotation_id),"quotation.py", "get_quotation_package")
	for package in packages:
		package_name = package.item_code
		break
	return package_name

def validate_request(reqData):
	quotation_type = reqData.get("for")
	tapiutils.validate_empty_string(quotation_type, "'for'", "quotation.py", "validate_request")

	#rfq_id = reqData.get("rfq_id")
	#tapiutils.validate_empty_string(rfq_id, "'rfq_id'", "quotation.py", "validate_request")

	details = reqData.get("details")
	if (details == None or  len(details) <= 0):
		raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_501, "Details " + tapiconstants.ERR_INFO_501,
										"Details cannot be empty in  validate_request of create_update_quotation", None)

	price_category = details["price_category"]
	tapiutils.validate_empty_string(price_category, "'price_category'", "quotation.py", "validate_request")

	if (quotation_type.strip().lower() == "customer"):
		customer = details["customer_id"]
		tapiutils.validate_empty_string(customer, "'customer_id'", "quotation.py", "validate_request")

	if (quotation_type.strip().lower() == "supplier"):
		supplier = details["supplier"]
		tapiutils.validate_empty_string(supplier, "'supplier'", "quotation.py", "validate_request")

	travelling_on = details["travelling_on"]
	tapiutils.validate_empty_string(travelling_on, "'travelling_on'", "quotation.py", "validate_request")
	traveling_on_date = tapiutils.validate_convert_date(travelling_on, "%Y-%m-%d"," %Y-%m-%d", "'travelling_on'", "quotation.py", "validate_request")

	number_of_children = details["number_of_children"]
	children = tapiutils.validate_convert_int(number_of_children, "'number_of_children'", "quotation.py", "validate_request")

	number_of_adult = details["number_of_adult"]
	adult = tapiutils.validate_convert_int(number_of_adult, "'number_of_adult'", "quotation.py", "validate_request")

	number_of_infant = str(details["number_of_infant"])
	infant = tapiutils.validate_convert_int(number_of_infant, "'number_of_infant'", "quotation.py", "validate_request")

	items = details["items"]
	if not (items == None):
		if (len(items) <= 0):
			raise travel_user_exception(tapiconstants.USER_ERROR, tapiconstants.ERR_CODE_501, "Items " + tapiconstants.ERR_INFO_501,
										"Items cannot be empty in  validate_request of create_update_quotation", None)



def get_quotation_sort_str(sort_by):
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
	elif (sort_by_str == "status"):
		sort_str=" order by status asc"

	return sort_str

@frappe.whitelist()
def create_or_update_customer_package():
	try:
		reqData = json.loads(frappe.request.data)
		customer_package_id = reqData.get("customer_package_id")
		parent_product_bundle = reqData.get("parent_product_bundle")
		items = reqData.get("items")
		company = "Strawberi"
		if customer_package_id:

			doc = frappe.get_doc("Product Bundle", customer_package_id)
			doc.items=[]
			doc.itinerary=[]
			for item in items:
				for itinerary in item["Itinerary"]:
					for i in range(0,len(itinerary["Products"])):

						product = itinerary["Products"][i]["Product_ID"]
						qty=itinerary["Products"][i]["qty"]
						category = itinerary["Category"]
						place=itinerary["Products"][i]["Place"]
						title=itinerary["Products"][i]["Title"]
						description=itinerary["Products"][i]["Description"]
						if product:
							row = doc.append('items',{})
							row.item_code= product
							row.item_name= product
							row.category= category
							row.location = place
							row.qty=qty
						#row.idx = i
						itinerary = doc.append('itinerary',{})
						itinerary.day=item["Day"]
						itinerary.start_date=item["Start_Date"]
						itinerary.end_date=item["End_Date"]
						itinerary.category= category
						itinerary.place= place
						itinerary.product= product
						itinerary.qty= qty
						itinerary.title=title
						itinerary.description=description
						#itinerary.idx=i
						doc.save()

			return "Product bundle has been updated successfully."

		else:

			item_Details = frappe.db.sql("""select * from `tabItem` where item_code=%s""",(parent_product_bundle),as_dict=1)
			if item_Details:
				item_group=item_Details[0]["item_group"]

			parent_product_bundle_item = parent_product_bundle + " FIT%"
			last_child_item = frappe.db.sql("""select * from `tabItem` where item_code like %s order by name desc limit 1""",(parent_product_bundle_item),as_dict=1)
			if last_child_item:
				item_last_value=last_child_item[0]["name"][-3:]
				#print(item_last_value)
				try:
					int_item_last_value = int(item_last_value) + 1
					pb_item_code=parent_product_bundle+" FIT-"+str('%03d' % int_item_last_value )
				except ValueError as ve:
					pb_item_code=parent_product_bundle+" FIT-001"
			else:
				pb_item_code=parent_product_bundle+" FIT-001"

			product_bundle_name=""

			item_code = frappe.db.sql("""select name from `tabProduct Bundle` where parent_product_bundle=%s order by name desc limit 1""",(parent_product_bundle),as_dict=1)
			if item_code:
				pd_last_value=item_code[0]["name"][-3:]
				pd_last_value=int(pd_last_value)+1
				product_bundle_name=parent_product_bundle+" FIT-"+str('%03d' % pd_last_value )
			else:
				product_bundle_name=parent_product_bundle+" FIT-001"


			item_json={
	    				"item_code": pb_item_code,
	    				"item_name": pb_item_code,
	    				"item_group": item_group,
	   				"stock_uom": "Nos",
	   				"category": "Package",
	    				"is_stock_item": 0,
	   			        "include_item_in_manufacturing": 0,
	    				"price_category": "Budget",
	     				"item_defaults": [{
	      							"company": company
	   						 }]
	  				}
			doc = frappe.new_doc("Item")
			doc.update(item_json)
			doc.save()
			itinerary_detail=[]
			product_bundle_item=[]
			for item in items:
				for itinerary in item["Itinerary"]:
					for product in itinerary["Products"]:
						itinerary_value={"day":item["Day"],"start_date":item["Start_Date"],"end_date":item["End_Date"],"category":itinerary["Category"],"place":product["Place"],
	"product":product["Product_ID"],"qty":product["qty"],"title":product["Title"], "description":product["Description"]}
						itinerary_detail.append(itinerary_value)
						if product["Product_ID"]:
							pb_item={"item_code":product["Product_ID"],"qty":product["qty"],"category":itinerary["Category"],"location":product["Place"],"description":""}
							product_bundle_item.append(pb_item)
			product_bundle_json = {
						 "new_item_code": product_bundle_name,
	   					 "description":"" ,
	   					 "id": "",
	   					 "parent_product_bundle":parent_product_bundle,
	   					 "package_type":"Standard",
	   					 "items": product_bundle_item,
						 "itinerary":itinerary_detail
					      }
			doc = frappe.new_doc("Product Bundle")
			doc.update(product_bundle_json)
			doc.save()
			returnStr = {"Product_Bundle_Id":doc.name}
			return returnStr
			#return "Product Bundle has created successfully."
	except Exception as e:
		#make_error_snapshot(e)
		logger.exception(tapiconstants.RUNTIME_ERROR + ": " + tapiconstants.ERR_CODE_500 + ": " + tapiconstants.ERR_INFO_500
						+" DESCRIPTION : Runtime Exception in create_or_update_customer_package")
		err_info = 'Error: {}. {}, line: {}'.format(sys.exc_info()[0],sys.exc_info()[1],sys.exc_info()[2].tb_lineno)
		error_json = {"Status":"500", "Error_code":tapiconstants.ERR_CODE_500, "Error Message":tapiconstants.ERR_INFO_500, "Error Type":type(e), "Error Info":err_info}
		frappe.local.response['http_status_code'] = 500
		return error_json


@frappe.whitelist()
def create_rfq_pdf(rfq_id):
	f=open("/home/frappe/frappe-bench/apps/strawberi/strawberi/QuotationTemplate.html", 'r')
	quotation=str(f.read())

	rfq_details =frappe.db.sql("""select * from `tabRequest for Quotation` rfq join `tabRequest for Quotation Item` rfqi on rfq.name=rfqi.parent  where rfq.name=%s """,(rfq_id),as_dict=1)
	rfq_supplier =frappe.db.sql("""select * from `tabRequest for Quotation Supplier` where parent=%s """,(rfq_id),as_dict=1)
	if rfq_supplier:
		quotation=quotation.replace("supplier_id",rfq_supplier[0]["supplier"])
	for q in rfq_details:
		quotation=quotation.replace("quotation_id",q.parent)
		quotation=quotation.replace("date_value",str(q.transaction_date))
		quotation=quotation.replace("notes_value",q.notes)
		quotation=quotation.replace("package_id",q.item_code)
		quotation=quotation.replace("package_name",q.item_code)
		quotation=quotation.replace("travveling_on",str(q.traveling_on))
		travellers=str(str(q.number_of_adult) +" Adults, "+ str(q.number_of_children)+" Child, " + str(q.number_of_infant) +" infant")
		quotation=quotation.replace("travellers",travellers)
		quotation=quotation.replace("class_value",q.price_category)
		pb_details=frappe.db.sql("""select * from `tabProduct Bundle` pb join `tabProduct Bundle Attribute` pba on pb.parent_product_bundle=pba.product_bundle where pb.new_item_code=%s""",(q.item_code),as_dict=1)

		if pb_details:

			if pb_details[0]["flights"]==1:
				quotation=quotation.replace("Inclusion1","Flights")
			else:
				quotation=quotation.replace("Inclusion1","")
			if pb_details[0]["visa"]==1:
				quotation=quotation.replace("Inclusion2","Visa")
			else:
				quotation=quotation.replace("Inclusion2","")
			if pb_details[0]["hotels"]==1:
				quotation=quotation.replace("Inclusion3","Hotels")
			else:
				quotation=quotation.replace("Inclusion3","")
			if pb_details[0]["sightseeing"]==1:
				quotation=quotation.replace("Inclusion4","Sightseeing")
			else:
				quotation=quotation.replace("Inclusion4","")
			quotation=quotation.replace("tour_type",pb_details[0]["tour_type"])
			quotation=quotation.replace("nod",str(pb_details[0]["number_of_days"]))
			quotation=quotation.replace("non",str(pb_details[0]["number_of_nights"]))
		hotels_details=frappe.db.sql("""select * from `tabItinerary  Details` where category="Hotel" and parent=%s order by day""",(q.item_code),as_dict=1)
		#Preparing Hotel Table
		hotels_value=""
		for hotel in hotels_details:
			hotel_list="<tr style= 'border-bottom: 1px solid gray;'><td style=width:70%>day_value<br><br><b>hotel_name</b></td><td style=width:20%></td><td style=width:20%></td><td style= text-align: right;width:100%></td></tr>"
			if hotel.day:
				hotel_list=hotel_list.replace("day_value","Day "+ str(hotel.day))
			else:
				hotel_list=hotel_list.replace("day_value","Day "+ "")
			if hotel.product:
				item_name =frappe.db.sql("""select item_name from `tabItem` where item_code=%s """,(hotel.product),as_dict=1)
				hotel_list=hotel_list.replace("hotel_name",item_name[0]["item_name"])
			else:
				hotel_list=hotel_list.replace("hotel_name","")
			#if hotel.description:
			#	hotel_list=hotel_list.replace("description",hotel.description)
			#else:
			#	hotel_list=hotel_list.replace("description","")
			#if hotel.title:
			#	hotel_list=hotel_list.replace("title",hotel.title)
			#else:
			#	hotel_list=hotel_list.replace("title","")
			hotels_value=hotels_value + hotel_list
		hotels="<table style=width:100%;margin-top:10px;><tr style='border-bottom: 1px solid gray;'><td><b>Hotel</b> </td><td colspan=3 style= text-align: right;></td></tr></table>"
		hotel_position = hotels.find('</table>')
		hotels   =  hotels[:hotel_position] + hotels_value + hotels[hotel_position:]
		quotation=quotation.replace("hotels",hotels)
		sightseeing=frappe.db.sql("""select * from `tabItinerary  Details` where category="Sightseeing" and parent=%s order by day""",(q.item_code),as_dict=1)
		ss_value=""
		for ss in sightseeing:
			sightseeing_list="<tr style='border-bottom: 1px solid gray;'><td style=width:50%>Day day_value <br><br><b>sightseeing_name</b><br><br>date</td><td style=width:30%></td><td style= text-align: right;width:30%></td></tr>"
			if ss.day:
				sightseeing_list=sightseeing_list.replace("day_value",str(ss.day))
			else:
				sightseeing_list=sightseeing_list.replace("day_value","")
			if ss.product:
				item_name =frappe.db.sql("""select item_name from `tabItem` where item_code=%s """,(ss.product),as_dict=1)
				sightseeing_list=sightseeing_list.replace("sightseeing_name",str(item_name[0]["item_name"]))
			else:
				sightseeing_list=sightseeing_list.replace("sightseeing_name","")
			if ss.date:
				sightseeing_list=sightseeing_list.replace("date",str(ss.date))
			else:
				sightseeing_list=sightseeing_list.replace("date","")
			#if ss.title:
				#sightseeing_list=sightseeing_list.replace("title",str(ss.title))
			#else:
				#sightseeing_list=sightseeing_list.replace("title","")
			ss_value=ss_value+sightseeing_list
		sightseeing_value="<table style=width:100%;margin-top:10px;><tr style=' border-bottom: 1px solid gray;'><td><b>Attraction</b></td><td colspan=2 style= text-align: right;></td></tr></table>"
		ss_position = sightseeing_value.find('</table>')
		sightseeing_value   =  sightseeing_value[:ss_position] + ss_value + sightseeing_value[ss_position:]
		quotation=quotation.replace("sightseeing",sightseeing_value)
		transfer=frappe.db.sql("""select * from `tabItinerary  Details` where category="Transfer" and parent=%s order by day""",(q.item_code),as_dict=1)
		transfer_list=""
		for t in transfer:

			transfer_value="<tr style=' border-bottom: 1px solid gray;' ><td style=width:60%>Day day_value<br><br><b>transfer_name</b></td><td style=width:20%>Transfer type<br><br>transfer_type Transfer</td><td style=width:10%></td><td style= text-align: right;width:10%></td></tr>"
			if t.day:
				transfer_value=transfer_value.replace("day_value",str(t.day))
			else:
				transfer_value=transfer_value.replace("day_value","")
			if t.product:
				item_name =frappe.db.sql("""select item_name from `tabItem` where item_code=%s """,(t.product),as_dict=1)
				transfer_value=transfer_value.replace("transfer_name",str(item_name[0]["item_name"]))
			else:
				transfer_value=transfer_value.replace("transfer_name","")
			variant=frappe.db.sql("""select * from `tabItem Variant Attribute` where parent=%s""",(t.product),as_dict=1)
			for v in variant:
				if v.attribute == "Vehicle Type":
					transfer_value=transfer_value.replace("transfer_capacity",v.attribute_value)
				if v.attribute == "Transfer Type":
					transfer_value=transfer_value.replace("transfer_type",v.attribute_value)
			transfer_list=transfer_list+transfer_value
		transfers="<table style=width:100%;margin-top:10px;><tr style= 'border-bottom: 1px solid gray;'><td><b>Transfer</b></td><td colspan=3 style= text-align: right;></td></tr></table>"
		t_position = transfers.find('</table>')
		transfers   =  transfers[:t_position] + transfer_list + transfers[t_position:]
		quotation=quotation.replace("transfers",transfers)
		pdfkit.from_string(quotation, "/home/frappe/frappe-bench/sites/site1.local/public/files/"+rfq_id+"-RFQ.pdf")



	#a_website = "http://13.232.237.47:8000/files/"+rfq_id+"-RFQ.pdf"
	#with open('/home/nikhil/Download/TEST.pdf', 'wb') as f:
    		#f.write(r.content)
	#webbrowser.open(a_website)


	pos_a = str(frappe.request.url).find("8000")
	ip_address=frappe.request.url[0:pos_a]


	return {"rfq_pdf_path":ip_address+"8000/files/"+rfq_id+"-RFQ.pdf"}



@frappe.whitelist()
def create_quotation_pdf(quotation_id):
	pos_a = str(frappe.request.url).find("8000")

	ip_address=frappe.request.url[0:pos_a]

	pdf_url=ip_address+"8000/api/method/frappe.utils.print_format.download_pdf?doctype=Quotation&name="+quotation_id+"&format={Standard}&no_letterhead=0"
	return pdf_url


@frappe.whitelist()
def create_quot_pdf(quotation_id):
	f=open("/home/frappe/frappe-bench/apps/strawberi/strawberi/Quotation.html", 'r')
	quotation=str(f.read())
	quotation=quotation.replace("quotation_id",quotation_id)
	quot_details =frappe.db.sql("""select * from `tabQuotation` q join `tabQuotation Item` qi on q.name=qi.parent  where q.name=%s """,(quotation_id),as_dict=1)
	
	for q in quot_details:
		quotation=quotation.replace("total_cost",str(q.total))
		quotation=quotation.replace("currency",str(q.price_list_currency))
		quotation=quotation.replace("grand_total",str(q.grand_total))
		quotation=quotation.replace("total_taxes_and_charges",str(q.total_taxes_and_charges))
		quotation=quotation.replace("creation_date",str(q.creation.strftime(" %d %b %Y ")))
		quotation=quotation.replace("package_name",q.item_name)
		quotation=quotation.replace("travelling_ed",str(q.travelling_end_date.strftime(" %d %b %Y ")))
		quotation=quotation.replace("travelling_on",str(q.travelling_on.strftime(" %d %b %Y ")))
		quotation=quotation.replace("noa",str(q.number_of_adult))
		quotation=quotation.replace("noc",str(q.number_of_children))
		quotation=quotation.replace("noi",str(q.number_of_infant))
		quotation=quotation.replace("no_of_travellers",str(q.number_of_children+q.number_of_infant+q.number_of_adult))
		quotation=quotation.replace("price_category",str(q.price_category))
		
		pb_details=frappe.db.sql("""select * from `tabProduct Bundle` pb join `tabProduct Bundle Attribute` pba on pb.parent_product_bundle=pba.product_bundle where pb.new_item_code=%s""",(q.item_code),as_dict=1)
		sales_person=frappe.db.sql("""select sales_person from `tabRequest for Quotation`  where name=%s""",(q.request_for_quotation),as_dict=1)
		
		if sales_person:
			quotation=quotation.replace("agent_name",str(sales_person[0]["sales_person"]))
		else:
			quotation=quotation.replace("agent_name",str("N/A"))
		inclusion_value=""
		if pb_details:
			
			if pb_details[0]["flights"]==1:
				inclusion_value=inclusion_value+"<li><span class=type>Flights</span></li>"
			if pb_details[0]["visa"]==1:
				inclusion_value=inclusion_value+"<li><span class=type>Visa</span></li>"
			if pb_details[0]["hotels"]==1:
				inclusion_value=inclusion_value+"<li><span class=type>Hotels</span></li>"
			if pb_details[0]["sightseeing"]==1:
				inclusion_value=inclusion_value+"<li><span class=type>Sightseeing</span></li>"
			if pb_details[0]["transfer"]==1:
				inclusion_value=inclusion_value+"<li><span class=type>Transfer</span></li>"
			quotation=quotation.replace("inclusion",inclusion_value)
			quotation=quotation.replace("tour_type",pb_details[0]["tour_type"])
			quotation=quotation.replace("nod",str(pb_details[0]["number_of_days"]))
			quotation=quotation.replace("non",str(pb_details[0]["number_of_nights"]))
			quotation=quotation.replace("theme_value",str(pb_details[0]["theme"]))	
			places=frappe.db.sql("""select * from `tabPlace` where parent in (select item_code from `tabProduct Bundle Item` where parent=%s and category="Package") order by sequence_number""",(pb_details[0]["parent_product_bundle"]),as_dict=1)
			place_name="";
			for p in places:
				place_name=place_name+p.place+"("+str(p.number_of_nights)+"N) > "
			quotation=quotation.replace("places",str(place_name))
		itinerary=frappe.db.sql("""select * from `tabItinerary  Details` where parent=%s order by day""",(q.item_code),as_dict=1)
		itinerary_value=""
		for i in itinerary:
			itinerary_details="<div class=card><div class=card-header id=heading-0><div class=accordian-top><div class=day  detail-set sect-1>Day day_value - date</div> <div class=info-wrapper float-col><h4 class=subtitle>Place: Title </h4 class=subtitle>Description<h4></h4></div></div></div><hr class=cal-border></div>"
			itinerary_details=itinerary_details.replace("day_value",str(i.day))
			itinerary_details=itinerary_details.replace("date",str(i.date.strftime(" %d %b %Y ")))
			itinerary_details=itinerary_details.replace("Place",str(i.place))
			itinerary_details=itinerary_details.replace("Title",str(i.title))
			itinerary_details=itinerary_details.replace("Description",str(i.description))
			itinerary_value=itinerary_value+itinerary_details
		quotation=quotation.replace("itinerary_value",str(itinerary_value))
		
	pos_a = str(frappe.request.url).find("8000")
	ip_address=frappe.request.url[0:pos_a]
	quotation=quotation.replace("ip_address",ip_address)
	pdfkit.from_string(quotation, "/home/frappe/frappe-bench/sites/site1.local/public/files/"+quotation_id+".pdf")
	print (quotation)
	#return {"quotation_path":ip_address+"8000/files/"+quotation_id+".pdf"}
	return quot_details
	
