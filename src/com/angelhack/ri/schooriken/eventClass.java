package com.angelhack.ri.schooriken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.graphics.Bitmap;

public class eventClass extends schooClass{
	
	protected Bitmap image;
	
	public eventClass(String tmpPost,Date tmpDue,String name,String description,int tmpID,boolean tmpStatus,Bitmap img){
		super(tmpPost,tmpDue,name,description,tmpID);
		this.status = tmpStatus;
		this.image=img;
	}
	
	public int rp()
	{
		//signature for eventClass
		return 2;
	}
	
	//Setter Function	
	/*public void setPostTime(Timestamp tmpPost){
		this.postedTime = tmpPost;
	}*/
		
	//Getter Function
	/*public String getPostTime(){
		String postStr = "";
		long timestamp = postedTime.getTimestamp().getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		postStr=Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+" "+getMonth(cal.get(Calendar.MONTH))+" "+Integer.toString(cal.get(Calendar.YEAR));
		return postStr;
	}*/
	
	public int getDaysLeft(){
		int dayDiff=0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calCur = Calendar.getInstance();
	    Calendar calDue = Calendar.getInstance();
	    calDue.setTime(dueTime);
		dayDiff=calDue.get(Calendar.DAY_OF_YEAR)-calCur.get(Calendar.DAY_OF_YEAR);
		
		return dayDiff;
	}
	
	public Bitmap getImage()
	{
		return this.image;
	}
	
}