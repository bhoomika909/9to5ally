package com.example.a001;
import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import org.tensorflow.lite.Interpreter;

        import java.io.File;

public class FrontPage extends AppCompatActivity {

    private Button button;
    Interpreter tflite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correct
                    Toast.makeText(FrontPage.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    openActivity2();
                } else {
                    Toast.makeText(FrontPage.this, "LOGIN FAIL!!!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void openActivity2() {

        Intent intent = new Intent(FrontPage.this, Activity2.class);
        startActivity(intent);
    }
}


