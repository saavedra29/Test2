package com.example.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Random;

public class GameActivity extends Activity
{
    // Create an image array
    /*
    String[] imageArray = {"R.drawable.chimpanzee", "R.drawable.elephant",
            "R.drawable.giraffe", "R.drawable.hippopotamus", "R.drawable.rhino",
            "R.drawable.lion"};
            */
    String[] imageArray = {"chimpanzee", "elephant",
            "giraffe", "hippopotamus", "rhino",
            "lion"};
    static int counter = 0;
    ViewGroup v;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        v = (ViewGroup)findViewById(R.id.imagesLayout);
        int cou = v.getChildCount();
        for (int i = 0; i < cou; i++)
        {
            ImageButton nextChild = (ImageButton) v.getChildAt(i);
            Random rand = new Random();
            int index = rand.nextInt(imageArray.length);
            String imageString = imageArray[index];
            int resID = getResources().getIdentifier(imageString, "drawable", getPackageName());
            nextChild.setImageResource(resID);

        }

    }

    public void addToCounter(View view)
    {
        counter++;
        Button b = (Button)findViewById(R.id.ok_button);
        b.setText("" + counter);
    }

    public void goToResult(View view)
    {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void refreshImages()
    {
        v = (ViewGroup)findViewById(R.id.imagesLayout);
        int cou = v.getChildCount();
        for (int i = 0; i < cou; ++i)
        {
            ImageButton nextChild = (ImageButton) v.getChildAt(i);
            Random rand = new Random();
            int index = rand.nextInt(imageArray.length);
            String imageString = imageArray[index];
            int resID = getResources().getIdentifier(imageString, "drawable", getPackageName());
            nextChild.setImageResource(resID);

        }
    }
}
