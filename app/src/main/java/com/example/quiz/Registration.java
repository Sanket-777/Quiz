package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {
    EditText  uname, pass, conf_pass,email;
    Button nxtbtn;
    String  Name, Password, Confirm_pass,s_email;
    private FirebaseAuth mAuth;
    Scores score;
    FirebaseFirestore fstore;
    DatabaseReference ref;
    String answer;
    private static final String TAG = "EmailPassword";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_registration);
        uname = findViewById(R.id.Username);
        pass = findViewById(R.id.password);
        conf_pass = findViewById(R.id.conf_password);
        nxtbtn = findViewById(R.id.nextbtn);
        email = findViewById(R.id.Email);



        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
                //calls the signup method for the form validation`
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Registration.this, Login.class);
        startActivity(i);
    }
    public void signup () {
        if (!validateform()) {
            return;
        }

        Name = uname.getText().toString();
        s_email = email.getText().toString();
        Password = pass.getText().toString();
        Confirm_pass = conf_pass.getText().toString();
        mAuth.createUserWithEmailAndPassword(s_email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                            Map<String,String> Userdata = new HashMap<>();
                            Userdata.put("Name",Name);
                            String userid= mAuth.getCurrentUser().getUid();
                            fstore = FirebaseFirestore.getInstance();
                            DocumentReference documentReference = fstore.collection("Users").document(userid);
                            documentReference.set(Userdata);

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Registration.this, "Check Your Email for Verfication", Toast.LENGTH_SHORT).show();

                                }
                            });
                            FirebaseUser fuser = mAuth.getCurrentUser();

                            if(fuser.isEmailVerified())
                            {
                                Intent i = new Intent(Registration.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Intent i = new Intent(Registration.this,Login.class);
                                startActivity(i);
                                finish();
                            }
                        }


                        }
                    });
                };





    private boolean validateform() {
        boolean valid = true;

        String us = uname.getText().toString();
        if (TextUtils.isEmpty(us)) {
            uname.setError("Field Empty");
            valid = false;
        } else {
            uname.setError(null);
        }

// onClick of button perform this simplest code.
        String pw = pass.getText().toString();
        if (TextUtils.isEmpty(pw)) {
            pass.setError("Field Empty");
            valid = false;
        } else {
            pass.setError(null);
        }
        String cpw = conf_pass.getText().toString();
        if (TextUtils.isEmpty(cpw)) {
            conf_pass.setError("Field Empty");
            valid = false;
        } else {
            conf_pass.setError(null);
        }
        if (!(cpw.equals(pw))) {
            conf_pass.setError("Password Not Match");
        } else {
            conf_pass.setError(null);
        }
        return valid;
    }
    }