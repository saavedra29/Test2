package com.saavedra29.aris.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity
{

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textView = (TextView)findViewById(R.id.seekView);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        final int max = seekBar.getMax() + 1;

        textView.setText("Rounds: " + 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int progress = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                progress = progresValue + 1;
                textView.setText("Rounds: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //textView.setText("Covered: " + progress + "/" + max);

            }

        });
    }
}
