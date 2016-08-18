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

public class GameActivity extends Activity
{
    class PlaceHolder
    {
        public int id;
        public int imageId;

        public PlaceHolder(int imageButtonId, int imageId)
        {
            this.id = imageButtonId;
            this.imageId = imageId;
        }
    }

    private ArrayList<Integer> newCouple;
    private ArrayList<Integer> oldCouple;
    private ArrayList<PlaceHolder> mainList;
    private ArrayList<String> animalList;
    static int counter;
    private int gameState;
    private int imagesNumber;
    private ViewGroup v;
    private int invisibleObjects;
    private MChronometer chrono;
    static String score;
    static long timeBeforeStop;
    static long timeAfterStop;
    static boolean firstTimeRunning;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        chrono = (MChronometer) findViewById(R.id.chronometer);
        chrono.start();
        counter = 0;
        score = "";
        firstTimeRunning = true;
        initStage();
        timeBeforeStop = SystemClock.elapsedRealtime();

    }



    public void myOnClick(View view)
    {
        if (gameState == 0)
        {
            // Change the cover image with the real image
            int id = view.getId();
            for (int i = 0; i < imagesNumber; i++)
            {
                int tmpId = mainList.get(i).id;
                if (id == tmpId)
                {

                    ImageButton button = (ImageButton)findViewById(view.getId());
                    button.setImageResource(mainList.get(i).imageId);
                    // Add the image to the viewable images
                    newCouple.add(0, tmpId);
                    // Add 1 to the game state
                    ++gameState;
                }
            }
        }
        else if (gameState == 1)
        {
            // Change the cover image with the real image
            int id = view.getId();
            if (id != newCouple.get(0))
            {
                for (int i = 0; i < imagesNumber; i++)
                {
                    int tmpId = mainList.get(i).id;
                    if (id == tmpId)
                    {

                        ImageButton button = (ImageButton) findViewById(view.getId());
                        button.setImageResource(mainList.get(i).imageId);
                        // Add the image to the viewable images
                        newCouple.add(1, tmpId);
                        // Add 1 to the game state
                        ++gameState;
                    }
                }
            }
        }
    }

    public void goToResult(View view)
    {
        if (newCouple.size() == 2)
        {
            if ((invisibleObjects == imagesNumber - 2) && (counter == MainActivity.stages))
            {
                score = (String)chrono.getText();
                chrono.stop();
                Intent intent = new Intent(this, ResultActivity.class);
                startActivity(intent);
                finish();
            }

            else
            {
                ImageButton button1 = null;
                ImageButton button2 = null;
                int imageId1 = 0;
                int imageId2 = 1;

                // Check for Couple of same images
                for (int i = 0; i < imagesNumber; i++)
                {
                    if (mainList.get(i).id == newCouple.get(0))
                    {
                        button1 = (ImageButton)findViewById(mainList.get(i).id);
                        imageId1 = mainList.get(i).imageId;
                    }
                    if (mainList.get(i).id == newCouple.get(1))
                    {
                        button2 = (ImageButton)findViewById(mainList.get(i).id);
                        imageId2 = mainList.get(i).imageId;
                    }
                }

                if (imageId1 == imageId2)
                {
                    // We got a pair
                    assert button1 != null;
                    button1.setVisibility(View.INVISIBLE);
                    invisibleObjects++;
                    assert button2 != null;
                    button2.setVisibility(View.INVISIBLE);
                    invisibleObjects++;
                    if (invisibleObjects == imagesNumber)
                    {
                        initStage();
                    }
                }


                // Change the cover image back to cover
                for (int i = newCouple.size() - 1; i >= 0; i--)
                {
                    ImageButton v = (ImageButton) findViewById(newCouple.get(i));
                    v.setImageResource(getResources().getIdentifier("cover", "drawable", getPackageName()));
                }
                newCouple.clear();
                gameState = 0;

            }
        }
    }

    private void refreshImages()
    {
        for (int i = imagesNumber - 1; i >= 0; --i)
        {
            ImageButton nextChild = (ImageButton) v.getChildAt(i);
            String imageString = animalList.remove(0);
            int resID = getResources().getIdentifier(imageString, "drawable", getPackageName());
            nextChild.setImageResource(getResources().getIdentifier(
                    "cover", "drawable", getPackageName()));
            PlaceHolder placeHolder = new PlaceHolder(nextChild.getId(), resID);
            mainList.add(placeHolder);

        }
    }

    private void initStage()
    {
        // INITIALIZE ALL THE VARIALBES
        counter++;
        TextView round = (TextView)findViewById(R.id.round_indicator);
        round.setText(Integer.toString(counter));
        animalList = new ArrayList<>(Arrays.asList("lion", "lion", "rhino",
                "rhino", "chimpanzee", "chimpanzee", "giraffe", "giraffe", "hippopotamus",
                "hippopotamus", "elephant", "elephant"));
        Collections.shuffle(animalList);
        newCouple = new ArrayList<>();
        oldCouple = new ArrayList<>();
        mainList = new ArrayList<>();
        gameState = 0;
        v = (ViewGroup)findViewById(R.id.imagesLayout);
        imagesNumber = v.getChildCount();
        for (int i = 0; i < imagesNumber; i++)
        {
            View imageButton = v.getChildAt(i);
            imageButton.setVisibility(View.VISIBLE);
        }
        invisibleObjects = 0;
        refreshImages();
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
