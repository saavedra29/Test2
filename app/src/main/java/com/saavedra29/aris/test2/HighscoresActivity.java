package com.saavedra29.aris.test2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighscoresActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                "Highscores", MODE_PRIVATE);
        TextView v1 = (TextView)findViewById(R.id.highscore1output);
        v1.setText(preferences.getString("1_str", "00:00:0"));
        TextView v2 = (TextView)findViewById(R.id.highscore2output);
        v2.setText(preferences.getString("2_str", "00:00:0"));
        TextView v3 = (TextView)findViewById(R.id.highscore3output);
        v3.setText(preferences.getString("3_str", "00:00:0"));
        TextView v4 = (TextView)findViewById(R.id.highscore4output);
        v4.setText(preferences.getString("4_str", "00:00:0"));
        TextView v5 = (TextView)findViewById(R.id.highscore5output);
        v5.setText(preferences.getString("5_str", "00:00:0"));

    }
}
