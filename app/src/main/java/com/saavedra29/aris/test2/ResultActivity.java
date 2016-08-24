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



        /// ---- CHECK FOR HIGHSCORE AND UPDATE IF NEEDED ----///
        boolean newHighscore = false;
        String oldScoreString = null;
        // Take context data from previous activity
        Bundle extras = getIntent().getExtras();
        // Score time in milliseconds
        long score = extras.getLong("score");
        // Score time in string time format
        String scoreStr = extras.getString("scoreStr");

        // Create shared preferences object
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                "Highscores", MODE_PRIVATE
        );

        long tmpScore = preferences.getLong(Integer.toString(MainActivity.rounds),
                score + 1);
        oldScoreString = preferences.getString(Integer.toString(
                MainActivity.rounds) + "_str", scoreStr);

        if (tmpScore > score)
        {
            newHighscore = true;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(Integer.toString(MainActivity.rounds), score);
            editor.putString(Integer.toString(MainActivity.rounds) + "_str", scoreStr);
            editor.apply();
        }
        /// -----------------------------------------------------///


        // Create reference to the output textview
        TextView text = (TextView)findViewById(R.id.score_output);

        // If oldScore is not null inform about new highscore
        if (newHighscore)
        {
            text.setText("You have achived new HIGHSCORE!!" +
            "\nCurrent score: " + scoreStr +
            "\nPrevious highscore: " + oldScoreString);
        }

        else
        {
            text.setText("Current score: " + scoreStr + "\nHighscore: " + oldScoreString);
        }

        // TAKE CARE OF GOING BACK TO MAIN ACTIVITY BY TOUCHING ANYWHERE IN THE ACTIVITY
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
