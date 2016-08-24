package com.saavedra29.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    private HashMap<Integer, Boolean> faceCondition = new HashMap<>();
    private ViewGroup layoutView;
    static int round;
    static String score;
    static long scoreLong;
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

        ImageButton currentButton = (ImageButton)view;
        if (!faceCondition.get(currentButton.getId()))
        {
            ImageButton button1 = null;
            ImageButton button2 = null;
            int imageId1 = 0;
            int imageId2 = 1;
            // Method called when an imageButton is pressed

            // Check the stage
            if (!gameState)
            {
                // Check the couple for same elements
                for (Map.Entry<Integer, Integer> entry: relation.entrySet())
                {
                    int buttonId = entry.getKey();
                    if (buttonId == couple.get(0))
                    {
                        button1 = (ImageButton)findViewById(buttonId);
                        imageId1 = entry.getValue();
                    }

                    if (buttonId == couple.get(1))
                    {
                        button2 = (ImageButton)findViewById(buttonId);
                        imageId2 = entry.getValue();
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
                        faceCondition.put(button.getId(), false);
                    }
                }
                couple.clear();
                // Add current selected image to couple
                couple.add(0, view.getId());
                // Turn the current image
                ImageButton tmpButton = (ImageButton)view;
                int tmpImageId = relation.get(view.getId());
                tmpButton.setImageResource(tmpImageId);
                faceCondition.put(currentButton.getId(), true);
                gameState = true;

            }
            else
            {
                // Check if already pressed
                int id = view.getId();
                // Turn the current image
                ImageButton tmpButton = (ImageButton)view;
                int tmpImageId = relation.get(view.getId());
                tmpButton.setImageResource(tmpImageId);
                faceCondition.put(currentButton.getId(), true);
                // Add it to couple second position
                couple.add(1, id);
                // Check for end of game
                if ((invisibleObjects == imagesNumber - 2) && (round == MainActivity.rounds))
                {
                    chrono.stop();
                    score = (String)chrono.getText();
                    // Create preferences to check if highscore and save
                    checkHighscore();
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
        indicator.setText(Integer.toString(round) + "/" + MainActivity.rounds);
        // Create list of animal names and shuffle it
        animalList = new ArrayList<>(Arrays.asList("lion", "lion", "rhino",
                "rhino", "chimpanzee", "chimpanzee", "giraffe", "giraffe", "hippopotamus",
                "hippopotamus", "elephant", "elephant", "cheetah", "cheetah", "gazelle",
                "gazelle", "gnu", "gnu", "zebra", "zebra"));
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
            faceCondition.put(nextChild.getId(), false);

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

    private void checkHighscore()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                "Highscores", MODE_PRIVATE
        );
        SharedPreferences.Editor editor = preferences.edit();

        long tmpScore = preferences.getLong(Integer.toString(MainActivity.rounds),
                MainActivity.scoreLong + 1);

        if (tmpScore > MainActivity.scoreLong)
        {
            editor.putLong(Integer.toString(MainActivity.rounds), MainActivity.scoreLong);
            editor.putString(Integer.toString(MainActivity.rounds) + "_str", score);
        }
        editor.apply();
    }

    public void endGame(View view)
    {
        chrono.stop();
        score = (String)chrono.getText();
        // Create preferences to check if highscore and save
        checkHighscore();
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        finish();
    }
}

