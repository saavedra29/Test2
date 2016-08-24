package com.saavedra29.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tx = (TextView)findViewById(R.id.result_label);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/wood.ttf");
        tx.setTypeface(custom_font);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                "Highscores", MODE_PRIVATE
        );
        String highscoreStr = preferences.getString(Integer.toString(MainActivity.rounds) + "_str",
                "00:00:0");

        TextView text = (TextView)findViewById(R.id.score_output);
        text.setText("Current score: " + GameActivity.score + "\nHighscore: " + highscoreStr);

        LinearLayout l = (LinearLayout)findViewById(R.id.resultLayout);
        l.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

    }

}
