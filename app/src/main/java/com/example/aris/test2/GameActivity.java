package com.example.aris.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends Activity
{
    class PlaceHolder
    {
        public String animalName;
        public boolean visible;
        public int id;

        public PlaceHolder(String animalName, boolean visible, int id)
        {
            this.animalName = animalName;
            this.visible = visible;
            this.id = id;
        }
    }

    private ArrayList<PlaceHolder> mainList = new ArrayList<PlaceHolder>();
    ArrayList<String> animalList = new ArrayList<>(Arrays.asList("lion", "lion", "rhino",
            "rhino", "chimpanzee", "chimpanzee", "giraffe", "giraffe", "hippopotamus",
            "hippopotamus", "elephant", "elephant"));
    static int counter = 0;
    ViewGroup v;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        long seed = System.nanoTime();
        Collections.shuffle(animalList);
        refreshImages();

    }

    public void addToCounter(View view)
    {
        counter++;
        Button b = (Button)findViewById(R.id.ok_button);
        b.setText("" + counter);
    }

    public void goToResult(View view)
    {
        //Intent intent = new Intent(this, ResultActivity.class);
        //startActivity(intent);
        refreshImages();
    }

    public void refreshImages()
    {
        v = (ViewGroup)findViewById(R.id.imagesLayout);
        int cou = v.getChildCount();
        for (int i = cou - 1; i >= 0; --i)
        {
            ImageButton nextChild = (ImageButton) v.getChildAt(i);
            String imageString = animalList.remove(0);
            int resID = getResources().getIdentifier(imageString, "drawable", getPackageName());
            nextChild.setImageResource(resID);
            PlaceHolder placeHolder = new PlaceHolder(imageString, false, resID);
            mainList.add(placeHolder);

        }
    }

}
