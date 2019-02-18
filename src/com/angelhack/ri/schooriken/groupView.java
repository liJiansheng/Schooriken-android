package com.angelhack.ri.schooriken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class groupView extends LinearLayout{
	
	public CheckBox statusLay;
	private TextView name;
	private UserLoginTask mAuthTask=null;
	private UserLoginTask2 mAuthTask2=null;
	private String myUsername,mPassword;
	private JSONObject jObj;
	private groupClass tmpGrp;
	private Context c;
	//private boolean checked;
	
	public groupView(Context context, groupClass tmpGroup,String uname, String pwd){
		super(context);
		myUsername=uname;
		mPassword=pwd;
		c=context;
		tmpGrp=tmpGroup;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.group_layout, this, true);
		
		statusLay = (CheckBox) findViewById(R.id.groupLayout_status);
		name = (TextView) findViewById(R.id.groupLayout_name);
		name.setText(tmpGroup.getGroupName());
		
	}
	
	public void triggeron()
	{
		mAuthTask = new UserLoginTask();
		mAuthTask.execute((Void) null);
	}
	
	public void triggeroff()
	{
		mAuthTask2 = new UserLoginTask2();
		mAuthTask2.execute((Void) null);
	}
	
	private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			int res = -1;
			StringBuilder sb = new StringBuilder();
			//viewtoast("Almost There");
			try {
			String param="user=" + URLEncoder.encode(myUsername,"UTF-8")+
					"&pass="+URLEncoder.encode(mPassword,"UTF-8")+
					"&query="+URLEncoder.encode("rd","UTF-8")+
					"&groupid="+URLEncoder.encode(tmpGrp.getName(),"UTF-8");
			Log.d("Debug--groupView",param);
			
			   final WebConnection wc = new WebConnection("http://schooriken.com/api.php");
		        final String str = wc.DownloadText(c,true,param);
		        //viewtoast(str);
		        
		        jObj = new JSONObject(str);	
			}catch (Exception e) {
				  e.printStackTrace();
				}
			return true;
		}
	//This function is to decode the Stream passed in by the server	
		private String readStream(InputStream in) {
			  BufferedReader reader = null;
			  String response="";
			  try {
			    reader = new BufferedReader(new InputStreamReader(in));
			    String line = "";
			    while ((line = reader.readLine()) != null) {
			      //System.out.println(line);
			    	response += line;
			    }
			  } catch (IOException e) {
			    e.printStackTrace();
			  } finally {
			    if (reader != null) {
			      try {
			        reader.close();
			      } catch (IOException e) {
			        e.printStackTrace();
			        }
			    }
			  }
			  return response;
			} 
		
		@Override
		protected void onPostExecute(final Boolean success) {
			Log.d("CCA","test");
			mAuthTask = null;
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}
	
	private class UserLoginTask2 extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			int res = -1;
			StringBuilder sb = new StringBuilder();
			//viewtoast("Almost There");
			try {
			String param="user=" + URLEncoder.encode(myUsername,"UTF-8")+
					"&pass="+URLEncoder.encode(mPassword,"UTF-8")+
					"&query="+URLEncoder.encode("ri","UTF-8")+
					"&id="+URLEncoder.encode(tmpGrp.getName(),"UTF-8");
			
			   final WebConnection wc = new WebConnection("http://schooriken.com/api.php");
		        final String str = wc.DownloadText(c,true,param);
		        //viewtoast(str);
		        
		        jObj = new JSONObject(str);	
			}catch (Exception e) {
				  e.printStackTrace();
				}
			return true;
		}
	//This function is to decode the Stream passed in by the server	
		private String readStream(InputStream in) {
			  BufferedReader reader = null;
			  String response="";
			  try {
			    reader = new BufferedReader(new InputStreamReader(in));
			    String line = "";
			    while ((line = reader.readLine()) != null) {
			      //System.out.println(line);
			    	response += line;
			    }
			  } catch (IOException e) {
			    e.printStackTrace();
			  } finally {
			    if (reader != null) {
			      try {
			        reader.close();
			      } catch (IOException e) {
			        e.printStackTrace();
			        }
			    }
			  }
			  return response;
			} 
		
		@Override
		protected void onPostExecute(final Boolean success) {
			Log.d("CCA","test");
			mAuthTask = null;
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}
	
}