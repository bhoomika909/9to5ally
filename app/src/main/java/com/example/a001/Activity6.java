package com.example.a001;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Activity6 extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    TextView textView;
    TextView outputText;
    Button button;
    Button btnn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextToSpeech textToSpeech;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);
        textView = findViewById(R.id.txt);
        outputText = (TextView) findViewById(R.id.editTextTextMultiLine);
        button = findViewById(R.id.btn);
        btnn = findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

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
                performAuth();
                Intent intent = new Intent(Activity6.this, Activity8.class);
                startActivity(intent);
            }
        });
    }
    public void btnSpeech(View view) {
        System.out.println("Ayush Output check 1");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello Speak Something!!");
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1: if(resultCode==RESULT_OK && null!=data)
            {
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                System.out.println("Ayush Output check 0");
                Log.i(TAG, "Ayush Output check Local   " + result);
                GlobalVariable myAppClass = (GlobalVariable)getApplicationContext();
//saving the list
                myAppClass.setGlobalArrayList(result);
//getting the list
                ArrayList<String> globalArrayList = myAppClass.getGlobalArrayList();
                System.out.println(globalArrayList.size());
                Log.i(TAG, "Ayush Output check on activity 5  " + globalArrayList);
                outputText.setText(result.get(0));
                //System.out.println(outputText);
            }
                break;
        }
    }
    public void performAuth()
    {
        FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference.child("UserAnswer").child("user").child("2").setValue(outputText.getText().toString());
    }
}