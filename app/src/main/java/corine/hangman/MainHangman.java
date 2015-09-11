package corine.hangman;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainHangman extends AppCompatActivity {

    private ImageView hangmanImage;
    private TextView attemptCounter, wordToGuess, guessedLetters;
    private EditText guessLetterInput;
    private Button guessLetterSubmit, makeGuessWord;

    Bundle bundle = new Bundle();

    public String guessed = "Letters guessed: \n";
    Integer attempts = 6;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
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
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        // create a bundle of information needed to retain status on orientation change
        bundle.putInt("attempts", attempts);
        bundle.putString("guessedletters", guessed);

    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        // retrieve information from bundle to rebuild status on orientation change
        guessed = bundle.getString("guessedletters");
        guessedLetters.setText(guessed);

        attempts = bundle.getInt("attempts");
        attemptCounter.setText("attempts left: " + String.valueOf(attempts));
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

        // making sure user input is appropriate
        if (guess.length() < 0){
            Toast.makeText(getApplicationContext(), "You have not provided input!",
                    Toast.LENGTH_SHORT).show();
        }
        else if (guess.length() > 1){
           Toast.makeText(getApplicationContext(), "You can only guess one character at a time",
                   Toast.LENGTH_SHORT).show();
        }
        else if (!Character.isLetter(guess.charAt(0))){
            Toast.makeText(getApplicationContext(), "You can only guess letters",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // keeps track of the letters that were guessed and displays them to the user
            guessed = guessed + guess;
            guessedLetters.setText(guessed);

            // keeps track of the attempts made by the users and displays this
            attempts = attempts - 1;
            attemptCounter.setText("attempts left: " + String.valueOf(attempts));

            // create a bundle of information needed to retain status on orientation change
            bundle.putInt("attempts", attempts);
            bundle.putString("guessedletters", guessed);

            // change hangman image to appropriate guess attempts left
            switch (attempts){
                case 6: hangmanImage.setImageResource(R.mipmap.hangman0);
                    break;
                case 5: hangmanImage.setImageResource(R.mipmap.hangman1);
                    break;
                case 4: hangmanImage.setImageResource(R.mipmap.hangman2);
                    break;
                case 3: hangmanImage.setImageResource(R.mipmap.hangman3);
                    break;
                case 2: hangmanImage.setImageResource(R.mipmap.hangman4);
                    break;
                case 1: hangmanImage.setImageResource(R.mipmap.hangman5);
                    break;
                case 0: hangmanImage.setImageResource(R.mipmap.hangman6);
                    // Game Over --> start message in Dialog box
                    DialogFragment gameOver = new MyDialogFragment();
                    gameOver.show(getFragmentManager(), "theDialog");
                    break;
            }
        }



    }
}
