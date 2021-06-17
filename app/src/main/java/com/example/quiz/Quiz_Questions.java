package com.example.quiz;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Quiz_Questions extends AppCompatActivity {

    TextView ques,tim;
    RadioGroup op;
    int uanswer,i=0,j,catid,useranswer[],databsanswer[],questionno=1,Result;
    Button nxtquestion;
    String answer;
    RadioButton opt1,opt2,opt3,opt4;
    final String TAG="Data RETRIEVal";
    DatabaseReference ref;
    int count;
    int score;
    Bundle  extras;
    boolean optionselected;
    private  FirebaseFirestore firestore;
    ProgressDialog progress;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__questions);

        progress = new ProgressDialog(Quiz_Questions.this);
        progress.setMessage("Loading Question...) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();
        ques = findViewById(R.id.Question);
        op = findViewById(R.id.options);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        nxtquestion = findViewById(R.id.nxtques);
        extras = getIntent().getExtras();
        tim = findViewById(R.id.timer);
        useranswer = new int[12];
        databsanswer =  new int[12];
        questionno=1;
        count=0;
        catid = extras.getInt("catid");
        firestore = FirebaseFirestore.getInstance();
        loaddata();
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tim.setBackgroundColor(Color.rgb(182,212,236));
                long time =millisUntilFinished / 1000;
                if(time<=0) {
                    tim.setText("Next Question in: " + millisUntilFinished / 1000);
                    questionno=questionno+1;
                    op.clearCheck();
                    progress.show();
                    loaddata();
                    optionselected=true;
                }
                else if(time<5)
                {
                    tim.setBackgroundColor(Color.RED);
                    tim.setText("Next Question in: " + millisUntilFinished / 1000);
                }

                else if(time>0)
                {
                    tim.setText("Next Question in: " + millisUntilFinished / 1000);
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(Quiz_Questions.this, "Time Up", Toast.LENGTH_SHORT).show();
                questionno=questionno+1;
                op.clearCheck();
                progress.show();
                optionselected=true;
                loaddata();

            }
        };

        nxtquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkanswer();
                if(optionselected==false)
                {
                    Toast.makeText(Quiz_Questions.this, "Please Select one Option", Toast.LENGTH_SHORT).show();

                }
                else if(optionselected==true)
                {
                    countDownTimer.cancel();
                    progress.show();
                    questionno=questionno+1;
                    op.clearCheck();
                    loaddata();
                    progress.dismiss();
                }
            }
        });
    }
    private void checkanswer() {
        optionselected = true;
        if(opt1.isChecked())
        {
           uanswer=1;
           useranswer[questionno]=uanswer;

        }
        else if(opt2.isChecked())
        {
            uanswer=2;
            useranswer[questionno]=uanswer;


        }
        else if(opt3.isChecked())
        {
            uanswer=3;
            useranswer[questionno]=uanswer;

        }
        else if(opt4.isChecked())
        {
            uanswer=4;
            useranswer[questionno]=uanswer;
        }
        else {
            optionselected = false;
        }
        int dbanswer = Integer.parseInt(answer);
        databsanswer[questionno]=dbanswer;
        if(dbanswer==uanswer)
        {
            count=count+1;
            Log.d(TAG, "Score: "+count);
        }
        else 
        {
            Log.d(TAG, "User anser: "+uanswer);
            Log.d(TAG, "System Anser: "+answer);
        }
    }

    private void loaddata() {
            i++;
            String qs="Q"+i;
            Log.d(TAG, "loaddata: "+qs);
            firestore.collection("CAT1").document(qs)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        ques.setText(qs+"."+documentSnapshot.getString("Q"));
                        opt1.setText(documentSnapshot.getString("A"));
                        opt2.setText(documentSnapshot.getString("B"));
                        opt3.setText(documentSnapshot.getString("C"));
                        opt4.setText(documentSnapshot.getString("D"));
                        answer =documentSnapshot.getString("Ans");
                        progress.dismiss();
                        countDownTimer.start();


                    }
                    else if(i==11)
                    {
                        tim.setVisibility(View.GONE);
                        countDownTimer.cancel();
                        progress.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz_Questions.this);
                        builder.setMessage("Congratulations ! You have Completed the Quiz");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                score=count;
                                Intent i = new Intent(Quiz_Questions.this, com.example.quiz.Result.class);
                                i.putExtra("marks",score);
                                startActivity(i);
                                dialog.dismiss();
                            }

                        });

                        builder.show();

                    }
                    else {
                        Log.d(TAG, "Retrieval Failed:"+i);
                    }
                }
            });





    }


}