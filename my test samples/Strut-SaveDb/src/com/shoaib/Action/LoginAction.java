package com.shoaib.Action;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{

private  RegisterDto dto;
	
	public RegisterDto getDto() {
		return dto;
	}
	public void setDto(RegisterDto dto) {
		this.dto = dto;
	}
	
	public void validate()
	{
		
		if(dto.getEmailid().length()<1 || dto.getPassword().length()<1)
		{
			addActionError("Emailid and password can not be blank!!!!!");
		}
		
	}
	
	public String execute(){ 
	  	
	    String i=LoginDao.login(dto);  
	    if(i!="null")
		{
	    	 return "success"; 
		}
	    else
	    {
	    
	    return "error";  
	}
	}
}
