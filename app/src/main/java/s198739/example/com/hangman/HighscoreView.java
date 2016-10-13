package s198739.example.com.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class HighscoreView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscoreview);

        DBHandler db = new DBHandler(this);




        String log = "";
        List<Highscore> highscores = db.finnAlleHighscores();

        for(Highscore high:highscores){


             log += "Name: "+high.getName()+",\t\tRound: "+high.getRound()+",\t\tScore: "+high.getPoints() + "\n";
        }
        ((TextView)findViewById(R.id.scores)).setText(log);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore_view, menu);
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
            super.finish();


        }

        return super.onOptionsItemSelected(item);
    }

    public void backToMain(View view){
        finish();
    }
}
