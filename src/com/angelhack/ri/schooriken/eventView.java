package com.angelhack.ri.schooriken;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class eventView extends RelativeLayout{

	private TextView datePost;
	private TextView name;
	private TextView datedue;
	private TextView descrip;
	private Button joinBtn;
	private eventClass curEvent;
	private ImageView imview;
	
	private RelativeLayout defaultLay;
	private RelativeLayout joinedLay;
	
	private TextView daysLeft;
	private TextView joinName;
	
	private MainActivity _root;
	private eventView thisSelf;
	
	public eventView(Context context,eventClass tmpEvent,MainActivity parentClass){
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.event_layout, this, true);
		
		curEvent = tmpEvent;
		_root = parentClass;
		thisSelf = this;
		
		datePost = (TextView) findViewById(R.id.eventLayout_datePost);
		name = (TextView) findViewById(R.id.eventLayout_name);
		datedue = (TextView) findViewById(R.id.eventLayout_dateDue);
		descrip = (TextView) findViewById(R.id.eventLayout_description);
		joinBtn = (Button) findViewById(R.id.eventLayout_joinBtn);
		imview = (ImageView) findViewById(R.id.eventLayout_pic);
		
		defaultLay = (RelativeLayout) findViewById(R.id.evtLay_notJoined);
		joinedLay = (RelativeLayout) findViewById(R.id.evtLay_joined);
		
		daysLeft = (TextView) findViewById(R.id.evtLayout_dateLeft);
		joinName = (TextView) findViewById(R.id.evtLayout_name);
		
		if(tmpEvent.getStatus()==true){
			joined();
		}
		else{		
			joinBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					joined();
					_root.changeEvtState(curEvent.getID());
				}
			});
		}
		initUI();
		
	}
	
	private void joined(){
		android.widget.FrameLayout.LayoutParams paramsShow = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.joinedLay.setLayoutParams(paramsShow);
		android.widget.FrameLayout.LayoutParams paramsHide = (android.widget.FrameLayout.LayoutParams) this.defaultLay.getLayoutParams();
		paramsHide.height=0;
		this.defaultLay.setLayoutParams(paramsHide);
		initUITwo();
	}
	
	private void initUI(){
		datePost.setText(curEvent.getPostTime());
		name.setText(curEvent.getName());
		datedue.setText(Integer.toString(curEvent.getDaysLeft()));
		descrip.setText(curEvent.getDes());
		if (curEvent.getImage()!=null)
		{
			imview.setImageBitmap(curEvent.getImage());
		}
	}
	
	private void initUITwo(){
		daysLeft.setText(Integer.toString(curEvent.getDaysLeft()));
		joinName.setText(curEvent.getName());
	}
	
}