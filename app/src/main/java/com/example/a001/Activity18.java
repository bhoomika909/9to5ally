package com.example.a001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Activity18 extends AppCompatActivity {

    EditText adminreply;
    Button replyy;
    TextView Description;
    TextView DescriName;
    String recname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_18);
        adminreply = findViewById(R.id.givereply);
        replyy = findViewById(R.id.button);
        Description = findViewById(R.id.textView2);
        //DescriName = findViewById(R.id.received_value_id);
        // create the get Intent object
        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        String str = intent.getStringExtra("message_des");
        recname = intent.getStringExtra("message_name");
        // display the string into textView
        Description.setText(str);
        replyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAuth();
            }
        });
    }

    public void performAuth()
    {
        String adminresponse = adminreply.getText().toString();
        String response = "Your complain has been successfully lodged and Respond !!";
        FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "anaymous@gmail.com";
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
                Toast.makeText(Activity18.this,"Fail to read value",Toast.LENGTH_SHORT).show();
            }
        });
        if(adminreply.getText().toString().isEmpty())
        {
            adminresponse = "We are sorry you faced issue , we will take appropriate actions. ";
        }
        databaseReference.child("Admin").child(recname).child("Name").setValue(recname);
        databaseReference.child("Admin").child(recname).child("Reply").setValue(adminresponse);
        databaseReference.child("Users").child(recname).child("Status").setValue(adminresponse);
        Toast.makeText(Activity18.this,"Your Response Recorded",Toast.LENGTH_SHORT).show();
        sendUserToNextActivity();
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(Activity18.this,FrontPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}