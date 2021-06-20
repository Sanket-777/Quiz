package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_DIsplayHigscores extends AppCompatActivity {
    TextView c,cpp,java,php,js,html;
    FirebaseFirestore fstore;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userid;
        setContentView(R.layout.activity__display_higscores);
        c = findViewById(R.id.scorec);
        cpp = findViewById(R.id.scorecpp);
        java = findViewById(R.id.scorejava);
        js = findViewById(R.id.scorejs);
        php = findViewById(R.id.scorephp);
        html = findViewById(R.id.scorehtml);

        fstore = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        userid = mauth.getCurrentUser().getUid();
        fstore.collection("Users").document(userid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    c.setText("HighScore :"+(documentSnapshot.getString("score_CH")));
                    cpp.setText("HighScore :"+(documentSnapshot.getString("score_CppH")));
                    java.setText("HighScore :"+(documentSnapshot.getString("score_JavaH")));
                    js.setText("HighScore :"+(documentSnapshot.getString("score_JsH")));
                    php.setText("HighScore :"+(documentSnapshot.getString("score_PhoH")));
                    html.setText("HighScore :"+(documentSnapshot.getString("score_HtmlH")));
                }
                else
                {
                    Toast.makeText(activity_DIsplayHigscores.this, "Scores Not Generated Yet", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(activity_DIsplayHigscores.this,MainActivity.class));
        finish();
    }
}