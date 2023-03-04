package com.example.a001;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Activity14 extends AppCompatActivity {

    Button goback;
    private NLClassifier textClassifier;
    private ExecutorService executorService;
    private static final String TAG = "TextClassificationDemo";
    private TextView resultTextView;
    //private EditText inputEditText;
    //private ScrollView scrollView;
    private Button predictButton;
    public String response = "";
    public float negative = 0;
    public float positive = 0;

    ImageView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14);
        executorService = Executors.newSingleThreadExecutor();
        goback = findViewById(R.id.button4);
        predictButton = findViewById(R.id.button5);
        resultTextView = findViewById(R.id.txt123);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity14.this, Activity2.class);
                startActivity(intent);
            }
        });
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //predictButton.setVisibility(View.GONE);
                //goback.setVisibility(View.VISIBLE);
                Toast.makeText(
                                Activity14.this,
                                "Processing........",
                                Toast.LENGTH_LONG)
                        .show();
                Toast.makeText(
                                Activity14.this,
                                "Analysing........",
                                Toast.LENGTH_LONG)
                        .show();
                  /////Database script start///////////////////
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserAnswer");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        response += snapshot.child("user").child("1").getValue().toString() + " ";
                        response += snapshot.child("user").child("2").getValue().toString() + " ";
                        response += snapshot.child("user").child("3").getValue().toString() + " ";
                        response += snapshot.child("user").child("4").getValue().toString() + " ";
                        response += snapshot.child("user").child("5").getValue().toString() ;
                        Log.d("Ayush0",response);
                        classify(response);

//                        if (response.equals("Your Complain is Pending !!")) {
//                            animation.setImageResource(R.drawable.pendingforproject);
//                        } else {
//                            animation.setImageResource(R.drawable.checkforproject);
//                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Activity14.this, "We are Sorry, Server Down", Toast.LENGTH_SHORT).show();
                    }
                });
                /////Database script end////////////////////
                Log.d("Ayush",response);
                System.out.println(response);
            }
        });
        downloadModel("sentiment_analysis");
    }
            private void classify(final String text) {
                executorService.execute(
                        () -> {
                            // TODO 7: Run sentiment analysis on the input text
                            List<Category> results = textClassifier.classify(text);
                            // TODO 8: Convert the result to a human-readable text
                            String textToShow = "Input: " + text + "\nOutput:\n";
                            for (int i = 0; i < results.size(); i++) {
                                Category result = results.get(i);
                                if(i == 0)
                                {
                                    Log.d("Ayush negative",result.getLabel());
                                    negative = result.getScore();
                                }
                                else
                                {
                                    Log.d("Ayush positive",result.getLabel());
                                    positive = result.getScore();
                                    System.out.println("Double Number: " + String.format("%.2f", positive));
                                    String str = String.format("%.2f", positive);
                                    positive = (Float.parseFloat(str))*100;
                                }
                                Log.d("Ayush result.getLabel()",result.getLabel());
                                Log.d("Ayush result.getScore()", String.valueOf(result.getScore()));

                                textToShow += String.format("    %s: %s\n", result.getLabel(),result.getScore());
                            }
                            textToShow += "---------\n";
                            // Show classification result on screen
                            Log.d("Ayush positive final value", String.valueOf(positive));
                            showResult(ouputbasedresult(positive,textToShow));
                        });
            }

    private String ouputbasedresult(float val ,String textToShow) {
        if( val <=10)
            textToShow += "It seems like things are not working out and youre feeling overwhelmed with the current situations. Please contact your in office counsellor to work through your problems, and let your teammates know how they can support you better with the work environment. It is necessary for us to pay attention to our well being while working too!";
        else if (val>10 && val <=20)
            textToShow +="You should acknowledge that things seem hard right now. It is okay to feel this way, and also important to give yourself the required care. Indulge yourself in building positive habits, and rely on your team to work together through this phase!";
        else if(val>20 && val<=30)
            textToShow +="Things seem tough but do not be helpless now. Relay your worries to your seniors, speak up to your teammates about how you can work together better and go with the flow! You will reach a good point with satisfaction at your work place.";
        else if (val>30 && val<=40)
            textToShow +="It sounds like you are somewhat satisfied with your workplace, but there may still be some areas that could be improved. Consider having an open and honest conversation with your supervisor or HR representative to address any concerns you may have.";
        else if (val>40 && val <=50)
            textToShow +="For you, the positives seem well balanced with the negatives at your work place! While this may not be a bad thing, consider how you can grow, or how your workplace can support you better and relay your suggestions, we are always open to hearing new ideas!";
        else if(val>50 && val<=60)
            textToShow +="Your outlook towards the workplace is quite neutral! It might be time to spice things up by  learning some new skills and growing your career, or you can take some time too draft suggestions that will support you better at the work place and let us know!";
        else if (val>60 && val<=70)
            textToShow +="we are glad the positives seem to outweigh the negatives at your work place! To help us improve, let us know what we can do better, and try to focus on thing you like about your work, shape your skillset in the direction of your career goals and remember to take a breather!";
        else if (val>70 && val <=80)
            textToShow +="It's great to hear that you are mostly satisfied with your workplace! However, there is always room for improvement. Consider talking with your supervisor or HR representative about ways to continue growing and developing within your current role.";
        else if(val>80 && val<=90)
            textToShow +="We are happy to see you so satisfied with your environment! You can keep looking for opportunities to get involved in projects or initiatives that align with your interests and career goals. Keep on maintaining a positive attitude and building relationships with your colleagues to foster a supportive and enjoyable work environment.";
        else
            textToShow +="We are glad to see you doing so well at the work place! Let us know what we are doing right/what we could do better by dropping an email to the HR time any time!";
        return textToShow;
    }

    private void showResult(final String textToShow) {
                // Run on UI thread as we'll updating our app UI
                runOnUiThread(
                        () -> {
                            // Append the result to the UI.
                            resultTextView.append(textToShow);

                            // Clear the input text.
                            //inputEditText.getText().clear();
                            response = "";

                        });
            }

            /**
             * Download model from Firebase ML.
             */
            private synchronized void downloadModel(String modelName) {
                CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();
                FirebaseModelDownloader.getInstance()
                        .getModel("sentiment_analysis", DownloadType.LOCAL_MODEL, conditions)
                        .addOnSuccessListener(model -> {
                            try {
                                // TODO 6: Initialize a TextClassifier with the downloaded model
                                textClassifier = NLClassifier.createFromFile(model.getFile());
                                predictButton.setEnabled(true);
                                Toast.makeText(
                                                Activity14.this,
                                                "Your report generated, Please Check.",
                                                Toast.LENGTH_LONG)
                                        .show();
                            } catch (IOException e) {
                                Log.e(TAG, "Failed to initialize the model. ", e);
                                Toast.makeText(
                                                Activity14.this,
                                                "Model initialization failed.",
                                                Toast.LENGTH_LONG)
                                        .show();
                                predictButton.setEnabled(false);
                            }
                        })
                        .addOnFailureListener(e -> {
                                    Log.e(TAG, "Failed to download the model. ", e);
                                    Toast.makeText(
                                                    Activity14.this,
                                                    "Model download failed, please check your connection.",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                }
                        );

            }
        }
