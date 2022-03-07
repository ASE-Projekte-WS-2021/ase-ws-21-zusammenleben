package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ActivityStartScreen extends AppCompatActivity {

    TextView useremail;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ImageView imageView;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirestoreRecyclerAdapter<notes,NoteViewHolder> noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_startscreen);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        imageView = findViewById(R.id.imageAddMain);

        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.home);

        imageView.setOnClickListener(view -> startActivity(new Intent(ActivityStartScreen.this, ActivityNoteSpace.class)));

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.payment:
                    startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    return true;
                /*case R.id.add_note:
                    startActivity(new Intent(getApplicationContext(),ActivityNoteSpace.class));
                    overridePendingTransition(0,0);
                    return true;*/
                case R.id.user:
                    startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                    overridePendingTransition(0,0);
                    return true;

            }
            return false;
        });


        firebaseAuth = FirebaseAuth.getInstance();
        useremail = findViewById(R.id.show_email);

        // TODO change Query here to current flat
        Query query=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<notes> allusernotes = new FirestoreRecyclerOptions.Builder<notes>().setQuery(query, notes.class).build();

        noteAdapter= new FirestoreRecyclerAdapter<notes, NoteViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull notes notes) {

                //noteViewHolder.note_title.setText(notes.getTitle());
                //noteViewHolder.note_subtitle.setText(notes.getSubtitle());
                //noteViewHolder.note_text.setText(notes.getText());

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };

        recyclerView =findViewById(R.id.notesRecyclerView);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager= new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(noteAdapter);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private  TextView note_title;
        private  TextView note_subtitle;
        private TextView note_text;
        LinearLayout mnote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title = findViewById(R.id.inputNoteTitle);
            note_subtitle = findViewById(R.id.noteSubtitleactivity);
            note_text = findViewById(R.id.notetextactivity);
            mnote = findViewById(R.id.note);
        }

        private void checkUserStatus() {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                useremail.setText(user.getEmail());
            } else {
                startActivity(new Intent(ActivityStartScreen.this, ActivityLogin.class));
                finish();
            }
        }

        @Override
        protected void onStart() {
            checkUserStatus();
            super.onStart();
            noteAdapter.startListening();
        }

        @Override
        protected void onStop() {
            //checkUserStatus();
            super.onStop();
            if (noteAdapter != null) {
                noteAdapter.startListening();
            }
        }

    }
}