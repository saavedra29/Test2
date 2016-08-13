package com.example.aris.test2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView text = (TextView)findViewById(R.id.score_output);
        text.setText(GameActivity.score);

    }
}
