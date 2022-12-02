package com.example.a001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.a001.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Activity5 extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    TextView outputText;
    Button btn1;
    Button btn2;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Ayush Output check 2");
        setContentView(R.layout.activity_5);
        System.out.println("Ayush Output check 3");
        outputText = (TextView) findViewById(R.id.txt_output);
        btn1 = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.next);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //firebaseDatabase = FirebaseDatabase.getInstance();
        //Log.i(TAG, "Ayush Output check firebase   " + firebaseDatabase);
        //ref = firebaseDatabase.getReference().child("User_Response");
        //activity5 = new GlobalVariable();



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer=outputText.getText().toString();
                GlobalVariable myAppClass = (GlobalVariable)getApplicationContext();
                ArrayList<String> globalArrayList = myAppClass.getGlobalArrayList();
                System.out.println("Ayush Output click submit in activity 5");
                Log.i(TAG, "Ayush Output Send DATA TO FIREBASE ON CLICK SUBMIT  " + answer);
                performAuth();
                Intent intent = new Intent(Activity5.this, Activity6.class);
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
        databaseReference.child("UserAnswer").child("user").child("1").setValue(outputText.getText().toString());
    }
}

