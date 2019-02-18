package com.angelhack.ri.schooriken;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class schooReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context cxt, Intent i) {
		// TODO Auto-generated method stub
		viewtoast(cxt,"test");
		addnotif(cxt,"Event Pending Intent","Y u no teach us about pending intent?\nY I know nothing about broadcast receiver?");
	}
	
	public void viewtoast(Context cxt,String s)
	{
		Toast t=Toast.makeText(cxt, s, Toast.LENGTH_SHORT);
		t.show();
	}
	
	public void addnotif(Context c,String title,String msg)
	{
		NotificationCompat.Builder b= new NotificationCompat.Builder(c);
		b.setSmallIcon(R.drawable.schooriken_notif);
		b.setContentTitle(title);
		b.setContentText(msg);
		NotificationCompat.BigTextStyle bt=new NotificationCompat.BigTextStyle();
		bt.bigText(msg);
		b.setStyle(bt);
		Intent intentt=new Intent(c,MainActivity.class);
		TaskStackBuilder tsb = TaskStackBuilder.create(c);
		tsb.addParentStack(MainActivity.class);
		tsb.addNextIntent(intentt);
		PendingIntent pii=tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		b.setContentIntent(pii);
		NotificationManager nm=(NotificationManager)(c.getSystemService(Context.NOTIFICATION_SERVICE));
		nm.notify(1,b.build());
	}
	
}
