package com.angelhack.ri.schooriken;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Others_tabActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Others tab");
        setContentView(textview);
    }
}