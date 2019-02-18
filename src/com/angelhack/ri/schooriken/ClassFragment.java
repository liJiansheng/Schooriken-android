package com.angelhack.ri.schooriken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 
/**
 * @author mwho
 *
 */
public class ClassFragment extends Fragment {
	private View mLoginStatusView,mLoginFormView;
	private String myUsername, mPassword;
	private UserLoginTask mAuthTask=null;
	private UserLoginTask2 mAuthTask2=null;
	private JSONObject jObj;
	private LinearLayout lly;
	private List<groupClass> groups;
	private TextView mLoginStatusMessageView;
    /** (non-Javadoc)
     * @return 
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	return (RelativeLayout)inflater.inflate(R.layout.fragment_layout, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle SavedInstanceState)
    {
    	super.onActivityCreated(SavedInstanceState);
    	Log.d("CCA","started");
    	groups= new ArrayList<groupClass>();
    	myUsername=((AddGroup)(getActivity())).getuname();
    	mPassword=((AddGroup)(getActivity())).getpassword();
    	lly=((LinearLayout)(getActivity().findViewById(R.id.fragment_mainLay)));
    	mLoginStatusMessageView=(TextView)(getActivity().findViewById(R.id.login_status_message2));
    	mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
    	mLoginStatusView=getView().findViewById(R.id.login_status2);
    	mLoginFormView=getView().findViewById(R.id.login_form2);
    	viewtoast("Started...");
		showProgress(true);
		mAuthTask = new UserLoginTask();
		mAuthTask.execute((Void) null);
		showProgress(true);
		mAuthTask2 = new UserLoginTask2();
		mAuthTask2.execute((Void) null);
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
    
    public void viewtoast(String s)
	{
		Toast t=Toast.makeText(getActivity().getBaseContext(), s, Toast.LENGTH_SHORT);
		t.show();
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
					"&query="+URLEncoder.encode("nongroup","UTF-8");
			
			   final WebConnection wc = new WebConnection("http://schooriken.com/api.php");
		        final String str = wc.DownloadText(getActivity().getBaseContext(),true,param);
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
			showProgress(false);
			
			JSONArray totJSONData;
			boolean checkStatus;

			if (success) {
				try {
					Log.d("CCA","Here");
					totJSONData = jObj.getJSONArray("data");
					Log.d("CCA","Here2");
					if(totJSONData!=null) checkStatus = true;
					Log.d("CCA","Here3");
					Log.d("CCA","l:"+totJSONData.length());
					for(int i=0;i<totJSONData.length();i++){
						Log.d("CCA","Here3.5");
						JSONObject tmpArr = (JSONObject) totJSONData.get(i);
						Log.d("CCA","Here4");
						Log.d("CCA Activity","1:"+tmpArr.get("group_id")+",2:"+tmpArr.get("type"));
						if(tmpArr.getString("type").equals("Class")){
							Log.d("CCA","Here5");
							Log.d("CCA",tmpArr.getString("type")+tmpArr.getString("group_name"));
							groupClass tmpGrp = new groupClass(tmpArr.getString("group_id"),tmpArr.getString("group_name"));
							Log.d("CCA",(tmpGrp!=null?"yes":"true"));
							groups.add(tmpGrp);
							groupView g=new groupView(getActivity().getBaseContext(),tmpGrp,myUsername,mPassword);
							lly.addView(g);
							final groupView h=g;
							g.setOnClickListener(
									new View.OnClickListener(){
										@Override
										public void onClick(View v)
										{
											
											if (h.statusLay.isChecked())
											{
												h.triggeroff();
												h.statusLay.setChecked(false);
											}
											else
											{
												h.triggeron();
												h.statusLay.setChecked(true);
											}
										}
									}
									);
						}
						Log.d("CCA","Here6");
					}
					Log.d("CCA","Here7");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				//finish();
			} else {
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
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
					"&query="+URLEncoder.encode("group","UTF-8");
			
			   final WebConnection wc = new WebConnection("http://schooriken.com/api.php");
		        final String str = wc.DownloadText(getActivity().getBaseContext(),true,param);
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
			showProgress(false);
			
			JSONArray totJSONData;
			boolean checkStatus;

			if (success) {
				try {
					Log.d("CCA","Here");
					totJSONData = jObj.getJSONArray("data");
					Log.d("CCA","Here2");
					if(totJSONData!=null) checkStatus = true;
					Log.d("CCA","Here3");
					Log.d("CCA","l:"+totJSONData.length());
					for(int i=0;i<totJSONData.length();i++){
						Log.d("CCA","Here3.5");
						JSONObject tmpArr = (JSONObject) totJSONData.get(i);
						Log.d("CCA","Here4");
						Log.d("CCA Activity","1:"+tmpArr.get("group_id")+",2:"+tmpArr.get("type"));
						if(tmpArr.getString("type").equals("Class")){
							Log.d("CCA","Here5");
							Log.d("CCA",tmpArr.getString("type")+tmpArr.getString("group_name"));
							groupClass tmpGrp = new groupClass(tmpArr.getString("type"),tmpArr.getString("group_name"));
							Log.d("CCA",(tmpGrp!=null?"yes":"true"));
							groups.add(tmpGrp);
							groupView g=new groupView(getActivity().getBaseContext(),tmpGrp,myUsername,mPassword);
							lly.addView(g);
							g.statusLay.setChecked(true);
							final groupView h=g;
							g.setOnClickListener(
									new View.OnClickListener(){
										@Override
										public void onClick(View v)
										{
											
											if (h.statusLay.isChecked())
											{
												h.triggeroff();
												h.statusLay.setChecked(false);
											}
											else
											{
												h.triggeron();
												h.statusLay.setChecked(true);
											}
										}
									}
									);
						}
						Log.d("CCA","Here6");
					}
					Log.d("CCA","Here7");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				//finish();
			} else {
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}