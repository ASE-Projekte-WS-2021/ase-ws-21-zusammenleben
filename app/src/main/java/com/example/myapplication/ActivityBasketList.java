package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entities.Basket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityBasketList extends AppCompatActivity {

    //UI layout activity_basket
    TextView showEmail, textPageTitle;
    CircleImageView showPicture;
    RecyclerView basketRecyclerView;
    FloatingActionButton basketActionButton;

    //ArrayList where baskets are stored
    ArrayList<Basket> mBaskets = new ArrayList<>();

    // bottomNav
    BottomNavigationView bottomNavigationView;

    //Firebase Stuff
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    //adapter
    //FirestoreRecyclerAdapter<Basket, BasketViewHolder> basketAdapter;
    LinearLayout basketLinearLayout;
    LinearLayoutManager basketLayoutManager;


    String mTitle;
    String mSubTitle;
    String mDesc;
    Basket mBasket;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView title, basketSubtitleactivity, basketTextactivity;
    ImageView editButton;

    // recyclerview in dem textviews angeziegt werden, wenn auf den plus button geclicked wird. Und auf die

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketlist);
        initViews();
        //setDateandtime();
        setBottomNavigationView();
        displayBasket();
    }

    private void initViews(){
        showPicture = findViewById(R.id.show_picture);
        showEmail = findViewById(R.id.show_email);
        textPageTitle = findViewById(R.id.textPageTitle);
        basketRecyclerView = findViewById(R.id.basketRecyclerView);
        basketActionButton = findViewById(R.id.basketActionButton);
        basketLinearLayout = findViewById(R.id.basket);

        title = findViewById(R.id.basketTitle);
        basketSubtitleactivity = findViewById(R.id.basketSubtitleactivity);
        basketTextactivity = findViewById(R.id.basketTextactivity);
        editButton = findViewById(R.id.editButton_basket);
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        basketAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        basketAdapter.stopListening();
    }
    */
    @Override
    protected void onResume(){
        super.onResume();
        createBasket();
    }
/*
    public void setDateandtime(){
        basketSubtitleactivity.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
    }*/

    private void displayBasket(){

        /*
        Calendar calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.MONDAY:
                mTitle = "Montagsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
            case Calendar.TUESDAY:
                mTitle = "Dienstagsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
            case Calendar.WEDNESDAY:
                mTitle = "Mittwochsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
            case Calendar.THURSDAY:
                mTitle = "Donnerstagsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
            case Calendar.FRIDAY:
                mTitle = "Freitagsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
            case Calendar.SATURDAY:
                mTitle = "Samstagsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
            case Calendar.SUNDAY:
                mTitle = "Sonntagsliste";
                mSubTitle = basketSubtitleactivity.getText().toString().trim();
                mDesc = "Diese Liste wurde von dir erstellt. Editiere sie oder füge Items zu deinem Basket hinzu";
                break;
        }*/


        Basket mBasket = new Basket("a", "b", "c");

        basketActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("basket was logged");
                mBaskets.add(mBasket);
                System.out.println(mBasket);
            }
        });
    }

    private void createBasket(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //mAdapter = new BasketViewAdapter();
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


    private void initFirebase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

}
