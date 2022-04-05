/*package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.entities.Notes;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityStartScreen extends AppCompatActivity {

    TextView useremail;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    private CircleImageView circleImageView;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirestoreRecyclerAdapter<Notes,NoteViewHolder> noteAdapter;

    //EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
        //setUserPicture();
        //openUserprofile();
        setBottomNavigationView();
        createNote();
        displayNote();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subtitle;
        TextView notice;
        TextView url;
        LinearLayout note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notetitle);
            subtitle = itemView.findViewById(R.id.noteSubtitleactivity);
            notice = itemView.findViewById(R.id.notetextactivity);
            url = itemView.findViewById(R.id.noteURL);
            note = itemView.findViewById(R.id.note);
        }

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

        private int getColorChange(){

            List<Integer> colorchange = new ArrayList<>();
            colorchange.add(R.color.orange);
            colorchange.add(R.color.colorNote1);
            colorchange.add(R.color.colorNote3);
            colorchange.add(R.color.teal_200);
            colorchange.add(R.color.teal_700);
            colorchange.add(R.color.purple_200);

            Random random = new Random();
            int number = random.nextInt(colorchange.size());
            return colorchange.get(number);

        }

        @Override
        protected void onStart() {
            checkUserStatus();
            super.onStart();
            noteAdapter.startListening();
        }

        @Override
        protected void onStop() {
            checkUserStatus();
            super.onStop();
            if (noteAdapter != null) {
                noteAdapter.stopListening();
            }
        }

    private void displayNote(){
        Query query=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Notes> allusernotes = new FirestoreRecyclerOptions.Builder<Notes>().setQuery(query, Notes.class).build();

        noteAdapter= new FirestoreRecyclerAdapter<Notes, NoteViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull Notes notes) {

                ImageView optionbuttons=noteViewHolder.itemView.findViewById(R.id.optionbutton);

                int colorchange = getColorChange();
                noteViewHolder.note.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colorchange, null));

                noteViewHolder.title.setText(notes.getTitle());
                noteViewHolder.subtitle.setText(notes.getSubtitle());
                noteViewHolder.notice.setText(notes.getNotice());
                noteViewHolder.url.setText(notes.getUrl());

                String noteid = noteAdapter.getSnapshots().getSnapshot(i).getId();

                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(view.getContext(),ActivityNoteInformations.class);
                        intent.putExtra("title", notes.getTitle());
                        intent.putExtra("subtitle", notes.getSubtitle());
                        intent.putExtra("notice", notes.getNotice());
                        intent.putExtra("url", notes.getUrl());
                        intent.putExtra("noteID", noteid);
                        view.getContext().startActivity(intent);
                    }
                });

                optionbuttons.setOnClickListener(view -> {
                    PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                    popupMenu.setGravity(Gravity.END);
                    popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(menuItem -> {

                        Intent intent = new Intent(view.getContext(),ActivityEditNote.class);
                        intent.putExtra("title", notes.getTitle());
                        intent.putExtra("subtitle", notes.getSubtitle());
                        intent.putExtra("notice", notes.getNotice());
                        intent.putExtra("url", notes.getUrl());
                        intent.putExtra("noteID", noteid);
                        view.getContext().startActivity(intent);
                        return false;
                    });

                    popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document(noteid);
                            documentReference.delete().addOnSuccessListener(unused -> Toast.makeText(view.getContext(), "Note succesfully deleted!", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(view.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return false;
                        }
                    });

                    popupMenu.show();
                });

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

    private void createNote(){
        imageView.setOnClickListener(view -> startActivity(new Intent(ActivityStartScreen.this, ActivityNoteSpace.class)));
    }

    /*private void openUserprofile(){
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivityUserProfile.class));
            }
        });
    }*/

/*

    private void setBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.payment:
                    startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    return true;
                case R.id.user:
                    startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.shopping:
                    startActivity(new Intent(getApplicationContext(),ActivityBasketList.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    /*private void setUserPicture(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.getPhotoUrl() != null ){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(circleImageView);
        }
    }*/

/*
    private void setupUIComponents(){
        setContentView(R.layout.activity_startscreen);
        imageView = findViewById(R.id.imageAddMain);
        //circleImageView =findViewById(R.id.show_picture);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.home);
        useremail = findViewById(R.id.show_email);
        //inputSearch = findViewById(R.id.inputSearch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initFirebase(){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

}

*/