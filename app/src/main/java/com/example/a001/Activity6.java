package com.example.a001;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Activity6 extends AppCompatActivity {

    TextView textView;

    Button button;
    Button btnn;
    TextToSpeech textToSpeech;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);
        textView = findViewById(R.id.txt);
        button = findViewById(R.id.btn);
        btnn = findViewById(R.id.submit);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Ayush Output Activity 6 click 2");
                textToSpeech.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Ayush Output Activity 6 click 2");
                String TAG = "MyActivity";
                GlobalVariable myAppClass = (GlobalVariable)getApplicationContext();
                ArrayList<String> globalArrayList = myAppClass.getGlobalArrayList();
                Log.i(TAG, "Ayush Output check Global varibale on submit activity 6  " + globalArrayList);
                Intent intent = new Intent(Activity6.this, Activity7.class);
                startActivity(intent);
            }
        });
    }
}