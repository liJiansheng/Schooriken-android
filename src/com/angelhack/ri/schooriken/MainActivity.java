package com.angelhack.ri.schooriken;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String md5tmpPass;
	private String tmpUser;
	private boolean checkStatus=false;
	
	private String debugStr=null;
	
	private LinearLayout mainLay;
	private Button reSync;
	
	private ArrayList<eventClass> totEventArr = new ArrayList<eventClass>();
	private ArrayList<taskClass> totTaskArr = new ArrayList<taskClass>();
	private JSONArray totJSONData;
	
	//Sorting Variables
	private int lenEvents;
	private int lenTasks;
	private ArrayList<schooClass> sortedArr=new ArrayList<schooClass>();
	
	private ArrayList<Integer> delTasks=new ArrayList<Integer>();
	private ArrayList<Integer> evtState=new ArrayList<Integer>();
	
	private MainActivity _root=null;
	
	private GetUserEvents uGEvent=null;
	private UpdateEventState upDateEvt=null;
	private DeleteTasks delTasksSync=null;
	
	private eventClass bugEvent;
	private int mId;
	public AlarmManager alman;
	public BroadcastReceiver bcr;
	public PendingIntent pii;
	private String myUsername, mPassword;
	
	@Override
	protected void onDestroy()
	{
		/*alman.cancel(pii);
		unregisterReceiver(bcr);*/
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		_root=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent i=getIntent();
		Bundle extras=i.getExtras();
		myUsername=extras.getString("uname");
		mPassword=extras.getString("pwd");
		md5tmpPass=mPassword;
		tmpUser=myUsername;
		
		mainLay = (LinearLayout) findViewById(R.id.main_mainLay);
		//reSync = (Button) findViewById(R.id.main_reSync);
		
		uGEvent = new GetUserEvents();
		uGEvent.execute((Void) null);
		//addnotif("Event Pending Intent","Y u no teach us about pending intent?\nY I know nothing about broadcast receiver?");
		//setup();
		Intent ii=new Intent(this,schooReceiver.class);
		alman=(AlarmManager)(getSystemService(Context.ALARM_SERVICE));
		pii=PendingIntent.getBroadcast(this, 1, ii, PendingIntent.FLAG_UPDATE_CURRENT);
		GregorianCalendar g= new GregorianCalendar();
		GregorianCalendar ng= new GregorianCalendar(g.get(Calendar.YEAR),g.get(Calendar.MONTH),g.get(Calendar.DAY_OF_MONTH)+1,6,0);//7.50 clock test
		//viewtoast(Long.toString(t));
		
		alman.setRepeating(AlarmManager.RTC_WAKEUP, ng.getTimeInMillis(), 1000*60*60*24,pii); // 1 second = 1000;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem i)
	{
		switch(i.getItemId())
		{
			case R.id.action_settings:
				//viewtoast("Settings");
				//addnotif("Hi","Test\nTest\nTest\n");
				break;
			case R.id.plus:
				Intent ii=new Intent(this,AddGroup.class);
				Bundle extras=new Bundle();
				extras.putString("uname", myUsername);
				extras.putString("pwd",mPassword);
				ii.putExtras(extras);
				startActivity(ii);
				break;
			case R.id.refresh:
				if(uGEvent == null){
					totEventArr.clear();
					totTaskArr.clear();
					sortedArr.clear();
					mainLay.removeAllViews();
					upDateEvt = new UpdateEventState();
					upDateEvt.execute((Void) null);
					delTasksSync= new DeleteTasks();
					delTasksSync.execute((Void) null);
					uGEvent = new GetUserEvents();
					uGEvent.execute((Void) null);
				}
				//viewtoast("Refreshed");
				break;
			default:
				break;
		}
		return true;
	}
	
	public void viewtoast(String s)
	{
		Toast t=Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT);
		t.show();
	}
	
	private void setup() //Sets up alarm manager
	{
		bcr=new BroadcastReceiver(){
			@Override
			public void onReceive(Context c,Intent i)
			{
				viewtoast("test");
				addnotif("Event Pending Intent","Y u no teach us about pending intent?\nY I know nothing about broadcast receiver?");
			}
		};
		registerReceiver(bcr,new IntentFilter("com.angelhack.ri.schooriken"));
		pii=PendingIntent.getBroadcast(this, 0, new Intent("com.angelhack.ri.schooriken"), 0);
		alman=(AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
	}
	
	public void addnotif(String title,String msg)
	{
		NotificationCompat.Builder b= new NotificationCompat.Builder(this);
		b.setSmallIcon(R.drawable.schooriken_notif);
		b.setContentTitle(title);
		b.setContentText(msg);
		NotificationCompat.BigTextStyle bt=new NotificationCompat.BigTextStyle();
		bt.bigText(msg);
		b.setStyle(bt);
		Intent intentt=new Intent(this,MainActivity.class);
		TaskStackBuilder tsb = TaskStackBuilder.create(this);
		tsb.addParentStack(MainActivity.class);
		tsb.addNextIntent(intentt);
		PendingIntent pii=tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		b.setContentIntent(pii);
		NotificationManager nm=(NotificationManager)(getSystemService(Context.NOTIFICATION_SERVICE));
		nm.notify(mId,b.build());
	}
	
	private void testtest()
	{
		
	}
	
	public void delTask(taskView tmpTskView){
		delTasks.add(tmpTskView.getTask().getID());
		mainLay.removeView(tmpTskView);
	}
	
	public void changeEvtState(int evtID){
		evtState.add(evtID);
	}
	
	public void mainLayUI(ArrayList<eventClass> tmpEventArr,ArrayList<taskClass> tmpTaskArr){
		lenEvents=tmpEventArr.size();
		lenTasks=tmpTaskArr.size();
		
		String beforeStr="";
		String afterStr="";
		for (eventClass i:tmpEventArr)
		{
			sortedArr.add((schooClass)i);
		}
		for (taskClass i:tmpTaskArr)
		{
			sortedArr.add((schooClass)i);
		}
		Collections.sort(sortedArr);
		for (schooClass i:sortedArr)
		{
			if (i.rp()==2)
			{
				eventView tmpView = new eventView(getBaseContext(),(eventClass)(i),_root);
				mainLay.addView(tmpView);
			}
			else
			{
				taskView tmpView = new taskView(getBaseContext(),(taskClass)(i),_root);
				mainLay.addView(tmpView);
			}
		}
	}
	
	public class GetUserEvents extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
						
			try {
				// Simulate network access.
				String data = "user="+URLEncoder.encode(tmpUser,"UTF-8")+"&pass="+URLEncoder.encode(md5tmpPass,"UTF-8")+"&query="+URLEncoder.encode("event","UTF-8");
				Log.d("MainDebug",data);
				
				internetSocket intSoc = new internetSocket("http://schooriken.com/api.php");
				String output = intSoc.getData(getBaseContext(),data);
				
				if(output==null) return false;
				
				totJSONData = new JSONArray(output);
				for(int i=0;i<totJSONData.length();i++){
					JSONObject tmpObj = new JSONObject(totJSONData.get(i).toString());
					
					if(tmpObj!=null){
						//Extract information from json object
						if(tmpObj.getString("type").equals("Event")){
							
							if(tmpObj.getInt("flag")==1){
								int curID = tmpObj.getInt("event_id");
								String postTime = tmpObj.getString("posted");
								SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
								String tmpTime = tmpObj.getString("date");
								Date dueTime = null;
								try {
					                dueTime = formatter.parse(tmpTime);
					            } catch (ParseException e) {
					                e.printStackTrace();
					            }
								String tmpName = tmpObj.getString("title");
								String tmpDescrip = tmpObj.getString("description");
								String img=tmpObj.getString("image");
								Bitmap image=null;
								if (img!="null")
								{
									Log.d("MainActivity","not a null image");
									WebConnection wc2= new WebConnection(img);
									image=wc2.DownloadImage(MainActivity.this, 100, 100);
								}
								
								eventClass tmpEvent = new eventClass(postTime,dueTime,tmpName,tmpDescrip,curID,true,image);
								//put formatted event into totEventArr
								bugEvent = tmpEvent;
								totEventArr.add(tmpEvent);
							}
							else{
								int curID = tmpObj.getInt("event_id");
								String postTime = tmpObj.getString("posted");
								SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
								String tmpTime = tmpObj.getString("date");
								Date dueTime = null;
								try {
					                dueTime = formatter.parse(tmpTime);
					            } catch (ParseException e) {
					                e.printStackTrace();
					            }
								String tmpName = tmpObj.getString("title");
								String tmpDescrip = tmpObj.getString("description");
								String img=tmpObj.getString("image");
								Bitmap image=null;
								if (img!="null")
								{
									Log.d("MainActivity","not a null image");
									WebConnection wc2= new WebConnection(img);
									image=wc2.DownloadImage(MainActivity.this, 100, 100);
								}
								
								
								eventClass tmpEvent = new eventClass(postTime,dueTime,tmpName,tmpDescrip,curID,false,image);
								bugEvent = tmpEvent;
								//put formatted event into totEventArr
								totEventArr.add(tmpEvent);
							}
						}
						else if(tmpObj.getString("type").equals("Assignment")){
							if(tmpObj.getInt("flag")!=1){
								int curID = tmpObj.getInt("event_id");
								String postTime = tmpObj.getString("posted");
								SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
								String tmpTime = tmpObj.getString("date");
								Date dueTime = null;
								try {
					                dueTime = formatter.parse(tmpTime);
					            } catch (ParseException e) {
					                e.printStackTrace();
					            }
								String tmpName = tmpObj.getString("title");
								String tmpDescrip = tmpObj.getString("description");
								
								taskClass tmpTask = new taskClass(postTime,dueTime,tmpName,tmpDescrip,curID);
								//put 
								totTaskArr.add(tmpTask);
							}
						}
					}
				}
				
			}
			catch (Exception err) {
				err.printStackTrace();
			}

			if(uGEvent == null){
				return false;
			}
			else{
				return true;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			uGEvent = null;
			
			if (success) {
				Toast.makeText(getBaseContext(),"getUserEvent",Toast.LENGTH_SHORT).show();
				//mainLayUI(totEventArr,totTaskArr);
			}
			else {
				Toast.makeText(getBaseContext(),"Something went wrong :(",Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			uGEvent = null;
		}
	}
	
	public class UpdateEventState extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
						
			try {
				// Simulate network access.
				
				//if(evtState.size()==0) return true;
				
				String data = "user="+URLEncoder.encode(tmpUser,"UTF-8")+"&pass="+URLEncoder.encode(md5tmpPass,"UTF-8")+"&query="+URLEncoder.encode("trueflagarr","UTF-8")+"&eventidarr="+URLEncoder.encode(evtState.toString(),"UTF-8");
				Log.d("PHP Trace",data);
				
				internetSocket intSoc = new internetSocket("http://schooriken.com/api.php");
				String output = intSoc.getData(getBaseContext(),data);
				
				if(output==null) return false;
				
				JSONArray tmpArray = new JSONArray(output);
				if(tmpArray.getBoolean(0)==true){
					return true;
				}
				else return false;
			}
			catch (Exception err) {
				err.printStackTrace();
			}

			if(upDateEvt == null){
				return false;
			}
			else{
				return true;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			upDateEvt = null;

			if (success) {
				Toast.makeText(getBaseContext(),"Updateevent",Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getBaseContext(),"Something went wrong :(",Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			upDateEvt = null;
		}
	}
	
	public class DeleteTasks extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
						
			try {
				// Simulate network access.
				
				//if(delTasks.size()==0) return true;
				
				String data = "user="+URLEncoder.encode(tmpUser,"UTF-8")+"&pass="+URLEncoder.encode(md5tmpPass,"UTF-8")+"&query="+URLEncoder.encode("trueflagarr","UTF-8")+"&eventidarr="+URLEncoder.encode(delTasks.toString(),"UTF-8");
				
				debugStr = delTasks.toString();
				
				internetSocket intSoc = new internetSocket("http://schooriken.com/api.php");
				String output = intSoc.getData(getBaseContext(),data);
				
				if(output==null) return false;
				
				JSONArray tmpArray = new JSONArray(output);
				if(tmpArray.getBoolean(0)==true){
					return true;
				}
				else return false;
				
			}
			catch (Exception err) {
				err.printStackTrace();
			}

			if(delTasksSync == null){
				return false;
			}
			else{
				return true;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			delTasksSync = null;
			Toast.makeText(getBaseContext(), "delete tasks"+debugStr,Toast.LENGTH_LONG).show();
			if (success) {
			}
			else {
				Toast.makeText(getBaseContext(),"Something went wrong :(",Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			delTasksSync = null;
		}
	}


}
