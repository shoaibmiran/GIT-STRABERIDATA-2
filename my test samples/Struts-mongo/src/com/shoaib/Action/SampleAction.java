package com.shoaib.Action;

import com.opensymphony.xwork2.ActionSupport;

public class SampleAction extends ActionSupport{
	
	private  RegisterDto dto;
	
	public RegisterDto getDto() {
		return dto;
	}
	public void setDto(RegisterDto dto) {
		this.dto = dto;
	}
	
	public void validate()
	{
		String s=RegisterDao.check(dto); 
		if(s!="null")
		{
			addActionError("Emailid already exist!!!!!");
		}
		if(dto.getName().length()< 1 || dto.getEmailid().length()<1 || dto.getPassword().length()<1)
		{
			addActionError("Mandatory fields can't be blank!!!!!");
		}
		
		if(dto.getPassword().length()<6)
		{
			addActionError("Password must have morethan 5 characters");
		}
		
		if(dto.getPassword().equals(dto.getCpassword()))
		{
			
		}
		else
		{
			addActionError("Password missmatch");
		}
		
	}
	public String execute(){ 
		  	
	    int i=RegisterDao.save(dto);  
	    if(i>0){  
	    return "success";  
	    }  
	    return "error";  
	}

}
