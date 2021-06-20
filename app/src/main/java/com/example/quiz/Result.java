package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Result extends AppCompatActivity {
    TextView marks;
    int score,rank,highscore;
    Button leaader;
    Bundle extras;
    Scores scores;
    final String TAG="Data RETRIEVal";
    DatabaseReference ref;

    ProgressDialog pg;
    String Userid,subject;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pg = new ProgressDialog(Result.this);
        setContentView(R.layout.activity_result);
        pg.setIndeterminate(true);
        pg.setMessage("Checking Your Answers");
        pg.setCancelable(false);
        pg.show();

        leaader = findViewById(R.id.lead);
        marks = findViewById(R.id.marks);
        extras = getIntent().getExtras();
        score = extras.getInt("marks");
        highscore = Integer.parseInt(extras.getString("Highscore"));

        rank = score;
        subject = extras.getString("Subject");
        Log.d(TAG, "onCreate: "+subject);
        marks.setText(String.valueOf(score+"/10"));
        pg.dismiss();
        Log.d(TAG, "onCreate: "+score);

        Map<String, String> Savescore = new HashMap<>();
        Savescore.put("score_"+subject,String.valueOf(score));
        mAuth = FirebaseAuth.getInstance();
        Userid = mAuth.getCurrentUser().getUid();
        checkscore();
        ref = FirebaseDatabase.getInstance().getReference().child("Scores");
        ref.child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                  snapshot.getRef().child("score").setValue(score);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fstore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstore.collection("Users").document(Userid);
          documentReference.set(Savescore, SetOptions.merge());


          leaader.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(),Leaderboard.class));
                  finish();

              }
          });




    }

    private void checkscore() {
        if(score>highscore)
        {
            Map<String, String> Savescore = new HashMap<>();
            Savescore.put("score_"+subject+"H",String.valueOf(score));
            fstore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = fstore.collection("Users").document(Userid);
            documentReference.set(Savescore, SetOptions.merge());

        }
    }
}