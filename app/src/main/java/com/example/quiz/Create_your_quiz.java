package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Create_your_quiz extends AppCompatActivity {
    EditText cat,quesno,ques,op1,op2,op3,op4,ans;
    String s_cat,s_quesno,s_ques,s_op1,s_op2,s_op3,s_op4,s_ans;
    Button subm;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_your_quiz);
        cat = findViewById(R.id.category);
        quesno = findViewById(R.id.questionno);
        ques = findViewById(R.id.question);
        op1 = findViewById(R.id.c_opt1);
        op2 = findViewById(R.id.c_opt2);
        op3 = findViewById(R.id.c_opt3);
        op4 = findViewById(R.id.c_opt4);
        ans = findViewById(R.id.c_ans);
        subm = findViewById(R.id.c_submit);
        db= FirebaseFirestore.getInstance();


        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_cat = cat.getText().toString();
                s_quesno = quesno.getText().toString();
                s_ques = ques.getText().toString();
                s_op1= op1.getText().toString();
                s_op2= op2.getText().toString();
                s_op3= op3.getText().toString();
                s_op4= op4.getText().toString();
                s_ans = ans.getText().toString();
                adddatatofirestore(s_cat,s_quesno,s_ques,s_op1,s_op2,s_op3,s_op4,s_ans);




            }
        });


    }

    private void adddatatofirestore(String s_cat, String s_quesno, String s_ques, String s_op1, String s_op2, String s_op3, String s_op4, String s_ans) {
        Map<String,String> questiondata = new HashMap<>();
        questiondata.put("Q",s_ques);
        questiondata.put("A",s_op1);
        questiondata.put("B",s_op2);
        questiondata.put("C",s_op3);
        questiondata.put("D",s_op4);
        questiondata.put("Ans",s_ans);

        db.collection(s_cat).document(s_quesno).set(questiondata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Create_your_quiz.this, "Data Added Sucessfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}