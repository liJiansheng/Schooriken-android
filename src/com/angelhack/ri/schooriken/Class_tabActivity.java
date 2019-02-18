package com.angelhack.ri.schooriken;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Class_tabActivity extends Activity {
	private View mLoginStatusView,mLoginFormView;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("This is the Others tab");
        setContentView(textview);
    }
    
    
}