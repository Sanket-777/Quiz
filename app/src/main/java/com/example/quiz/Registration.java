package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


public class Registration extends AppCompatActivity {
    EditText  uname, pass, conf_pass,email;
    Button nxtbtn;
    String  Name, Password, Confirm_pass,s_email;
    DatabaseReference myref,myref2;
    private FirebaseAuth mAuth;
    String Question="0";
    String o1,o2,o3,o4;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref = database.getReference("User Details");
        myref2 = database.getReference("English");



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
                            Toast.makeText(Registration.this, "User Sucessfully Created", Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }
                    }
                });



    }

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