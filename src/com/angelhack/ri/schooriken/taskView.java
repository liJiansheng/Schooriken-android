package com.angelhack.ri.schooriken;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class taskView extends RelativeLayout{
	
	private TextView datePost;
	private TextView dateLeft;
	private TextView subject;
	private TextView name;
	private Button completeBtn;
	private taskClass curTask;
	
	private MainActivity _root;
	private taskView thisSelf;
	
	public taskView(Context context,taskClass tmpTask,MainActivity parentClass){
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.task_layout, this, true);

		curTask = tmpTask;
		_root=parentClass;
		thisSelf=this;
		
		datePost = (TextView) findViewById(R.id.taskLayout_datePost);
		dateLeft = (TextView) findViewById(R.id.taskLayout_dateLeft);
		subject = (TextView) findViewById(R.id.taskLayout_subject);
		name = (TextView) findViewById(R.id.taskLayout_name);
		completeBtn = (Button) findViewById(R.id.taskLayout_completeBtn);

		completeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_root.delTask(thisSelf);
			}
		});
		
		initUI();
		
	}
	
	public taskClass getTask(){
		return this.curTask;
	}
	
	private void initUI(){
		datePost.setText(curTask.getPostTime());
		dateLeft.setText(Integer.toString(curTask.getDaysLeft()));
		subject.setText(curTask.getName());
		name.setText(curTask.getDes());
	}
}