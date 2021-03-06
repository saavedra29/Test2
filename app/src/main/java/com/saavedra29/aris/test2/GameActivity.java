package com.saavedra29.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private int rounds;
    private MChronometer chrono;
    private ArrayList<Integer> couple = new ArrayList<>();
    private ArrayList<String> animalList;
    private HashMap<Integer, Integer> relation = new HashMap<>();
    private HashMap<Integer, Boolean> faceCondition = new HashMap<>();
    private ViewGroup layoutView;
    public static long scoreLong;
    private int round;
    private String score;
    private boolean gameState;
    private int imagesNumber;
    private int invisibleObjects;
    // chronometer related variables
    private long timeBeforeStop;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                "Highscores", MODE_PRIVATE
        );
        rounds = preferences.getInt("rounds", 1);
        chrono = (MChronometer) findViewById(R.id.chronometer);
        round = 0;
        score = "";
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
                if ((invisibleObjects == imagesNumber - 2) &&
                        (round == rounds))
                {
                    // Stop chronometer
                    chrono.stop();
                    // Get the new score as time string
                    score = (String)chrono.getText();
                    // Create context to pass data to the next activity
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("scoreStr", score);
                    intent.putExtra("score", scoreLong);
                    // Start new activity and close the current
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
        indicator.setText(Integer.toString(round) + "/" + Integer.toString(rounds));
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
        long timeAfterStop = SystemClock.elapsedRealtime();
        difference = timeAfterStop - timeBeforeStop;
        chrono.setBase(chrono.getBase() + difference);
        chrono.start();
    }


    public void endGame(View view)
    {
        // Stop chronometer
        chrono.stop();
        // Get the new score as time string
        score = (String)chrono.getText();
        // Create context to pass data to the next activity
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("scoreStr", score);
        intent.putExtra("score", scoreLong);
        // Start new activity and close the current
        startActivity(intent);
        finish();
    }
}

