package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.entities.Basket;
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

public class ActivityBasketList extends AppCompatActivity {

    TextView useremail;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    private CircleImageView circleImageView;
    EditText setTitle, setDescription, setNotice;

    ArrayList<String> basketList = new ArrayList<>();

    ListView listviewBasket;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<Basket, BasketViewHolder> basketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
        setUserPicture();
        openUserprofile();
        setBottomNavigationView();
        displayBasket();
        createBasket();
        onRecyclerClicked();
    }

    public class BasketViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView subtitle;
        TextView notice;
        LinearLayout basketLayout;

        public BasketViewHolder(@NonNull View basketView) {
            super(basketView);
            title = basketView.findViewById(R.id.basketTitle);
            subtitle = basketView.findViewById(R.id.basketSubtitleactivity);
            notice = basketView.findViewById(R.id.basketTextactivity);
            basketLayout = basketView.findViewById(R.id.basket);
        }
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            useremail.setText(user.getEmail());
        } else {
            startActivity(new Intent(ActivityBasketList.this, ActivityLogin.class));
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
        basketAdapter.startListening();
    }

    @Override
    protected void onStop() {
        checkUserStatus();
        super.onStop();
        if (basketAdapter != null) {
            basketAdapter.stopListening();
        }
    }

    private void displayBasket(){
        Query query=firebaseFirestore.collection("basket").document(firebaseUser.getUid()).collection("mynotes").orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Basket> alluserbasket = new FirestoreRecyclerOptions.Builder<Basket>().setQuery(query, Basket.class).build();

        basketAdapter= new FirestoreRecyclerAdapter<Basket, BasketViewHolder>(alluserbasket) {
            @Override
            protected void onBindViewHolder(@NonNull BasketViewHolder basketViewHolder, int i, @NonNull Basket basket) {

                ImageView optionbuttons=basketViewHolder.itemView.findViewById(R.id.optionbutton);

                int colorchange = getColorChange();
                basketViewHolder.basketLayout.setBackgroundColor(basketViewHolder.itemView.getResources().getColor(colorchange, null));

                basketViewHolder.title.setText(basket.getTitle());
                basketViewHolder.subtitle.setText(basket.getSubtitle());
                basketViewHolder.notice.setText(basket.getNotice());

                String basketid = basketAdapter.getSnapshots().getSnapshot(i).getId();

                basketViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(view.getContext(),ActivityShoppingList.class);
                        intent.putExtra("title", basket.getTitle());
                        intent.putExtra("subtitle", basket.getSubtitle());
                        intent.putExtra("notice", basket.getNotice());
                        intent.putExtra("basketID", basketid);
                        view.getContext().startActivity(intent);
                    }
                });

                optionbuttons.setOnClickListener(view -> {
                    PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                    popupMenu.setGravity(Gravity.END);
                    popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(menuItem -> {

                        Intent intent = new Intent(view.getContext(),ActivityShoppingList.class);
                        intent.putExtra("title", basket.getTitle());
                        intent.putExtra("subtitle", basket.getSubtitle());
                        intent.putExtra("notice", basket.getNotice());
                        intent.putExtra("basketID", basketid);
                        view.getContext().startActivity(intent);
                        return false;
                    });

                    popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            DocumentReference documentReference = firebaseFirestore.collection("basket").document(firebaseUser.getUid()).collection("mynotes").document(basketid);
                            documentReference.delete().addOnSuccessListener(unused -> Toast.makeText(view.getContext(), "Basket succesfully deleted!", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
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
            public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout,parent,false);
                return new BasketViewHolder(view);
            }
        };

        recyclerView =findViewById(R.id.basketRecyclerView);
        recyclerView.setHasFixedSize(true);
        //staggeredGridLayoutManager= new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(basketAdapter);
        //recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //recyclerView.setAdapter(basketAdapter);

    }

    private void createBasket(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*
        //versuch 1
        listviewBasket.removeAllViews();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            TextView txtBasket = new TextView(ActivityBasketList.this);
            int id = 3;

            //aus basketAdapter muss die ID ausgelesen werden und hier eingetragen werden damit man die Baskets unterscheiden kann
            txtBasket.setId(id);
            txtBasket.setText("datum");
            listviewBasket.addView(txtBasket, 50, 50);


                //versuch 2
                String title = setTitle.getText().toString().trim();
                String subtitle = setDescription.getText().toString().trim();
                String notice= setNotice.getText().toString().trim();

                if (title.isEmpty()){
                    Toast.makeText(getApplicationContext(), "A title and a subtitle are required!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (subtitle.isEmpty()){
                    Toast.makeText(getApplicationContext(), "A title and a subtitle are required!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document();
                    Map<String, Object> basket= new HashMap<>();
                    basket.put("title", title);
                    basket.put("subtitle", subtitle);
                    basket.put("notice", notice);

                    documentReference.set(basket).addOnSuccessListener(unused -> {
                        Toast.makeText(getApplicationContext(), "Note successfully created!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());
                }
            }
        });


        // Versuch 3
        //imageView.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBasketList.this);
                //builder.setTitle("Add New Basket");

                View dialogLayout = LayoutInflater.from(ActivityBasketList.this).inflate(R.layout.layout_add_basket, null, false);

                builder.setView(dialogLayout);
                final EditText inputNameBasket = dialogLayout.findViewById(R.id.inputNameList);
                final EditText inputDescriptionBasket = dialogLayout.findViewById(R.id.inputDescriptionList);


                builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
                    if (!inputNameBasket.getText().toString().isEmpty() && !inputDescriptionBasket.getText().toString().isEmpty()) {
                        list.add(inputNameBasket.getText().toString().trim());
                        basketAdapter.notifyDataSetChanged();

                    } else {
                        inputNameBasket.setError("add item here !");
                        inputDescriptionBasket.setError("add costs here !");
                    }
                });

                builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
                builder.show();
            }
        });*/
        //pop-up screen wie bei shopping list
        //imageView.setOnClickListener(view -> startActivity(new Intent(ActivityBasketList.this, ActivityShoppingList.class)));
    }


    private void setBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.payment:
                    startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),ActivityStartScreen.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.user:
                    startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.shopping:
                    return true;
            }
            return false;
        });
    }
    private void setupUIComponents(){
        setContentView(R.layout.activity_basketlist);
        imageView = findViewById(R.id.basketActionButton);
        circleImageView =findViewById(R.id.show_picture);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        useremail = findViewById(R.id.show_email);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initFirebase(){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setUserPicture(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.getPhotoUrl() != null ){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(circleImageView);
        }
    }

    private void openUserprofile(){
        circleImageView.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityUserProfile.class)));
    }

    private void onRecyclerClicked(){
        recyclerView.setOnClickListener(view -> {
            Intent intent= new Intent(getApplicationContext(), ActivityShoppingList.class);
            startActivity(intent);
        });
    }

}
