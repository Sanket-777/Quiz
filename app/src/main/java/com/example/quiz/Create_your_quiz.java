package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Create_your_quiz extends AppCompatActivity {
    EditText cat,ques,op1,op2,op3,op4,ans;
    String s_cat,s_quesno,s_ques,s_op1,s_op2,s_op3,s_op4,s_ans;
    TextView quesno;
    int i,j=2;
    Button subm;
    FirebaseFirestore db;
    String uniquecode;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_your_quiz);
        cat = findViewById(R.id.category);
        quesno = findViewById(R.id.QuestionNo);
        ques = findViewById(R.id.question);
        op1 = findViewById(R.id.c_opt1);
        op2 = findViewById(R.id.c_opt2);
        op3 = findViewById(R.id.c_opt3);
        op4 = findViewById(R.id.c_opt4);
        ans = findViewById(R.id.c_ans);
        subm = findViewById(R.id.c_submit);
        showDialog();
        db= FirebaseFirestore.getInstance();
        i=1;
        pg = new ProgressDialog(Create_your_quiz.this);
        pg.setMessage("Feeding Your Question into Database..");
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setIndeterminate(true);
        pg.setCancelable(false);

        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==10)
                {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(Create_your_quiz.this);
                    builder2.setCancelable(false);
                    builder2.setMessage("You Have submitted all the questions.You can now share this code  "+uniquecode+" to your friends and access the quiz");
                    builder2.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();


                        }
                    });

                    builder2.show();
                    startActivity(new Intent(Create_your_quiz.this,MainActivity.class));
                    finish();
                }
                pg.show();
                s_cat = cat.getText().toString();
                s_ques = ques.getText().toString();
                s_op1= op1.getText().toString();
                s_op2= op2.getText().toString();
                s_op3= op3.getText().toString();
                s_op4= op4.getText().toString();
                s_ans = ans.getText().toString();
                if(s_cat==null)
                {
                    cat.setError("Please Enter Your Unique Code");
                }
                else
                {
                    uniquecode = "CAT"+s_cat;
                    cat.setVisibility(View.GONE);
                }

                adddatatofirestore(uniquecode,s_quesno,s_ques,s_op1,s_op2,s_op3,s_op4,s_ans);




            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Create_your_quiz.this,MainActivity.class));
    }

    private boolean validate(String s_ques, String s_op1, String s_op2, String s_op3, String s_op4, String s_ans) {
        boolean valid = true;
        if (s_cat.isEmpty()) {
            cat.setError("Field Empty");
            valid = false;
            pg.dismiss();
            return valid;
        } else {
            cat.setError(null);

        }
        if (s_ques.isEmpty()) {
            ques.setError("Field Empty");
            valid = false;
            pg.cancel();
            return valid;
        } else {
            ques.setError(null);

        }
        if (s_op1.isEmpty()) {
            op1.setError("Field Empty");
            valid = false;
            pg.cancel();
            return valid;
        } else
        {
            op1.setError(null);

        }
        if(s_op2.isEmpty())
        {
            op2.setError("Field Empty");
            valid=false;
            pg.cancel();
            return  valid;
        }else
        {
            op2.setError(null);

        }
        if(s_op3.isEmpty())
        {
            op3.setError("Field Empty");
            valid=false;
            pg.cancel();
            return  valid;
        }else
        {
            op3.setError(null);

        }
        if(s_op4.isEmpty())
        {
            op4.setError("Field Empty");
            valid=false;
            pg.cancel();
            return  valid;
        }
        else
        {
            op4.setError(null);

        }
        if(s_ans.isEmpty())
        {
            ans.setError("Field Empty");
            valid=false;
            pg.cancel();
            return  valid;
        }
        else
        {

            ans.setError(null);
        }

        return valid;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Create_your_quiz.this);
        builder.setCancelable(false);
        builder.setMessage("Note:While Giving a unique code in number don't add any alphabet otherwise it will not be accessible to other user");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();


            }
        });

        builder.show();
    }

    private void adddatatofirestore(String s_cat, String s_quesno, String s_ques, String s_op1, String s_op2, String s_op3, String s_op4, String s_ans) {
        if (validate(s_ques, s_op1, s_op2, s_op3, s_op4,s_ans) == true)
        {
            Map<String, String> questiondata = new HashMap<>();
            questiondata.put("Q", s_ques);
            questiondata.put("A", s_op1);
            questiondata.put("B", s_op2);
            questiondata.put("C", s_op3);
            questiondata.put("D", s_op4);
            questiondata.put("Ans", s_ans);

            db.collection(s_cat).document("Q" + i).set(questiondata).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Create_your_quiz.this, "Data Added Sucessfully", Toast.LENGTH_SHORT).show();
                    ques.setText("");
                    op1.setText("");
                    op2.setText("");
                    op3.setText("");
                    op4.setText("");
                    ans.setText("");

                    quesno.setText("Enter Question No:" + j);
                    ques.setHint("Enter Question No :" + j);
                    op1.setHint("Enter Option 1 ");
                    op2.setHint("Enter Option 2 ");
                    op3.setHint("Enter Option 3 ");
                    op4.setHint("Enter Option 4 ");
                    ans.setHint("Enter Answer like 1,2,3,4(Option Number) ");
                    j++;
                    i++;
                    pg.dismiss();
                }
            });


        }
        else {

            Toast.makeText(this, "Please fill the Fields Coreectly", Toast.LENGTH_SHORT).show();
        }
    }


}