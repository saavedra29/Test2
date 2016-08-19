package com.example.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class GameActivity extends Activity
{
    private MChronometer chrono;
    private int stage;
    private ArrayList<Integer> couple = new ArrayList<>();
    private ArrayList<String> animalList;
    private HashMap<Integer, Integer> relation = new HashMap<>();
    private ViewGroup layoutView;
    static int round;
    private int gameState;
    private int imagesNumber;
    private int invisibleObjects;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        chrono = (MChronometer) findViewById(R.id.chronometer);

    }

    public void imageClick(View view)
    {
        // Method called whan an imageButton is pressed

        // Check the stage
        if (stage == 0)
        {

        }
        else
        {
            // Check if already pressed
            int id = view.getId();
            if (id != couple.get(0));
            {

            }
        }
    }

    private void initStage()
    {
        // INITIALIZE ALL THE VARIALBES
        // Add to the rounds counter
        round++;
        // Set new text for the round indicator
        TextView indicator = (TextView)findViewById(R.id.round_indicator);
        indicator.setText(Integer.toString(round));
        // Create list of animal names and shuffle it
        animalList = new ArrayList<>(Arrays.asList("lion", "lion", "rhino",
                "rhino", "chimpanzee", "chimpanzee", "giraffe", "giraffe", "hippopotamus",
                "hippopotamus", "elephant", "elephant"));
        Collections.shuffle(animalList);
        // Some variables to reinitialize
        couple = new ArrayList<>();
        relation = new HashMap<Integer, Integer>();
        invisibleObjects = 0;
        gameState = 0;
        // Set all the images visible
        layoutView = (ViewGroup)findViewById(R.id.imagesLayout);
        imagesNumber = layoutView.getChildCount();
        for (int i = 0; i < imagesNumber; i++)
        {
            View imageButton = layoutView.getChildAt(i);
            imageButton.setVisibility(View.VISIBLE);
        }
        refreshImages();
    }

    private void refreshImages()
    {
        for (int i = imagesNumber - 1; i >= 0; --i)
        {
            ImageButton nextChild = (ImageButton) layoutView.getChildAt(i);
            String imageString = animalList.remove(0);
            int resID = getResources().getIdentifier(imageString, "drawable", getPackageName());
            nextChild.setImageResource(getResources().getIdentifier(
                    "cover", "drawable", getPackageName()));
            relation.put(nextChild.getId(), resID);

        }
    }
}

