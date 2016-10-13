package s198739.example.com.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnterScore extends AppCompatActivity {
    private int round = 0;
    private int score = 0;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterscore);

        Intent intent = getIntent();
        int[] message = intent.getIntArrayExtra(GameWindow.EXTRA_MESSAGE);
        round=message[0];
        score= message[1];

        ((TextView)findViewById(R.id.highscore_result)).setText(""+score);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_score, menu);
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

        return super.onOptionsItemSelected(item);
    }
    public void insertScore(View view){
        DBHandler db = new DBHandler(this);
        name = ((EditText)findViewById(R.id.namn)).getText().toString();
       // ((TextView)findViewById(R.id.viss)).setText("name: "+name +"\nround: "+round +"\nscore: " + score);
        db.insertCorrectplacement(new Highscore(name, round, score));
        Intent intent = new Intent(this, HighscoreView.class);
        startActivity(intent);
        this.finish();

    }
}
