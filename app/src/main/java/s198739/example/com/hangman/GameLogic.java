package s198739.example.com.hangman;

/**
 * Created by simander on 25/08/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Random;

public class GameLogic {
    private Context context;

    private int counter = 0;

    private int lives = 6;
    private int score = 0;
    private int round = 0;

    private String[] wordlist;
    private boolean[] wordUsed;

    private String word;

    private LinkedList<Highscore> highscoreList = new LinkedList<>();

    //Constructor////////////////////////////////////////////////////////////////
    public GameLogic(Context context/*String[] wordlist*/) {
        //this.wordlist = wordlist
        this.context = context;
        this.wordlist = context.getResources().getStringArray(R.array.letteOrd);
        wordUsed = new boolean[wordlist.length];
        for (int i = 0; i < wordlist.length; i++) {
            wordUsed[i] = false;
        }

    }
    ///////////////////////////////////////////////////////////////////////////

    //Getters and Setters//////////////////////////////////////////////////////
    public void setCounter(int counter)
    {
        this.counter = counter;
    }
    public int getCounter()
    {
        return counter;
    }
    public void setLives(int lives)
    {
        this.lives = lives;
    }
    public int getLives()
    {
        return lives;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
    public int getScore()
    {
        return this.score;
    }

    public void setRound(int round)
    {
        this.round = round;
    }
    public int getRound()
    {
        return round;
    }

    public void setWord(String word)
    {
        this.word = word;
    }
    public String getWord()
    {
        return word;
    }
    //////////////////////////////////////////////////////////
    public String incrementRound() {
        round++;
        return "" + round;
    }

    //resets on new game
    public void resetValues() {
        score = 0;
        round = 0;
        counter = 0;
    }

    //resets on new round
    public void resetLives() {
        lives = 6;
        counter = 0;
    }




    public void resetCounter() {
        counter = 0;
    }

    public String getNewWord() {
        this.word = selectRandomWord(wordlist).toUpperCase();
        return GameLogic.generateString(word);
    }



    //velger ut et random ord fra listen
    public String selectRandomWord(String[] stringArray) {
        String[] words = stringArray;
        Random randomGen = new Random();
        int i = randomGen.nextInt(words.length);
        while (wordUsed[i] != false) {
            i = randomGen.nextInt(words.length);
        }
        wordUsed[i] = true;
        return words[i];
    }

    //returnerer en boolean som viser om det er seier.
    public Boolean victory() {
        if (lives <= 0) {
            return false;
        } else if (counter == word.length()) {
            return true;
        }
        return false;
    }

    //genererer en string av _ for hver bokstav i ordet, til grafisk visning
    public static String generateString(String s) {
        String test = s;
        String output = "";
        for (int i = 0; i < test.length(); i++) {
            output += "_ ";
        }
        return output;
    }

    //Tar 3 parameter: ordet som skal gjettes, tilstanden sålangt i spillet, og input av en bokstav
    public String updateString(String gs, char input) {
        String word1 = word;

        String graphWord = gs;
        String output = "";
        int oldCount = counter;

        int wi = 0;
        for (int gi = 0; gi < graphWord.length(); gi++) {
            switch (graphWord.charAt(gi)) {
                case '_':
                    if (word1.charAt(wi) == input) {
                        output += input;
                        counter++;

                    } else {
                        output += "_";
                    }
                    break;
                case ' ':
                    output += " ";
                    break;
                default:
                    output += word1.charAt(wi);
                    break;
            }
            if (gi % 2 == 0) {
                wi++;
            }
        }
        if (oldCount == counter) {
            lives--;

        } else {
            score++;
        }
        return output;
    }
}
