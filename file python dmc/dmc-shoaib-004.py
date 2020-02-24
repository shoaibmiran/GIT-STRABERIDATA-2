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
import json

@frappe.whitelist()
def post_dmc_list(itemcode):
	dmc_list=[]				
	if itemcode:
		supplier_data = frappe.db.sql("""select
		   name,supplier_name,supplier_type,supplier_group from tabSupplier where name IN(select supplier from `tabItem Supplier` where parent='"""+itemcode+"""')""", as_dict=1)
		for item in supplier_data:
			sup=item.name
			group=item.supplier_group
			doc="Supplier"
			address=get_address(sup,doc)
			contacts=get_contacts(sup,doc)
			dmc_value={"supplier_name":item.name,"supplier_type":item.supplier_type,"supplier_group":item.supplier_group,"address":address,"contacts":contacts}
			dmc_list.append(dmc_value)
	if itemcode=='':
		supplier_data2 = frappe.db.sql("""select
		   name,supplier_name,supplier_type,supplier_group from tabSupplier where supplier_group='DMC'""", as_dict=1)
		for item in supplier_data2:
			sup=item.name
			group=item.supplier_group
			doc="Supplier"
			address=get_address(sup,doc)
			contacts=get_contacts(sup,doc)
			dmc_value={"supplier_name":item.name,"supplier_type":item.supplier_type,"supplier_group":item.supplier_group,"address":address,"contacts":contacts}
			dmc_list.append(dmc_value)
	return dmc_list

@frappe.whitelist()
def get_address(sup,doc):
	address_datas=frappe.db.sql("""select
			parent from `tabDynamic Link` where link_name=%s and parenttype='Address' and link_doctype=%s """,(sup,doc), as_dict=1)
	address=[]
	for q in address_datas:
		add_data=frappe.db.sql("""select * from `tabAddress` where name=%s """,(q.parent), as_dict=1)
		for a in add_data:
			address_value={"address_type":a.address_type,"address_title":a.address_title,"address_line":a.address_line1,"city":a.city,"pincode":a.pincode,"state":a.state,"country":a.country}
			address.append(address_value)
	return address

@frappe.whitelist()
def get_contacts(sup,doc):
	co_datas=frappe.db.sql("""select
					parent from `tabDynamic Link` where link_name=%s and parenttype='Contact' and link_doctype=%s """,(sup,doc), as_dict=1)
	contacts=[]
	for cont_dt in co_datas:
		co_cont=frappe.db.sql("""select * from `tabContact` where name=%s""",(cont_dt.parent),as_dict=1)
		for contacts2 in co_cont:
			contacts_value={"first_name":contacts2.first_name,"email_id":contacts2.email_id,"phone":contacts2.phone}
			contacts.append(contacts_value)
	return contacts
	

