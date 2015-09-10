package corine.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainHangman extends AppCompatActivity {

    private ImageView hangmanImage;
    private TextView attemptCounter, wordToGuess, guessedLetters;
    private EditText guessLetterInput;
    private Button guessLetterSubmit, makeGuessWord;

    String guessed = "Letters guessed: ";
    Integer attempts = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hangman);

        hangmanImage = (ImageView) findViewById(R.id.hangmanImage);
        attemptCounter = (TextView) findViewById(R.id.attemptCounter);
        wordToGuess = (TextView) findViewById(R.id.wordToGuess);
        guessedLetters = (TextView) findViewById(R.id.guessedLetters);
        guessLetterInput = (EditText) findViewById(R.id.guessLetterInput);
        guessLetterSubmit = (Button) findViewById(R.id.guessLetterSubmit);
        makeGuessWord = (Button) findViewById(R.id.makeGuessWord);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_hangman, menu);
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

    public void onSubmitGuess(View view) {
        String guess = String.valueOf(guessLetterInput.getText());

        // keeps track of the letters that were guessed and displays them to the user
        guessed = guessed + guess;
        guessedLetters.setText(guessed);

        // keeps track of the attempts made by the users and displays this
        attempts = attempts - 1;
        attemptCounter.setText("attempts left: " + String.valueOf(attempts));

    }
}
