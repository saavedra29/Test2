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
import java.util.Map;

public class GameActivity extends Activity
{
    private MChronometer chrono;
    private ArrayList<Integer> couple = new ArrayList<>();
    private ArrayList<String> animalList;
    private HashMap<Integer, Integer> relation = new HashMap<>();
    private ViewGroup layoutView;
    static int round;
    static String score;
    private boolean gameState;
    private int imagesNumber;
    private int invisibleObjects;
    // chronometer related variables
    static long timeBeforeStop;
    static long timeAfterStop;
    static boolean firstTimeRunning;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        chrono = (MChronometer) findViewById(R.id.chronometer);
        round = 0;
        score = "";
        firstTimeRunning = true;
        initStage();
        timeBeforeStop = SystemClock.elapsedRealtime();

    }

    public void imageClick(View view)
    {
        ImageButton button1 = null;
        ImageButton button2 = null;
        int imageId1 = 0;
        int imageId2 = 1;
        // Method called when an imageButton is pressed

        // Check the stage
        if ((!gameState) && (!couple.contains(view.getId())))
        {
            // Check the couple for same elements
            for (Map.Entry<Integer, Integer> entry: relation.entrySet())
            {
                int buttonId = entry.getKey();
                if (buttonId == couple.get(0))
                {
                    button1 = (ImageButton)findViewById(buttonId);
                    int imageId = entry.getValue();
                    imageId1 = imageId;
                }

                if (buttonId == couple.get(1))
                {
                    button2 = (ImageButton)findViewById(buttonId);
                    int imageId = entry.getValue();
                    imageId2 = imageId;
                }
            }

            if (imageId1 == imageId2)
            {
                // Set the imageButtons invisible
                assert button1 != null;
                button1.setVisibility(View.INVISIBLE);
                invisibleObjects++;
                assert button2 != null;
                button2.setVisibility(View.INVISIBLE);
                invisibleObjects++;
            }

            // Change couple images back to cover
            for (int i = 1; i >= 0; i--)
            {
                ImageButton button = (ImageButton)findViewById(couple.get(i));
                if (button != null)
                {
                    button.setImageResource(getResources().getIdentifier("cover", "drawable",
                            getPackageName()));
                }
            }
            couple.clear();
            // Add current selected image to couple
            couple.add(0, view.getId());
            // Turn the current image
            ImageButton tmpButton = (ImageButton)view;
            int tmpImageId = relation.get(view.getId());
            tmpButton.setImageResource(tmpImageId);
            gameState = true;

        }
        else
        {
            // Check if already pressed
            int id = view.getId();
            if (id != couple.get(0));
            {
                // Turn the current image
                ImageButton tmpButton = (ImageButton)view;
                int tmpImageId = relation.get(view.getId());
                tmpButton.setImageResource(tmpImageId);
                // Add it to couple second position
                couple.add(1, id);
                // Check for end of game
                if ((invisibleObjects == imagesNumber - 2) && (round == MainActivity.stages))
                {
                    score = (String)chrono.getText();
                    chrono.stop();
                    Intent intent = new Intent(this, ResultActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (invisibleObjects == imagesNumber - 2)
                {
                    initStage();
                }
                gameState = false;
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
        couple.add(0, 0);
        couple.add(1, 1);
        relation = new HashMap<Integer, Integer>();
        invisibleObjects = 0;
        gameState = false;
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

    protected void onStop()
    {
        super.onStop();
        timeBeforeStop = SystemClock.elapsedRealtime();
        chrono.stop();
    }

    protected void onStart()
    {
        long difference;
        super.onStart();
        timeAfterStop = SystemClock.elapsedRealtime();
        difference = timeAfterStop - timeBeforeStop;
        chrono.setBase(chrono.getBase() + difference);
        chrono.start();
    }
}

