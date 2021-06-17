package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email;
    Button send_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);



        email =  findViewById(R.id.frg_email);
        send_pass = findViewById(R.id.frg_send_password);


        firebaseAuth = FirebaseAuth.getInstance();
        send_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Forgot_password.this,"Password reset Link is send to your email",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Forgot_password.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Forgot_password.this, Login.class);
        startActivity(i);

    }
}