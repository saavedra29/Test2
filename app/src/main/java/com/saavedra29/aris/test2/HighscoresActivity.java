package com.saavedra29.aris.test2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        TextView v = (TextView)findViewById(R.id.highscore1output);
        v.setText(preferences.getString("1_str", "00:00:0"));
        v = (TextView)findViewById(R.id.highscore2output);
        v.setText(preferences.getString("2_str", "00:00:0"));
        v = (TextView)findViewById(R.id.highscore3output);
        v.setText(preferences.getString("3_str", "00:00:0"));
        v = (TextView)findViewById(R.id.highscore4output);
        v.setText(preferences.getString("4_str", "00:00:0"));
        v = (TextView)findViewById(R.id.highscore5output);
        v.setText(preferences.getString("5_str", "00:00:0"));

    }

    public void onReset1(View view)
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Highscores",
                MODE_PRIVATE);
        preferences.edit().remove("1").apply();
        preferences.edit().remove("1_str").apply();
        TextView v = (TextView)findViewById(R.id.highscore1output);
        v.setText("00:00:0");
    }

    public void onReset2(View view)
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Highscores",
                MODE_PRIVATE);
        preferences.edit().remove("2").apply();
        preferences.edit().remove("2_str").apply();
        TextView v = (TextView)findViewById(R.id.highscore2output);
        v.setText("00:00:0");
    }

    public void onReset3(View view)
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Highscores",
                MODE_PRIVATE);
        preferences.edit().remove("3").apply();
        preferences.edit().remove("3_str").apply();
        TextView v = (TextView)findViewById(R.id.highscore3output);
        v.setText("00:00:0");
    }

    public void onReset4(View view)
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Highscores",
                MODE_PRIVATE);
        preferences.edit().remove("4").apply();
        preferences.edit().remove("4_str").apply();
        TextView v = (TextView)findViewById(R.id.highscore4output);
        v.setText("00:00:0");
    }

    public void onReset5(View view)
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Highscores",
                MODE_PRIVATE);
        preferences.edit().remove("5").apply();
        preferences.edit().remove("5_str").apply();
        TextView v = (TextView)findViewById(R.id.highscore5output);
        v.setText("00:00:0");
    }
}
