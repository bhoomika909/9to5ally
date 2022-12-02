package com.example.a001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAccount;
    EditText inputEmail,inputPassword,inputconfirmPassword;
    Button btnRegister;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputconfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegister = findViewById(R.id.btncheckin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performAuth();
            }
        });
    }

    private void performAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputconfirmPassword.getText().toString();

        if(!email.matches(EMAIL_PATTERN))
        {
           inputEmail.setError("Enter Correct Email !");
        }
        else if(password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter proper Password");
        }
        else if(!password.equals(confirmpassword))
        {
            inputconfirmPassword.setError("Password not Matched");
        }
        else
        {
            progressDialog.setMessage("Please wait while Registering User.......");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

                        Toast.makeText(RegisterActivity.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextNextActivity() {
        System.out.println("verify user inside sendUserToNextNextActivity ");
        Intent intent = new Intent(RegisterActivity.this,FrontPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this,Activity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}