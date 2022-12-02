package com.example.a001;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Activity9 extends AppCompatActivity {

    TextView outputText;
    Button btn1;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9);

        outputText=(TextView) findViewById(R.id.txt_output);
        btn1=(Button) findViewById(R.id.btn);
        btn2=(Button) findViewById(R.id.next);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performAuth();
                Intent intent = new Intent(Activity9.this, Activity10.class);
                startActivity(intent);
            }
        });
    }

    public void btnSpeech(View view) {

        Intent intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hello Speak Something!!");
        try{
            startActivityForResult(intent,1);
        }catch(ActivityNotFoundException e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

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

                outputText.setText(result.get(0));

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
        databaseReference.child("UserAnswer").child("user").child("3").setValue(outputText.getText().toString());
    }
}