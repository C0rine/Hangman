package corine.hangman;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainHangman extends AppCompatActivity {

    private ImageView hangmanImage;
    private TextView attemptCounter, wordToGuess, guessedLetters;
    private EditText guessLetterInput;
    private Button guessLetterSubmit, makeGuessWord;

    Bundle bundle = new Bundle();

    private String[] words;
    private String guessed = "Letters guessed: \n";
    private Integer attempts = 6;
    private String randomword = "randomword here";
    private Character[] randomwordmask;
    private Integer lengthrandomword;
    private String guessingstatus = "";


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

        // load in a random word to guess and set it to underscores
        randomword = getRandomWord();
        wordToGuess.setText(randomword);
        resetGuessStatus();

    }


    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        // create a bundle of information needed to retain status on orientation change
        bundle.putInt("attempts", attempts);
        bundle.putString("guessedletters", guessed);
        bundle.putString("randomword", randomword);
        bundle.putString("guessingstatus", guessingstatus);

    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        // retrieve information from bundle to rebuild status on orientation change
        guessed = bundle.getString("guessedletters");
        guessedLetters.setText(guessed);

        attempts = bundle.getInt("attempts");
        attemptCounter.setText("attempts left: " + String.valueOf(attempts));

        randomword = bundle.getString("randomword");
        wordToGuess.setText(randomword);

        guessingstatus = bundle.getString("guessingstatus");
        wordToGuess.setText(guessingstatus);
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

    // Generates a random word for the user to guess
    public String getRandomWord(){

        words = getResources().getStringArray(R.array.words);
        int arraylength = words.length;
        int random = (int)(Math.random() * arraylength);
        return words[random];

    }

    // Reset guessing status: set word to guess to all underscores
    public void resetGuessStatus(){

        guessingstatus = "";
        lengthrandomword = randomword.length();
        randomwordmask = new Character[lengthrandomword];

        // make a char array of underscores
        for (int i = 0; i < lengthrandomword; i++){
            randomwordmask[i] = '_';
        }

        // convert char array to string
        for (Character c : randomwordmask){
            guessingstatus += c.toString();
        }

        wordToGuess.setText(guessingstatus);

    }

    // checks guessed letter and replaces relevant underscores to the letter
    public void checkGuess(String letter){

        guessingstatus = "";

        char tocheck = letter.charAt(0);
        char[] randomwordarray = randomword.toCharArray();

        for (int i = 0; i < randomword.length(); i++){
            if (randomwordarray[i] == tocheck){
                randomwordmask[i] = tocheck;
            }
        }

        for (Character c : randomwordmask){
            guessingstatus += c.toString();
        }

        wordToGuess.setText(guessingstatus);
    }

    // user can guess the full word (unfortunately not enough time to fully implement)
    public void makeGuessWord(View view) {

        Toast.makeText(getApplicationContext(), "not yet implemented, not enough time... :( " +
                "the word you tried to guess was " + "\"" + randomword + "\"", Toast.LENGTH_LONG).show();

    }

    // handles all the events that need to happen when the user makes a guess
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
            // check guess letter with the word and update that textview
            checkGuess(guess);

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

                    // show the word the user had to guess
                    Toast.makeText(getApplicationContext(), "The word you tried to guess was "
                            + "\"" + randomword + "\"", Toast.LENGTH_LONG).show();

                   // Prepare a new game

                    // get new random word
                    randomword = getRandomWord();
                    wordToGuess.setText(randomword);

                    // reset guess status
                    resetGuessStatus();

                    // reset attempt status
                    attempts = 6;
                    attemptCounter.setText("attempts left: " + String.valueOf(attempts));

                    // reset guessed letters
                    guessed = "Letters guessed: \n";
                    guessedLetters.setText(guessed);

                    // reset hangman image
                    hangmanImage.setImageResource(R.mipmap.hangman0);


                    break;
            }
        }



    }

}
