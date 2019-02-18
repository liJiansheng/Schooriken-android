package com.angelhack.ri.schooriken;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class UpdateData extends Service
{
	private int mId;
	Context c;
	private final IBinder mBinder =new LocalBinder();
	
	public class LocalBinder extends Binder
	{
		UpdateData getService()
		{
			return UpdateData.this; 
		}
	}
	
	@Override
	public void onCreate()
	{
		
	}
	
	@Override
	public int onStartCommand(Intent i,int flags, int startid)
	{
		return START_STICKY;
	}
	
	public void addnotif(String title,String msg)
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
		nm.notify(mId,b.build());
	}



	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}
}
