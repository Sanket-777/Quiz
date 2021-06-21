package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class checkanswers extends AppCompatActivity {
    TextView ques, tim, high;
    RadioGroup op;
    RadioButton opt1, opt2, opt3, opt4;
    Button nxtquestion,gohome;
    int useranwers[];
    final String TAG = "Get User Answers";
    FirebaseFirestore firestore;
    int ianswer,catid,qs;
    String answer;
    Bundle extrass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkanswers);
        ques = findViewById(R.id.Questionn);
        op = findViewById(R.id.optionss);
        opt1 = findViewById(R.id.opt11);
        opt2 = findViewById(R.id.opt22);
        opt3 = findViewById(R.id.opt33);
        opt4 = findViewById(R.id.opt44);



        nxtquestion = findViewById(R.id.nxtquess);
        gohome = findViewById(R.id.gohome);
        extrass = getIntent().getExtras();
        catid = extrass.getInt("catid");
        useranwers = new int[12];
        Intent i = getIntent();
        useranwers = i.getIntArrayExtra("uanswers");
        Log.d(TAG, "onCreate: " + useranwers[1] + useranwers[2] + useranwers[3] + useranwers[4] + useranwers[5] + useranwers[6] + useranwers[7] + useranwers[8] + useranwers[9] + useranwers[10]);
        qs =1;
        getQuestionanswers();

        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

        nxtquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qs++;
                getQuestionanswers();
                if(qs==11)
                {
                    startActivity(new Intent(checkanswers.this,MainActivity.class));
                    finish();
                }
            }
        });

        gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(checkanswers.this,MainActivity.class));

            }
        });


    }

    private void getQuestionanswers() {
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("CAT" + catid).document("Q"+qs)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    opt1.setBackgroundColor(Color.rgb(124,125,127));
                    opt2.setBackgroundColor(Color.rgb(124,125,127));
                    opt3.setBackgroundColor(Color.rgb(124,125,127));
                    opt4.setBackgroundColor(Color.rgb(124,125,127));

                    opt1.setTextColor(Color.BLACK);
                    opt2.setTextColor(Color.BLACK);
                    opt3.setTextColor(Color.BLACK);
                    opt4.setTextColor(Color.BLACK);

                    ques.setText(qs + "." + documentSnapshot.getString("Q"));
                    opt1.setText(documentSnapshot.getString("A"));
                    opt2.setText(documentSnapshot.getString("B"));
                    opt3.setText(documentSnapshot.getString("C"));
                    opt4.setText(documentSnapshot.getString("D"));
                    answer = documentSnapshot.getString("Ans");
                    ianswer = Integer.parseInt(answer);
                    if(useranwers[qs]==ianswer) {
                        if (useranwers[qs] == 1)
                        {
                            opt1.setBackgroundColor(Color.GREEN);
                            opt1.setTextColor(Color.WHITE);
                        }
                        else if (useranwers[qs] == 2)
                        {
                            opt2.setBackgroundColor(Color.GREEN);
                            opt2.setTextColor(Color.WHITE);
                        }
                        else if (useranwers[qs] == 3)
                        {
                            opt3.setBackgroundColor(Color.GREEN);
                            opt3.setTextColor(Color.WHITE);
                        }
                        else if (useranwers[qs] == 4)
                        {
                            opt4.setBackgroundColor(Color.GREEN);
                            opt4.setTextColor(Color.WHITE);
                        }

                    }
                    else
                    {
                        if (useranwers[qs] == 1)
                        {
                            opt1.setBackgroundColor(Color.RED);
                            opt1.setTextColor(Color.WHITE);
                        }
                        else if (useranwers[qs] == 2)
                        {
                            opt2.setBackgroundColor(Color.RED);
                            opt2.setTextColor(Color.WHITE);
                        }
                        else if (useranwers[qs] == 3)
                        {
                            opt3.setBackgroundColor(Color.RED);
                            opt3.setTextColor(Color.WHITE);
                        }
                        else if (useranwers[qs] == 4)
                        {
                            opt4.setBackgroundColor(Color.RED);
                            opt4.setTextColor(Color.WHITE);
                        }

                        //for correct answer
                        if (ianswer == 1)
                        {
                            opt1.setBackgroundColor(Color.GREEN);
                            opt1.setTextColor(Color.WHITE);
                        }
                        else if (ianswer == 2)
                        {
                            opt2.setBackgroundColor(Color.GREEN);
                            opt2.setTextColor(Color.WHITE);
                        }
                        else if (ianswer== 3)
                        {
                            opt3.setBackgroundColor(Color.GREEN);
                            opt3.setTextColor(Color.WHITE);
                        }
                        else if (ianswer == 4)
                        {
                            opt4.setBackgroundColor(Color.GREEN);
                            opt4.setTextColor(Color.WHITE);
                        }
                    }



                }
            }
        });

    }


}