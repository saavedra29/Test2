package com.example.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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
        TextView text = (TextView)findViewById(R.id.score_output);
        text.setText(GameActivity.score);

    }

    /*
    public void restartGame(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
    */

}
