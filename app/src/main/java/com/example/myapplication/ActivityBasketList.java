package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityBasketList extends AppCompatActivity implements BasketViewAdapter.ItemClickListener{

    //UI layout activity_basket
    TextView showEmail, textPageTitle;
    CircleImageView showPicture;
    RecyclerView basketRecyclerView;
    FloatingActionButton basketActionButton;

    //ArrayList where baskets are stored
    ArrayList<Basket> mBaskets = new ArrayList<>();

    // bottomNav
    BottomNavigationView bottomNavigationView;
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


    Intent i;

    ////////////////////////////////////


    //Firebase Stuff
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FloatingActionButton b;


    String currentUser;
    FirebaseDatabase database;
    final String FIREBASEPATH = "https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/";
    DatabaseReference databaseReferenceShopping;

    // recyclerview in dem textviews angeziegt werden, wenn auf den plus button geclicked wird. Und auf die

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initViews();
        initFirebase();
    }


    private void initFirebase() {
        database = FirebaseDatabase.getInstance(FIREBASEPATH);
        databaseReferenceShopping = database.getReference("ShoppingList");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getEmail();
    }

    private void initViews(){
        setContentView(R.layout.activity_basketlist);
        //setBottomNavigationView();
        recyclerView = findViewById(R.id.basketRecyclerView);
        b = findViewById(R.id.basketActionButton);
        //bottomNavigationView = findViewById(R.id.bottomnavview);
        //bottomNavigationView.setSelectedItemId(R.id.shopping);
    }

    @Override
    protected void onResume(){
        super.onResume();
        initRecyclerView();
        fillRecyclerView();
    }

    private String day() {
        String s = "";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.MONDAY:
                s += "Montagsliste";
                break;
            case Calendar.TUESDAY:
                s += "Dienstagsliste";
                break;
            case Calendar.WEDNESDAY:
                s += "Mittwochsliste";
                break;
            case Calendar.THURSDAY:
                s += "Donnerstagsliste";
                break;
            case Calendar.FRIDAY:
                s += "Freitagsliste";
                break;
            case Calendar.SATURDAY:
                s += "Samstagsliste";
                break;
            case Calendar.SUNDAY:
                s += "Sonntagsliste";
                break;
        }

        return s;
    }

    private String currentTime(){
        String currentDataTime = DateFormat.getDateTimeInstance().format(new Date());
        return currentDataTime;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("mBaskets", mBaskets.toString());
        mAdapter = new BasketViewAdapter(mBaskets, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void fillRecyclerView(){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "it was !");
                Basket basket = new Basket(day(), currentUser, "Angelegt:" + "" + currentTime());
                mBaskets.add(basket);
                mAdapter.notifyDataSetChanged();
                Log.d("mbaskets", mBaskets.toString());
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        mBaskets.get(position);
        Log.d("check", String.valueOf(position));
        Bundle send = new Bundle();
        send.putString("VALIDATE", day() + "---" + String.valueOf(position));
        Intent intent = new Intent(this, ActivityShoppingList.class);
        intent.putExtras(send);
        startActivity(intent);
    }

    /*private void setBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.payment:
                        ActivityBasketList.this.startActivity(new Intent(ActivityBasketList.this.getApplicationContext(), ActivityOverview.class));
                        ActivityBasketList.this.overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        ActivityBasketList.this.startActivity(new Intent(ActivityBasketList.this.getApplicationContext(), ActivityStartScreen.class));
                        ActivityBasketList.this.overridePendingTransition(0, 0);
                        return true;
                    case R.id.user:
                        ActivityBasketList.this.startActivity(new Intent(ActivityBasketList.this.getApplicationContext(), ActivityUserProfile.class));
                        ActivityBasketList.this.overridePendingTransition(0, 0);
                        return true;
                    case R.id.shopping:
                        return true;
                }
                return false;
            }
        });
    }*/
}
