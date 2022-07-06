package com.example.kitchenpal;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;

import com.example.kitchenpal.databinding.ActivityHandsFreeStepsBinding;

import org.w3c.dom.Text;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HandsFreeSteps extends AppCompatActivity implements RecognitionListener, View.OnClickListener {
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private SpeechRecognizer speech = null;
    private Intent intentRecognizer;
    private TextView textView;
    private int currStep = 0; //follow index of arraylist
    private final ArrayList<String> commands = new ArrayList<String>(Arrays.asList("next", "back"));
    private ArrayList<String> steps = new ArrayList<String>();

    private ImageView exitButton;
    private Button nextButton, backButton;
    private TextView stepNum;
    String LOG_TAG = "HandsFreeActivity";
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    private void resetSpeechRecognizer() {
        if (speech != null) {
            speech.destroy();
        }
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speech.setRecognitionListener(this);
        } else {
            finish();
        }
    }

    private void setIntentRecognizer() {
        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hands_free_steps);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Initialise UI
        exitButton = (ImageView) findViewById(R.id.exit_button);
        nextButton = (Button) findViewById(R.id.nextButton);
        backButton = (Button) findViewById(R.id.handsFreeBackButton);
        stepNum = (TextView) findViewById(R.id.step_number);
        stepNum.setText("Step 1");

        exitButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        Intent in = getIntent();
        steps = (ArrayList<String>) in.getExtras().getSerializable("step list");

        String thisStep = steps.get(currStep);
        setButtonsVisibility();
        textView = (TextView) findViewById(R.id.RecipeStep);
        textView.setText(thisStep);

        //start recogniser
        resetSpeechRecognizer();

        //check for permission
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

        setIntentRecognizer();
        speech.startListening(intentRecognizer);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                speech.startListening(intentRecognizer);
            } else {
                Toast.makeText(HandsFreeSteps.this, "Permission Denied!", Toast
                        .LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onResume() {
        Log.i(LOG_TAG, "onResume");
        super.onResume();
        resetSpeechRecognizer();
        speech.startListening(intentRecognizer);
    }

    @Override
    protected void onPause() {
        Log.i(LOG_TAG, "pause");
        super.onPause();
        speech.stopListening();
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG, "stop");
        super.onStop();
        if (speech != null) {
            speech.destroy();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "RmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        speech.stopListening();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null) {
            String currentCommand = matches.get(0).toLowerCase(Locale.ROOT);
            //get the index of the "next" keyword
            int indexNextKeyword = currentCommand.indexOf("next");
            int indexBackKeyword = currentCommand.indexOf("back");
            //command is "next"
            if (indexNextKeyword > -1) {
                toggleNext(textView);
            } else if (indexBackKeyword > -1) {
                toggleBack(textView);
            }
        }
        speech.startListening(intentRecognizer);
    }

    @Override
    public void onError(int errorCode) {
        Log.i(LOG_TAG, "FAILED" + errorCode);
        resetSpeechRecognizer();
        speech.startListening(intentRecognizer);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    //stop speech recognition, exit the page
    public void ExitButton(View view) {
        speech.stopListening();
    }

    //switch to next step
    @SuppressLint("SetTextI18n")
    public void toggleNext(View view) {
        this.currStep++;
        stepNum.setText("Step " + String.valueOf(currStep + 1));
        if (!(currStep > steps.size() - 1)) {
            textView.setText(steps.get(currStep));
        }
    }

    //switch to prev step
    @SuppressLint("SetTextI18n")
    public void toggleBack(View view) {
        this.currStep--;
        stepNum.setText("Step " + String.valueOf(currStep + 1));
        if(!(currStep < 0)) {
            textView.setText(steps.get(currStep));
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.exit_button:
                finish();
                break;
            case R.id.nextButton:
                toggleNext(view);
                setButtonsVisibility();
                break;
            case R.id.handsFreeBackButton:
                toggleBack(view);
                setButtonsVisibility();
                break;
        }
    }

    private void setButtonsVisibility() {
        if (currStep == 0) {
            exitButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        } else if (currStep == steps.size() - 1) {
            exitButton.setVisibility(View.INVISIBLE);
            backButton.setVisibility(View.VISIBLE);
            nextButton.setText("FINISH");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            exitButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }
}