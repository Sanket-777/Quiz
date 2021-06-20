package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Scores> list;
    FirestoreRecyclerAdapter adpater;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewforleaderboard);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview1);
        databaseReference = FirebaseDatabase.getInstance().getReference("Scores");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Scores scores = dataSnapshot.getValue(Scores.class);
                    Toast.makeText(Leaderboard.this, "Data Fetched", Toast.LENGTH_SHORT).show();
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*
        String user;
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference collectionReference = db.collection("Users");

        FirestoreRecyclerOptions<Scoreleaderboard> options = new FirestoreRecyclerOptions.Builder<Scoreleaderboard>()
                .setQuery(collectionReference,Scoreleaderboard.class).build();

         adpater = new FirestoreRecyclerAdapter<Scoreleaderboard, ScoreViewHolder>(options) {
            @NonNull
            @Override
            public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard,parent,false );
                return new ScoreViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ScoreViewHolder holder, int position, @NonNull Scoreleaderboard model) {
                holder.r_name.setText(model.getName());
                holder.r_score.setText(model.getScore_C() + "");

            }
        };
        /*

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Leaderboard.this));
        db = FirebaseFirestore.getInstance();
        scoreleaderboardArrayList =  new ArrayList<Scoreleaderboard>();
        myadapter = new Myadapter(Leaderboard.this,scoreleaderboardArrayList);
        recyclerView.setAdapter(myadapter);




        EvenyChangeListner();

    }

    private void EvenyChangeListner() {
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {

                        pg.dismiss();
                    Log.d("DATABASE", "onEvent: "+error.getMessage());

                    return;
                }

                for (DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        scoreleaderboardArrayList.add(dc.getDocument().toObject(Scoreleaderboard.class));
                    }
                    myadapter.notifyDataSetChanged();

                        pg.dismiss();

                }
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adpater);

    }

    private class ScoreViewHolder extends  RecyclerView.ViewHolder {
        private TextView r_name,r_score,r_rank;
        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            r_name = itemView.findViewById(R.id.Name);
            r_rank = itemView.findViewById(R.id.rank);
            r_score = itemView.findViewById(R.id.score);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adpater.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adpater.stopListening();
    }
    */
    }

}