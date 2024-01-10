package com.example.todolistapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class getStarted extends AppCompatActivity {

    // Variables here
    Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // find elements by ID
        getStartedButton = findViewById(R.id.getStarted);

        // methods here
        getStarted();
    }

    public void getStarted(){
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getStarted.this, cousesActivity.class);
                startActivity(intent);
            }
        });
    }
}