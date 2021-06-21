package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    CardView c,cpp,java,php,js,html;
    Button createypur,finduiz;
    int catid;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    String Name,email;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id=item.getItemId();

        if(item_id==R.id.Profile)
        {   setContentView(R.layout.activity_profile);
            TextView name = findViewById(R.id.p_namee);
            TextView email = findViewById(R.id.p_eemail);
            String userid;

            firebaseFirestore=FirebaseFirestore.getInstance();
            userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
            String mail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            firebaseFirestore.collection("Users").document(userid)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists())
                    {
                        name.setText(documentSnapshot.getString("Name"));
                        email.setText(mail);

                    }
                }
            });


        }
        else if(item_id==R.id.Logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }
        else if(item_id==R.id.Aboutus)
        {
            setContentView(R.layout.activity_aboutus);
        }
        else if(item_id==R.id.ContactUs)
        {
            String sub="Quizeasy FeedBack";
            String to="quizeasy.help@gmail.com";
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
            email.putExtra(Intent.EXTRA_SUBJECT,sub );
            email.putExtra(Intent.EXTRA_TEXT, "Enter your Message here");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
        else if(item_id==R.id.Highscores)
        {
            startActivity(new Intent(MainActivity.this,activity_DIsplayHigscores.class));
            finish();

        } if(item_id==R.id.Share)
        {

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("text/plain");
            email.putExtra(Intent.EXTRA_TEXT, "Enter your Message here");
            startActivity(Intent.createChooser(email, "Share App using :"));
        }
        return true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = findViewById(R.id.C);
        cpp = findViewById(R.id.Cpp);
        java = findViewById(R.id.Java);
        js= findViewById(R.id.Javascript);
        php = findViewById(R.id.Php);
        html = findViewById(R.id.HTML);
        createypur= findViewById(R.id.createquiz);
        finduiz = findViewById(R.id.findaquiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);



        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        email = auth.getCurrentUser().getEmail();


        finduiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter the Code provided for accessing the quiz");

                final EditText input = new EditText(MainActivity.this);
                input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                builder.setView(input);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing but close the dialog
                        catid= Integer.parseInt(input.getText().toString());
                        String emp=input.getText().toString();
                        if(emp == null || emp.isEmpty())
                        {
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                        i.putExtra("catid",catid);
                        startActivity(i);
                        finish();
                        dialog.dismiss();

                    }
                });


                builder.show();

            }
            });

        cpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=1;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=2;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });
        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=3;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });
        php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=4;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });
        js.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=5;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });
        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Quiz_Questions.class);
                catid=6;
                i.putExtra("catid",catid);
                startActivity(i);
                finish();
            }
        });



        createypur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Create_your_quiz.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}