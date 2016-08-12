package com.example.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import com.example.aris.test2.MChronometer;

public class GameActivity extends Activity
{
    class PlaceHolder
    {
        public String animalName;
        public boolean visible;
        public int id;
        public int index;
        public int imageId;

        public PlaceHolder(String animalName, boolean visible, int id, int index, int imageId)
        {
            this.animalName = animalName;
            this.visible = visible;
            this.id = id;
            this.index = index;
            this.imageId = imageId;
        }
    }

    public static final int NUM_OF_STAGES = 1;
    private ArrayList<Integer> couple;
    private ArrayList<PlaceHolder> mainList;
    private ArrayList<String> animalList;
    static int counter;
    private int gameState;
    private int imagesNumber;
    private ViewGroup v;
    private int invisibleObjects;
    private MChronometer chrono;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        chrono = (MChronometer) findViewById(R.id.chronometer);
        chrono.start();
        counter = 0;
        initStage();

    }

    void myOnClick(View view)
    {
        /*
        TextView v = (TextView)findViewById(R.id.testView);
        v.setText(Integer.toString(view.getId()));
        */
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
                    couple.add(0, tmpId);
                    // Add 1 to the game state
                    ++gameState;
                }
            }
        }
        else if (gameState == 1)
        {
            // Change the cover image with the real image
            int id = view.getId();
            if (id != couple.get(0))
            {
                for (int i = 0; i < imagesNumber; i++)
                {
                    int tmpId = mainList.get(i).id;
                    if (id == tmpId)
                    {

                        ImageButton button = (ImageButton) findViewById(view.getId());
                        button.setImageResource(mainList.get(i).imageId);
                        // Add the image to the viewable images
                        couple.add(1, tmpId);
                        // Add 1 to the game state
                        ++gameState;
                    }
                }
            }
        }
    }

    void goToResult(View view)
    {
        if (couple.size() == 2)
        {
            ImageButton button1 = null;
            ImageButton button2 = null;
            String animalName1 = null;
            String animalName2 = null;

            // Check for couple of same images
            for (int i = 0; i < imagesNumber; i++)
            {
                if (mainList.get(i).id == couple.get(0))
                {
                    button1 = (ImageButton)findViewById(mainList.get(i).id);
                    animalName1 = mainList.get(i).animalName;
                }
                if (mainList.get(i).id == couple.get(1))
                {
                    button2 = (ImageButton)findViewById(mainList.get(i).id);
                    animalName2 = mainList.get(i).animalName;
                }
            }

            if (Objects.equals(animalName1, animalName2))
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
                    if (counter == NUM_OF_STAGES)
                    {
                        chrono.stop();
                        Intent intent = new Intent(this, ResultActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        initStage();
                    }
                }
            }


            // Change the cover image back to cover
            for (int i = couple.size() - 1; i >= 0; i--)
            {
                ImageButton v = (ImageButton) findViewById(couple.get(i));
                v.setImageResource(getResources().getIdentifier("back", "drawable", getPackageName()));
            }
            couple.clear();
            gameState = 0;
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
                    "back", "drawable", getPackageName()));
            PlaceHolder placeHolder = new PlaceHolder(imageString, false, nextChild.getId(), i, resID);
            mainList.add(placeHolder);

        }
    }

    private void initStage()
    {
        // INITIALIZE ALL THE VARIALBES
        counter++;
        animalList = new ArrayList<>(Arrays.asList("lion", "lion", "rhino",
                "rhino", "chimpanzee", "chimpanzee", "giraffe", "giraffe", "hippopotamus",
                "hippopotamus", "elephant", "elephant"));
        Collections.shuffle(animalList);
        couple = new ArrayList<>();
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

}
