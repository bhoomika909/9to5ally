package com.example.a001;
import android.app.Application;
import java.util.ArrayList;

public class GlobalVariable extends Application {


    String globalVariableOne;
    ArrayList<String> globalArrayList;

    public String getGlobalVariableOne()
    {
        return globalVariableOne;
    }
    public void setGlobalVariableOne(String globalVariableOne)
    {
        this.globalVariableOne = globalVariableOne;
    }
    public ArrayList<String> getGlobalArrayList() {
        return globalArrayList;
    }

    public void setGlobalArrayList(ArrayList<String> globalArrayList) {
        this.globalArrayList = globalArrayList;
    }
}
