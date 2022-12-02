package com.example.a001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity2 extends AppCompatActivity {

    private Button buttoncheckin;
    private Button buttoncomplain;
    private Button buttonlogout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //TextView username= (TextView) findViewById(R.id.username);
        buttoncheckin = (Button) findViewById(R.id.btncheckin);
        buttoncomplain = findViewById(R.id.btncomplain);
        buttonlogout = findViewById(R.id.btnlogout);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        buttoncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity2.this,Activity3.class);
                startActivity(intent);
            }
        });
        buttoncomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this,Activity15.class);
                startActivity(intent);
            }
        });
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Activity2.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}