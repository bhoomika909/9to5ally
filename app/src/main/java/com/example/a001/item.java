package com.example.a001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class item {
    String subject, Name, Description,RealName,Status;

    public String getStatus(){return Status;}

    public String getRealName()
    {
        return RealName;
    }
    public String getSubject() {
        return subject;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

}
