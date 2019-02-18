package com.angelhack.ri.schooriken;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class taskClass extends schooClass{
	
	
	public taskClass(String tmpPost,Date tmpDue,String name,String description,int tmpID){
		super(tmpPost,tmpDue,name,description,tmpID);
	}
	
	//Setter Function	
	/*public void setPostTime(Timestamp tmpPost){
		this.postedTime = tmpPost;
	}*/
	
	public int rp()
	{
		//Signature for task class
		return 1;
	}
	
	public int getDaysLeft(){
		int dayDiff=0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calCur = Calendar.getInstance();
	    Calendar calDue = Calendar.getInstance();
	    calDue.setTime(dueTime);
		dayDiff=calDue.get(Calendar.DAY_OF_YEAR)-calCur.get(Calendar.DAY_OF_YEAR);
		
		return dayDiff;
	}
	
}