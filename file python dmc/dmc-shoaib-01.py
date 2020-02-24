
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

def get_dmc_list():

	data2 = []
	dmc_list=[]
	supplier_data = frappe.db.sql("""select

		   name,supplier_name,supplier_type,supplier_group from tabSupplier where supplier_group='DMC'""", as_dict=1)				

	for item in supplier_data:

		sup=item.name

		group=item.supplier_group

		print("hi___",sup,group)

		doc="Supplier"

		print("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII")

		address_datas=frappe.db.sql("""select

		   parent from `tabDynamic Link` where link_name=%s and parenttype='Address' and link_doctype=%s """,(sup,doc), as_dict=1)
		co_datas=frappe.db.sql("""select

                   parent from `tabDynamic Link` where link_name=%s and parenttype='Contact' and link_doctype=%s """,(sup,doc), as_dict=1)

		data2.append([item.name,item.supplier_type,item.supplier_group])
		address=[]
		for q in address_datas:

			print(q.parent)

			print("Address DATas")

			add_data=frappe.db.sql("""select * from `tabAddress` where name=%s """,(q.parent), as_dict=1)

			print("address executed")

			for a in add_data:

				print(a.state,a.country,a.city,a.address_line1)

				data2.append([a.address_type,a.address_title,a.address_line1,a.city,a.pincode,a.state,a.country])
				address_value={"address_type":a.address_type,"address_title":a.address_title,"address_line":a.address_line1,"city":a.city,"pincode":a.pincode,"state":a.state,"country":a.country}
				address.append(address_value)
			#all_contacts=get_all_contacts(sup,doc)
		contacts=[]
		for cont_dt in co_datas:
			co_cont=frappe.db.sql("""select * from `tabContact` where name=%s""",(cont_dt.parent),as_dict=1)
			for contacts2 in co_cont:
				data2.append([contacts2.email_id,contacts2.phone,contacts2.first_name])
				contacts_value={"first_name":contacts2.first_name,"email_id":contacts2.email_id,"phone":contacts2.phone}
				contacts.append(contacts_value)
		dmc_value={"supplier_name":item.name,"supplier_type":item.supplier_type,"supplier_group":item.supplier_group,"contacts":contacts,"address":address}
		dmc_list.append(dmc_value)
	return dmc_list

@frappe.whitelist()
def get_all_contacts(sup,doc):
	co_data=""
	contact_datas=frappe.db.sql("""select parent from `tabDynamic Link` where link_name=%s and parenttype='Contact' and link_doctype=%s """,(sup,doc),as_dict=1)
	for cont in contact_datas:
		co_data=frappe.db.sql("""select * from tabContact where name=%s """,(cont.parent),as_dict=1)
	return co_data
