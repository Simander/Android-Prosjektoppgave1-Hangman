package s198739.example.com.hangman;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Locale sprak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate the menu items for use in the action bar

        //getSupportActionBar().setIcon(R.drawable.icon1);
       // ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
       // return true;
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
        if(id == R.id.sprak_norsk){
            Toast.makeText(this.getApplicationContext(),
                    "Du har valgt Norsk", Toast.LENGTH_SHORT)
                    .show();
            setLocale("nb");
        }
        else if(id == R.id.sprak_english){
            Toast.makeText(this.getApplicationContext(),
                    "You have selected English", Toast.LENGTH_SHORT)
                    .show();
            setLocale("en");
        }
        else if(id == R.id.menu_exit){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void newGame(View view){
        Intent intent = new Intent(this, GameWindow.class);
        startActivity(intent);
    }
    public void Highscores(View view){
        Intent intent = new Intent(this, HighscoreView.class);
        startActivity(intent);
    }
    public void rules(View view){
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    //setter språk
    public void setLocale(String sprak) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(sprak);
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
}
