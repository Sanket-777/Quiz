package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView marks;
    int score;
    Bundle extras;
    final String TAG="Data RETRIEVal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        marks = findViewById(R.id.marks);
        extras = getIntent().getExtras();
        score = extras.getInt("marks");
        marks.setText(String.valueOf(score));
        Log.d(TAG, "onCreate: "+score);

    }
}