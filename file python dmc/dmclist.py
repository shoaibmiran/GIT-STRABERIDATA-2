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
	supplier_data = frappe.db.sql("""select sup.name,sup.supplier_name,sup.supplier_type,sup.supplier_group,cont.phone,cont.email_id,adds.address_line1,adds.city,   
	adds.pincode,adds.state,adds.country FROM `tabSupplier` sup LEFT JOIN `tabDynamic Link` link ON sup.name=link.link_name LEFT JOIN `tabContact` cont ON             
	cont.name=link.parent LEFT JOIN `tabAddress` adds ON adds.name=link.parent where sup.supplier_group='DMC' """, as_dict=1)     
	return supplier_data