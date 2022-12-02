package com.example.a001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity15 extends AppCompatActivity {

    private Button btnfilecomplain;
    private Button btncheckcomplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_15);
        btnfilecomplain = findViewById(R.id.btnfilecomplain);
        btncheckcomplain = findViewById(R.id.btncomplainstatus);

        btnfilecomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity15.this,Activity16.class);
                startActivity(intent);
            }
        });

        btncheckcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity15.this,Activity17.class);
                startActivity(intent);
            }
        });

    }
}