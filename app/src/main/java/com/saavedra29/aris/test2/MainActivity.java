package com.saavedra29.aris.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    public static int rounds = 1;
    public static long scoreLong;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
