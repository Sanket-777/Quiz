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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Quiz_Questions extends AppCompatActivity {

    TextView ques,tim,high;
    int q1,q2,q3,q4,q5,q6,q7,q8,q9,q10;
    long Highscore_C=0;
    RadioGroup op;
    int uanswer,i=0,j,catid,questionno=1,useranswer[],wrngquestionno[];
    Button nxtquestion;
    String answer,subject,userid,hightxt;
    RadioButton opt1,opt2,opt3,opt4;
    Bundle extras;
    final String TAG="Data RETRIEVal";
    DatabaseReference ref;
    int count;
    int score;
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
        high = findViewById(R.id.highscore);
        useranswer = new int[12];
        wrngquestionno = new int[12];
        

        questionno=1;
        count=0;
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        catid = extras.getInt("catid");
        firestore = FirebaseFirestore.getInstance();
        checkhighscore();
        showdialog();

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
                    if(questionno==10)
                    {
                        tim.setText("Quiz Ends in: " + millisUntilFinished / 1000);

                    }

                }

                else if(time>0)
                {
                    tim.setText("Next Question in: " + millisUntilFinished / 1000);
                    if(questionno==10)
                    {
                        tim.setText("Quiz Ends in: " + millisUntilFinished / 1000);

                    }
                }

            }

            @Override
            public void onFinish() {
                if(questionno==10)
                {
                    setMessage();
                }
                else 
                {
                    Toast.makeText(Quiz_Questions.this, "Time Up", Toast.LENGTH_SHORT).show();
                    questionno=questionno+1;
                    op.clearCheck();
                    progress.show();
                    optionselected=true;
                    loaddata();
                }
               

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
                    if(questionno==11)
                    {
                        setMessage();
                    }
                    else {
                        countDownTimer.cancel();
                        progress.show();
                        questionno = questionno + 1;
                        op.clearCheck();
                        loaddata();
                        progress.dismiss();
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz_Questions.this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit the Quiz");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                startActivity(new Intent(Quiz_Questions.this ,MainActivity.class));
                finish();
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz_Questions.this);
        builder.setCancelable(false);
        builder.setMessage("The following Quiz contains 10 question each for 1 mark and there is also a timer for each question of 20 seconds you can't skip a question.All THE BEST FOR YOUR QUIZ ");
        builder.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
                loaddata();

            }
        });

        builder.show();

    }


    void checkhighscore() {
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Users").document(userid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if(catid==1)
                    {
                        high.setText("HighScore :"+(documentSnapshot.getString("score_CppH")));
                        hightxt = documentSnapshot.getString("score_CppH");


                    }
                    else if(catid==2)
                    {
                        high.setText("HighScore :"+(documentSnapshot.getString("score_CH")));
                        hightxt = documentSnapshot.getString("score_CH");

                    }
                    else if(catid==3)
                    {
                        high.setText("HighScore :"+(documentSnapshot.getString("score_JavaH")));
                        hightxt = documentSnapshot.getString("score_JavaH");
                    }
                    else if(catid==4)
                    {
                        high.setText("HighScore :"+(documentSnapshot.getString("score_PhpH")));
                        hightxt = documentSnapshot.getString("score_PhpH");
                    }
                    else if(catid==5)
                    {
                        high.setText("HighScore :"+(documentSnapshot.getString("score_JsH")));
                        hightxt = documentSnapshot.getString("score_JsH");
                    }
                    else if(catid==6)
                    {
                        high.setText("HighScore :"+(documentSnapshot.getString("score_HtmlH")));
                        hightxt = documentSnapshot.getString("score_HtmlH");
                    }

                    if(hightxt==null)
                    {
                        high.setText("Highscore :00");
                        hightxt ="0";

                    }

                }
                else {
                    high.setText("00");
                    Log.d(TAG, "onSuccess: "+"No documents Found");
                    Log.d(TAG, "onSuccess: "+catid);
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
        int dbanswer=0;
        dbanswer= Integer.valueOf(answer);
        Log.d(TAG, "checkanswer : "+useranswer[questionno]);
        if(dbanswer==uanswer)
        {
            count=count+1;
            Log.d(TAG, "Score: "+count);
        }
        else
        {
            useranswer[questionno]=uanswer;
            wrngquestionno[questionno]=questionno;

        }
    }

    private void loaddata() {
            i++;
            String qs="Q"+i;
            Log.d(TAG, "loaddata: "+qs);
            firestore.collection("CAT"+catid).document(qs)
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
                    else if(i>10)
                    {
                        setMessage();
                       
                    }
                    else {
                        Log.d(TAG, "Retrieval Failed:"+i);
                        Toast.makeText(Quiz_Questions.this, "No such Quiz", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void setMessage() {
        tim.setVisibility(View.GONE);
        high.setVisibility(View.GONE);
        countDownTimer.cancel();

        Log.d(TAG, "onSuccess: "+subject);
        progress.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz_Questions.this);
        builder.setMessage("Congratulations ! You have Completed the Quiz");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                score=count;
                Intent i = new Intent(Quiz_Questions.this, com.example.quiz.Result.class);
                int highs = Integer.parseInt(hightxt);

                if (catid == 1) {
                    subject = "Cpp";
                    i.putExtra("Subject",subject);
                } else if (catid == 2) {
                    subject = "C";
                    i.putExtra("Subject",subject);
                } else if (catid == 3) {
                    subject = "Java";
                    i.putExtra("Subject",subject);
                } else if (catid == 4) {
                    subject = "Php";
                    i.putExtra("Subject",subject);
                } else if (catid == 5) {
                    subject = "Js";
                    i.putExtra("Subject",subject);
                } else if (catid == 6) {
                    subject = "Html";
                    i.putExtra("Subject",subject);
                }
                Log.d(TAG, "Subject"+subject);
                i.putExtra("Highscore",Integer.valueOf(highs));
                i.putExtra("marks",score);
                i.putExtra("answers",useranswer);
                i.putExtra("catid",catid);
                startActivity(i);
                dialog.dismiss();
            }
        });
        builder.show();


    }


}