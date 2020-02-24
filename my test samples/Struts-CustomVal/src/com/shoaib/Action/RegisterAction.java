package com.shoaib.Action;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport{

	private String name,password;
	

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
	
	public void validate() {  
	    if(name.length()<1)  
	        addFieldError("name","Name can't be blank");  
	    if(password.length()<6)  
	        addFieldError("password","Password must be greater than 5");  
	}  
	  
	  
	public String execute(){  
	    return "success";  
	}


}
