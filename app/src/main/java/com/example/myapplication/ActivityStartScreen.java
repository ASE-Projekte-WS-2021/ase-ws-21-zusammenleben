package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentKt;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.util.List;

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityStartScreen.this, ActivityNoteSpace.class));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
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
                //noteViewHolder.note_subttitle.setText(notes.getSubtitle());
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

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView note_title;
        private TextView note_subttitle;
        private TextView note_text;
        LinearLayout mnote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title = findViewById(R.id.inputNoteTitle);
            note_subttitle = findViewById(R.id.noteSubtitleactivity);
            note_text = findViewById(R.id.notetextactivity);
            mnote = findViewById(R.id.note);
        }
    }

    private void checkUserStatus (){
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
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
        if(noteAdapter!=null){
            noteAdapter.startListening();
        }
    }


}