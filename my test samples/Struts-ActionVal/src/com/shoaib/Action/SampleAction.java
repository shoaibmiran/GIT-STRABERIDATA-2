package com.shoaib.Action;

import com.opensymphony.xwork2.ActionSupport;

public class SampleAction extends ActionSupport
{  
	private String name;
	
	private String password;
	
	public void validate() {  
	    if(name.trim().length()<1 || password.trim().length()<1){  
	        addActionError("Fields can't be blank");  
	    }  
	}  
	  
	  
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String execute(){  
	    return "success";  
	}  
}
