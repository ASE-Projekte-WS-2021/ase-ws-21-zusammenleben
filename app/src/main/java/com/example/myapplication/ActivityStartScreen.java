package com.example.myapplication;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

                ImageView optionbuttons=noteViewHolder.itemView.findViewById(R.id.optionbutton);

                int colorchange = getColorChange();
                noteViewHolder.note.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colorchange, null));

                noteViewHolder.title.setText(notes.getTitle());
                noteViewHolder.subtitle.setText(notes.getSubtitle());
                noteViewHolder.notice.setText(notes.getNotice());

                String noteid = noteAdapter.getSnapshots().getSnapshot(i).getId();

                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // TODO Note is shown by klicking on it
                        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                optionbuttons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                        popupMenu.setGravity(Gravity.END);
                        popupMenu.getMenu().add("Edit choosen Note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                // TODO Feature Edit Note
                                //Intent intent = new Intent(view.getContext(),ActivityUserProfile.class);
                                //view.getContext().startActivity(intent);
                                Toast.makeText(view.getContext(), "TODO", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });

                        popupMenu.getMenu().add("Delete Note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document(noteid);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(view.getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });

                        popupMenu.show();
                    }
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

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subtitle;
        TextView notice;
        LinearLayout note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notetitl);
            subtitle = itemView.findViewById(R.id.noteSubtitleactivity);
            notice = itemView.findViewById(R.id.notetextactivity);
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
            colorchange.add(R.color.purple_500);
            colorchange.add(R.color.purple_700);

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
            //checkUserStatus();
            super.onStop();
            if (noteAdapter != null) {
                noteAdapter.stopListening();
            }
        }

}