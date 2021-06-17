package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CardView c,cpp,java,php,js,html;
    Button createqu;
    int catid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = findViewById(R.id.C);
        cpp = findViewById(R.id.Cpp);
        java = findViewById(R.id.Java);
        js= findViewById(R.id.Javascript);
        php = findViewById(R.id.Php);
        html = findViewById(R.id.HTML);
        createqu = findViewById(R.id.createquiz);


        cpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=1;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });

        createqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Create_your_quiz.class));
                finish();
            }
        });
    }
}