package com.example.a001;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;
import android.app.Application;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView createnewAccount;
    EditText inputEmail,inputPassword;
    Button btnLogin;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView buttonGoogle;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createnewAccount=findViewById(R.id.createNewAccount);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnLogin = findViewById(R.id.btnlogin);
        buttonGoogle = findViewById(R.id.buttonGoogle);

        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if(!email.matches(EMAIL_PATTERN))
                {
                    inputEmail.setError("Enter Correct Email !");
                }
                else if(password.isEmpty() || password.length()<6)
                {
                    inputPassword.setError("Enter proper Password");
                }
                else {
                    progressDialog.setMessage("Please wait while Logging User.......");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    String email = user.getEmail();
                                    String Authdomain = "admin.com";
                                    String domain = email.substring(email.indexOf("@") + 1);
                                    System.out.println("verify user " + email + " verify domain " + domain+"hi");

                                    if(domain.equals(Authdomain))
                                    {
                                        System.out.println("verify user inside if ");
                                        sendUserToNextNextActivity();
                                    }
                                    else
                                    {
                                        System.out.println("verify user inside else ");
                                        sendUserToNextActivity();
                                    }
                                }
                                Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
            private void sendUserToNextNextActivity() {
                System.out.println("verify user inside sendUserToNextNextActivity ");
                Intent intent = new Intent(MainActivity.this,FrontPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            private void sendUserToNextActivity() {
                Intent intent = new Intent(MainActivity.this,Activity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
