package com.angelhack.ri.schooriken;

public class groupClass {
	
	private String name;
	private boolean status;
	private String group_name;
	
	public groupClass(String name){
		this.name = name;
		this.status = false;
	}
	
	public groupClass(String name, String group_name)
	{
		this.name=name;
		this.group_name=group_name;
		this.status=false;
	}
	
	//Setter Function
	public void setName(String name){
		this.name = name;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	//Getter Function
	public String getName(){
		return this.name;
	}
	
	public boolean getStatus(){
		return this.status;
	}
	
	public String getGroupName(){
		return this.group_name;
	}
	
}