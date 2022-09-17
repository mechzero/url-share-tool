package bulk.example.links;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

// Add Beautify & Copy to Clipboard
    //add Share Button

    String shareUrl;
    String protocol = "https";
    TextInputEditText inputTextField;
    Button beautifyButton, resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beautifyButton = findViewById(R.id.beautify);
        inputTextField = findViewById(R.id.inputText);
        resetButton = findViewById(R.id.resetButton);



        beautifyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

             int inputTextLength =  inputTextField.getText().length();

             String urlsThatIPastedIntoTextBox = inputTextField.getText().toString();

                // Clicking reset when input field is empty
                String resetFriendlyErrorText = "There's nothing to reset!";

                // Beautify & Copy when input field is empty
                String emptyFriendlyErrorText = "You didn't paste URLs!";

                String oracleErrorMessageText = "Error,click reset and try again";


               // Conditions when Beautfy & Copy, should not run
                boolean inputTextIsNotEmpty = inputTextLength !=0;

                boolean inputTextIsNotEqualToErrorMsgText =
                        inputTextField.getText().toString().equals(resetFriendlyErrorText);

                boolean inputTextIsNotEqualToEmptyErrorText =
                        inputTextField.getText().toString().equals(emptyFriendlyErrorText);

                boolean inputTextIsNotEqualToOracleErrorText =
                        inputTextField.getText().toString() == oracleErrorMessageText ;

                // these dont work
                boolean preconditions = inputTextIsNotEqualToEmptyErrorText
                        && inputTextIsNotEqualToErrorMsgText
                        && inputTextIsNotEmpty
                        && !inputTextIsNotEqualToOracleErrorText;


             if (inputTextIsNotEmpty) {


                    try {

                        StringBuilder builder = new StringBuilder();
                        String[] listOfURLs = urlsThatIPastedIntoTextBox.split(protocol);

                        for (int i = 1; i < listOfURLs.length; i++){
                            builder.append("\n"+"\n"+  protocol + listOfURLs[i]);

                        }
                        inputTextField.setText(builder.toString());


                        shareUrl = inputTextField.getText().toString();

                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clipData =  ClipData.newPlainText("label",inputTextField.getText().toString());

                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(MainActivity.this, "TextCopied", Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                    inputTextField.setText("Error,click reset and try again" );

                    }

                } else {
                    inputTextField.setText("You didn't paste URLs!");
                }


            }
        });


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputTextField.getText().length() !=0){
                inputTextField.setText("");
            } else {
                    inputTextField.setText("There's nothing to reset!");
                } }
        });

    }

          @Override
            public boolean onCreateOptionsMenu (Menu menu){
                getMenuInflater().inflate(R.menu.menu,menu);
                return super.onCreateOptionsMenu(menu);
            }

            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Watch these links! \n");
        intent.putExtra(Intent.EXTRA_TEXT, inputTextField.getText().toString());
        startActivity(Intent.createChooser(intent, "Share Via"));

        return super.onOptionsItemSelected(item);
            }

}