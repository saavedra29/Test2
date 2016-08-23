package com.saavedra29.aris.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    public static int stages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        // Create the spinner
        Spinner spinner = (Spinner) findViewById(R.id.stages_spinner);
        ArrayAdapter<CharSequence> dataAdapter =ArrayAdapter.createFromResource(this,
                R.array.stages_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goToGame(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
    {
        stages = Integer.parseInt(adapterView.getItemAtPosition(pos).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    public void showSettings(MenuItem item)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showHighScores(MenuItem item)
    {
        Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
    }

    public void showAbout(MenuItem item)
    {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
