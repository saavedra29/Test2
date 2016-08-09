package com.example.aris.test2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

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

    private ArrayList<Integer> couple;
    private ArrayList<PlaceHolder> mainList;
    private ArrayList<String> animalList;
    static int counter;
    int gameState;
    int imagesNumber;
    ViewGroup v;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // INITIALIZE ALL THE VARIALBES
        couple = new ArrayList<>();
        mainList = new ArrayList<>();
        animalList = new ArrayList<>(Arrays.asList("lion", "lion", "rhino",
                "rhino", "chimpanzee", "chimpanzee", "giraffe", "giraffe", "hippopotamus",
                "hippopotamus", "elephant", "elephant"));
        counter = 0;
        gameState = 0;
        Collections.shuffle(animalList);
        v = (ViewGroup)findViewById(R.id.imagesLayout);
        imagesNumber = v.getChildCount();
        Button button = (Button)findViewById(R.id.ok_button);
        //button.setVisibility(View.INVISIBLE);
        refreshImages();


    }

    public void myOnClick(View view)
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

    public void goToResult(View view)
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
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
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

    public void refreshImages()
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

}
