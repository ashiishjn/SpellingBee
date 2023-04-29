package com.example.spellingbee;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int pos;
    String word="";
    String allWords="";
    String[] letter = {"CDIUNOT","NDTWAIL","AFIONTX"};
    String[][] answers = {{"Conduction", "Conduit", "Induction", "Coconut", "Conduct", "Count", "Cutout", "Donut", "Duct",
            "Dunno", "Induct", "Intuit", "Intuition", "Noun", "Outdid", "Outdo", "Tout", "Tuition", "Tunic", "Tutti",
            "Tutu", "Udon", "Unction", "Uncut", "Undid", "Undo", "Union", "Unit", "Unto"},{"LAWN", "DAWN", "WAIT", "WIND",
            "WILD", "WILT", "WANT", "WATT", "TWIT", "WALL", "WAIL", "TWIN", "WILL", "WAND", "WANNA", "TWAIN", "AWAIT",
            "TWILL", "TWILIT", "NITWIT", "TAILWIND", "WILDLAND"},{"Fixation", "Afoot", "Anion", "Annotation", "Anoint",
            "Anon", "Anoxia", "Antitoxin", "Axon", "Finito", "Font", "Fontina", "Foot", "Info", "Initiation", "Into",
            "Intonation", "Iota", "Nation", "Nonfat", "Noon", "Notation", "Notion", "Onion", "Onto", "Tattoo",
            "Taxation", "Taxon", "Toon", "Toot", "Toxin"}};
    List<String> l = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pos = (int)(Math.random()*3);
        placeLetters();
        playMusic(R.raw.background_music);
    }
    public static MediaPlayer music;
    public void playMusic(int id)
    {
        music = MediaPlayer.create(MainActivity.this, id);
        music.setLooping(true);
        music.start();
    }
    public void placeLetters()
    {
        RelativeLayout relative_grid = findViewById(R.id.relative_grid);
        int i;
        Button button;
        for(i=0;i<7;i++)
        {
            button = (Button) relative_grid.getChildAt(i);
            button.setText(Character.toString(letter[pos].charAt(i)));
        }
    }

    public void tapIn(View v)
    {
        tapSound();
        Button button = findViewById(v.getId());
        word+=button.getText();
        display();
    }

    public void delete(View v)
    {
        tapSound();
        if(word.length() != 0)
        {
            word=word.substring(0,word.length()-1);
            display();
        }
    }

    public void display()
    {
        TextView tv = findViewById(R.id.word);
        tv.setText(word);
    }

    public void enter(View v)
    {
        tapSound();
        if(word.length() < 4) {
            Toast.makeText(this, "Too Short!", Toast.LENGTH_SHORT).show();
            word="";
            display();
        }
        else if(word.indexOf(letter[pos].charAt(3)) == -1)
        {
            Toast.makeText(this, "Missing center letter", Toast.LENGTH_SHORT).show();
            word="";
            display();
        }
        else if(l.contains(word))
        {
            Toast.makeText(this, "Already found", Toast.LENGTH_SHORT).show();
            word="";
            display();
        }
        else
        {
            int flag = 0;
            for(int i=0;i<answers[pos].length;i++)
            {
                if(word.equalsIgnoreCase(answers[pos][i]))
                {
                    Toast.makeText(this, "Good!", Toast.LENGTH_SHORT).show();
                    allWords+=word+", ";
                    TextView tv = findViewById(R.id.word_display);
                    tv.setText(allWords);
                    l.add(word);
                    flag = 1;
                    break;
                }
            }
            if(flag == 0)
            {
                Toast.makeText(this, "Not in word List", Toast.LENGTH_SHORT).show();
            }
            word="";
            display();
        }
    }

    public void shuffle(View v)
    {
        tapSound();
        List<Character> l = new ArrayList<>();
        for(int i=0;i<letter[pos].length();i++)
            l.add(letter[pos].charAt(i));
        l.remove(3);
        Collections.shuffle(l);
        String shuffled = "";
        for (char letter : l)
            shuffled += letter;
        letter[pos]=shuffled.substring(0,3)+letter[pos].charAt(3)+shuffled.substring(3,6);
        placeLetters();
    }
    public void tapSound()
    {
        MediaPlayer	media= MediaPlayer.create(getApplicationContext(), R.raw.tap_sound);
        media.start();
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            };
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
        music.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        music.start();
    }
}