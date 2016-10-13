package s198739.example.com.hangman;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class GameWindow extends AppCompatActivity {
    //TILSTANDSVARIABLER///////////////////////////////////////////////////////
    private static final String STATE_WORD = "word_var";
    private static final String STATE_COUNTER = "counter_var";
    private static final String STATE_SCORE = "playerScor_var";
    private static final String STATE_ROUND = "playerRound_var";
    private static final String STATE_GUESS = "playerGuess_var";
    private static final String STATE_GFXCOUNT = "gfxCounter_var";
    private static final String STATE_LIVES = "lives_var";
    private static final String STATE_BUTTONS = "buttons_var";
    private static final String STATE_BUTTONSTATE = "buttonstate_var";
    ///////////////////////////////////////////////////////////////////////////

    //HIGHSCORE RESULTAT///////////////////////////////////////////////////////
    public final static String EXTRA_MESSAGE = "scoredata";
    ///////////////////////////////////////////////////////////////////////////

    private String word;
    private GameLogic gameLogic;
    String[] wordlist;
    //TextView message1;
    LinkedList<View>knappar = new LinkedList<>();
    LinkedList<Boolean>knappar_tilstand = new LinkedList<>();
    TypedArray GRAPHICS_HANGMAN;
    TypedArray GRAPHICS_LIVES;
    Resources res;  //referanse til ressursene i resource mappa.
    ImageView GFXPANEL_HANGMAN;  //referanse til hangmangrafikken
    ImageView GFXPANEL_LIVES;   //referanse til grafisk fremstilling av sjanser/liv
    TextView scoregfx;
    TextView roundNR;
    View keyboard;
    View keyboardB;
    View nuRoundscreen;
    View gameOverscreen;


    int graphicCounter = 0; //Holder styr på hvilken grafikk som skal vises

    //viser hvilke bokstaver som alt er gjettet, og hvilke som mangler.
    private TextView guessProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamewindow);


        //  wordlist = getResources().getStringArray(R.array.letteOrd);

        //message1 = (TextView) findViewById(R.id.message);
        guessProgress = (TextView) findViewById(R.id.resultGraph);
        res = getResources();
        GRAPHICS_HANGMAN = res.obtainTypedArray(R.array.imgs);
        GRAPHICS_LIVES = res.obtainTypedArray(R.array.lives);
        GFXPANEL_HANGMAN = ((ImageView) findViewById(R.id.graphic));
        GFXPANEL_LIVES = ((ImageView) findViewById(R.id.lifegfx));
        scoregfx = ((TextView)findViewById(R.id.points));
        roundNR = (TextView) findViewById(R.id.roundNr);
        keyboard = findViewById(R.id.keyboard);
        nuRoundscreen = findViewById(R.id.nuRoundScreen);
        gameOverscreen = findViewById(R.id.gameOverScreen);
        gameLogic = new GameLogic(this);



        // Sjekker om det er lagret en tidligere tilstand
        if (savedInstanceState != null) {
            //gjenoppretter lagrede tilstandsverdier
            gameLogic.setWord(savedInstanceState.getString(STATE_WORD));
            gameLogic.setCounter(savedInstanceState.getInt(STATE_COUNTER));
            gameLogic.setScore(savedInstanceState.getInt(STATE_SCORE));
            gameLogic.setRound(savedInstanceState.getInt(STATE_ROUND));
            gameLogic.setLives(savedInstanceState.getInt(STATE_LIVES));
            graphicCounter = savedInstanceState.getInt(STATE_GFXCOUNT);

            guessProgress.setText(savedInstanceState.getString(STATE_GUESS));
            int[] arr = savedInstanceState.getIntArray(STATE_BUTTONS);
            boolean[]arrval = savedInstanceState.getBooleanArray(STATE_BUTTONSTATE);

            if(arr != null){
            for(int i = 0; i < arr.length; i++){
                View tmpview = findViewById(arr[i]);
                knappar.add(findViewById(arr[i]));
                knappar_tilstand.add(arrval[i]);
                if(arrval[i]== true){
                    tmpview.setBackgroundResource(R.drawable.buttonok1);
                }
                else if(arrval[i]==false){
                    tmpview.setBackgroundResource(R.drawable.buttonnotok1);
                }
                tmpview.setEnabled(false);
            }}
            update();

        }
        else {
            // Probably initialize members with default values for a new instance
            guessProgress.setText(gameLogic.getNewWord());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hangman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.menu_exit){
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState (Bundle state){
        state.putString(STATE_WORD, gameLogic.getWord());       //Lagrer ordet som er i spill
        state.putInt(STATE_SCORE, gameLogic.getScore());        //Lagrer antall poeng.
        state.putInt(STATE_COUNTER, gameLogic.getCounter());    //Lagrer telleverdien.
        state.putInt(STATE_ROUND, gameLogic.getRound());        //lagrer hvilken runde man er kommet til
        state.putInt(STATE_LIVES, gameLogic.getLives());        //lagrer antall liv
        state.putInt(STATE_GFXCOUNT, this.graphicCounter);      //lagrer tilstanden til grafikk
        state.putString(STATE_GUESS, guessProgress.getText().toString());   //Lagrer gjettestringen

         int[] arr = new int[knappar.size()];
        boolean[] arrval = new boolean[knappar_tilstand.size()];
        for(int i = 0; i < knappar.size(); i++){
           arr[i] = knappar.get(i).getId();
            arrval[i] = knappar_tilstand.get(i);
       }


            state.putIntArray(STATE_BUTTONS, arr);
            state.putBooleanArray(STATE_BUTTONSTATE, arrval);

            super.onSaveInstanceState(state);

    }
    //oppdaterer grafikken
    public void update() {
        scoregfx.setText("" + gameLogic.getScore());
        roundNR.setText("" + gameLogic.getRound());

        if(gameLogic.getLives()<=0)
        {
            GFXPANEL_LIVES.setImageDrawable(null);
            GFXPANEL_HANGMAN.setImageDrawable(getResources().getDrawable(R.drawable.hangpic7));
        }
        else if(gameLogic.victory()){
            GFXPANEL_HANGMAN.setImageDrawable(getResources().getDrawable(R.drawable.victory));
        }
        else
        {
            Drawable livedraw = GRAPHICS_LIVES.getDrawable(graphicCounter);
            GFXPANEL_LIVES.setImageDrawable(livedraw);
            Drawable draw = GRAPHICS_HANGMAN.getDrawable(graphicCounter);
            GFXPANEL_HANGMAN.setImageDrawable(draw);
        }
    }


    //Det som skjer når en bokstav klikkes
    public void selectLetter(View view) {
        knappar.add(view);
        int oldCount = gameLogic.getCounter();  //reads the count of correct letters
        String input = ((Button) view).getText().toString();
        char bokstav = input.charAt(0);
        view.setEnabled(false);

        String resultGuesses = guessProgress.getText().toString();

        String output = gameLogic.updateString(resultGuesses, bokstav);
        guessProgress.setText(output);

        //IF THE LETTER WAS NOT IN THE WORD
        if (gameLogic.getCounter() == oldCount) {

            if(graphicCounter < 5) {
                graphicCounter++;
                knappar_tilstand.add(false);
                view.setBackgroundResource(R.drawable.buttonnotok1);
            }
        }
        else{
            knappar_tilstand.add(true);
            view.setBackgroundResource(R.drawable.buttonok1);
        }
        if (gameLogic.getLives() <= 0) {
            view.setBackgroundResource(R.drawable.buttonnotok1);
            knappar_tilstand.add(false);

            gameOver();
        }
        else if (gameLogic.victory()==true){
            onVictory();
        }
        update();
    }
    //tilbakestiller knapper
    public void resetButtons(){
        while(knappar.size()>0) {
            knappar.getFirst().setBackgroundResource(R.drawable.button1);
            knappar.getFirst().setEnabled(true);
            knappar.pop();
            gameLogic.resetCounter();
        }
    }


        public void newRound(View view){
            gameLogic.resetLives();
            String thisRound = gameLogic.incrementRound();
            roundNR.setText(thisRound);
            guessProgress.setText(gameLogic.getNewWord());
            resetButtons();
            graphicCounter = 0;
            update();
            nuRoundscreen.setVisibility(View.INVISIBLE);
            keyboard.setVisibility(View.VISIBLE);
        }
    public void newGame(View view){
        newRound(view);
        gameLogic.resetValues();
        scoregfx.setText("" + gameLogic.getScore());
        roundNR.setText("" + gameLogic.getRound());
        gameOverscreen.setVisibility(View.INVISIBLE);
    }

        public void gameOver(){
            DBHandler db = new DBHandler(this);
            if(gameLogic.getScore() > 0){
                Intent intent = new Intent(this, EnterScore.class);
                int[] message = {gameLogic.getRound(), gameLogic.getScore()};
                intent.putExtra(EXTRA_MESSAGE, message);

                /*try {

                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                startActivity(intent);
                this.finish();
            }
            else {
                guessProgress.setText("GAME OVER!");
                ((TextView) findViewById(R.id.ordetvar)).setText(gameLogic.getWord());

                    keyboard.setVisibility(View.INVISIBLE);
                    gameOverscreen.setVisibility(View.VISIBLE);

            }

        }
        public void exit(View view){
           finish();
        }

    public void onVictory() {
        GFXPANEL_HANGMAN.setImageDrawable(getResources().getDrawable(R.drawable.victory));
        keyboard.setVisibility(View.INVISIBLE);
        nuRoundscreen.setVisibility(View.VISIBLE);


    }
}