package com.angelhack.ri.schooriken;

import java.util.Date;

public class schooClass implements Comparable<schooClass>{
	protected String postedTime;
	//private Timestamp postedTime;
	protected String dueTime;
	protected String name;
	protected String description;
	protected boolean status;
	protected int ID;
	
	public schooClass(String tmpPost,String tmpDue,String name,String description,int tmpID){
		this.postedTime = tmpPost;
		this.dueTime = tmpDue;
		this.name = name;
		this.description = description;
		this.status = false;
		this.ID=tmpID;
	}
	
	public int rp()
	{
		//Signature for base class
		return 0;
	}
	
	public void setDueTime(String tmpDue){
		this.dueTime = tmpDue;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void setDes(String description){
		this.description = description;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	//PRIVATE FUNCTIONS
	protected String getMonth(int monthNo){
		switch(monthNo){
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";
		}
		return "ERROR";
	}
	
	//to be overwritten
	public int getDaysLeft()
	{
		return 100;
	}
	
	//Getter Function
	public int getID(){
		return this.ID;
	}
	public String getDueDate(){
		return this.dueTime.toString();
	}
	public String getPostTime(){
		return this.postedTime;
	}
	/*public String getPostTime(){
		String postStr = "";
		long timestamp = postedTime.getTimestamp().getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		postStr=Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+" "+getMonth(cal.get(Calendar.MONTH))+" "+Integer.toString(cal.get(Calendar.YEAR));
		return postStr;
	}*/
	
	public String getName(){
		return this.name;
	}
	
	public String getDes(){
		return this.description;
	}
	
	public boolean getStatus(){
		return this.status;
	}

	@Override
	public int compareTo(schooClass other) {
		
		if (this.getDaysLeft()==other.getDaysLeft())
		{
			return 0;
		}
		else
		{
			if (this.getDaysLeft()<other.getDaysLeft())
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}
}
