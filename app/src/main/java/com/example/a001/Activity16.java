package com.example.a001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Activity16 extends AppCompatActivity {

    EditText namee;
    CheckBox isanaymous;
    EditText complainsub;
    EditText complain;
    ImageView writingimage;
    //ImageView stopimage;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button submitbtn;
    String anaymous = "No";
    String temp = "Yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16);
        namee = (EditText) findViewById(R.id.complainname);
        complainsub = (EditText) findViewById(R.id.subtext);
        complain = (EditText) findViewById(R.id.comlaintext);
        isanaymous = (CheckBox) findViewById(R.id.beanonyms);
        writingimage = (ImageView) findViewById(R.id.writeanim);
        submitbtn = (Button) findViewById(R.id.btnsubmitcomplain);
        //stopimage = (ImageView)findViewById(R.id.stopanim);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        namee.addTextChangedListener(new TextWatcher()
        {
            @Override
            // when there is no text added
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    //stopimage.setVisibility(View.VISIBLE);
                    // set text to Not typing
                } else {
                    // set text to typing
                    writingimage.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                writingimage.setVisibility(View.VISIBLE);
            }
            // after we input some text
            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.toString().trim().length() == 0)
                {
                    writingimage.setVisibility(View.GONE);
                    // set text to Stopped typing
                    //stopimage.setVisibility(View.VISIBLE);
                }
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAuth();
            }
        });
    }
    public void Check(View view)
    {
        if(isanaymous.isChecked())
        {
            anaymous = "Yes";
        }
        Toast.makeText(this,"Your Identity Hidden", Toast.LENGTH_LONG).show();
    }
    public void performAuth()
    {
        String username = namee.getText().toString();
        String usersub = complainsub.getText().toString();
        String usercomplain = complain.getText().toString();
        String email = "anaymous@gmail.com";
        if(isanaymous.isChecked())
        {
            username = "Anonymous";
        }

        FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            email = user.getEmail();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity16.this,"Fail to read value",Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.child("Users").child(namee.getText().toString()).child("Name").setValue(username);
        databaseReference.child("Users").child(namee.getText().toString()).child("Status").setValue("Your Complain is Pending !!");
        databaseReference.child("Users").child(namee.getText().toString()).child("RealName").setValue(namee.getText().toString());
        databaseReference.child("Users").child(namee.getText().toString()).child("Email").setValue(email);
        databaseReference.child("Users").child(namee.getText().toString()).child("subject").setValue(complainsub.getText().toString());
        databaseReference.child("Users").child(namee.getText().toString()).child("Description").setValue(complain.getText().toString());
        databaseReference.child("Admin").child(namee.getText().toString()).child("Name").setValue(namee.getText().toString());
        databaseReference.child("Admin").child(namee.getText().toString()).child("Reply").setValue("Your Complain is Pending !!");
        Toast.makeText(Activity16.this,"Your Complain Registered",Toast.LENGTH_SHORT).show();
        sendUserToNextActivity();
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(Activity16.this,Activity15.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}