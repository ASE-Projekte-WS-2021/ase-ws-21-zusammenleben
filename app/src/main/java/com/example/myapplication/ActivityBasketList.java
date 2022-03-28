package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entities.Basket;
import com.example.myapplication.entities.Flats;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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


    long count;
    String mTitle;
    String mSubTitle;
    String mDesc;
    Basket mBasket;

    String identifier;
    String basketID;

    long basketCounter;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView title, basketSubtitleactivity, basketTextactivity;
    ImageView editButton;


    Intent i;

    ////////////////////////////////////
    ArrayList<ArrayList<String>> allFlats = new ArrayList<>();



    //Firebase Stuff
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FloatingActionButton b;



    String currentUserFlatID;
    String currentUser;
    FirebaseDatabase database;
    final String FIREBASEPATH = "https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/";
    DatabaseReference databaseReferenceShopping, databaseReferenceFlats, databaseReferenceBaskets;

    // recyclerview in dem textviews angeziegt werden, wenn auf den plus button geclicked wird. Und auf die

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initViews();
        setBottomNavigationView();
    }

    private void retrieveFlatDataFromFirebase(){
        readFlatData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ArrayList<String>> list) {
                retrieveFlatIDfromFirebase();
            }
        });
    }

    private void retrieveFlatIDfromFirebase(){
        Log.d("anzahl aller Flats", String.valueOf(allFlats.size()));
        for (int i = 0 ; i < allFlats.size() ; i++) {
            ArrayList<String> currentFlat = allFlats.get(i);
            if(currentFlat.contains(currentUser)){
                currentUserFlatID = "";
                currentUserFlatID += currentFlat.get(0);
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        mBaskets.clear();
    }

    private void readFlatData(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    int flatSize = snap.getValue(Flats.class).getFlatSize();
                    ArrayList<String> flatContent = snap.getValue(Flats.class).getData(flatSize);
                    allFlats.add(flatContent);
                }
                // wait
                firebaseCallback.onCallback(allFlats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferenceFlats.addListenerForSingleValueEvent(valueEventListener);
    }

    interface FirebaseCallback{
        void onCallback(ArrayList<ArrayList<String>> list);
    }


    private void initFirebase() {
        database = FirebaseDatabase.getInstance(FIREBASEPATH);
        databaseReferenceShopping = database.getReference("ShoppingList");
        databaseReferenceFlats = database.getReference("Flats");
        databaseReferenceBaskets = database.getReference("Baskets");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getEmail();
        Log.d("currentUser", currentUser);
    }

    private void initViews(){
        setContentView(R.layout.activity_basketlist);
        //setBottomNavigationView();
        recyclerView = findViewById(R.id.basketRecyclerView);
        b = findViewById(R.id.basketActionButton);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);
        //editButton = findViewById(R.id.editButton_basket);
        //bottomNavigationView = findViewById(R.id.bottomnavview);
        //bottomNavigationView.setSelectedItemId(R.id.shopping);

    }

    //TODO : Firebase counter nachbessern

    @Override
    protected void onResume(){
        super.onResume();
        initFirebase();
        retrieveFlatDataFromFirebase();
        Log.d("before callback", mBaskets.toString());
        readBasketData(new FirebaseBasketCallback() {
            @Override
            public void onBasketCallback(ArrayList<Basket> mBaskets) {
                fillRecyclerView();
            }
        });

        Log.d("after callback", mBaskets.toString());
    }

    private void readBasketData(FirebaseBasketCallback firebaseBasketCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    basketCounter = snapshot.getChildrenCount();
                    Log.d("count", String.valueOf(basketCounter));
                    String subtitle = ds.getValue(Basket.class).getSubtitle();
                    if(subtitle.equals(currentUser)) {
                        Log.d("test123", "made it inside callback func");
                        basketID = ds.getValue(Basket.class).getFlatID();
                        String title = ds.getValue(Basket.class).getTitle();
                        String notice = ds.getValue(Basket.class).getNotice();
                        Basket basket = new Basket(basketID, title, subtitle, notice);
                        //datenübertragen


                        mBaskets.add(basket);
                        Log.d("test123", basket.toString());
                    }
                }
                firebaseBasketCallback.onBasketCallback(mBaskets);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceBaskets.addListenerForSingleValueEvent(valueEventListener);
    }

    interface FirebaseBasketCallback{
        void onBasketCallback(ArrayList<Basket> myBaskets);
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



    private void fillRecyclerView(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        Log.d("mBaskets", mBaskets.toString());
        mAdapter = new BasketViewAdapter(mBaskets, this);
        recyclerView.setAdapter(mAdapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("before increasing", String.valueOf(basketCounter));
                basketCounter += 1;
                Log.d("after 1 increasing", String.valueOf(basketCounter));
                identifier = currentUserFlatID + String.valueOf(basketCounter);
                Basket basket = new Basket(day(), currentUser, "Angelegt:" + "" + currentTime(), identifier);
                mBaskets.add(basket);
                Log.d("check", identifier);
                databaseReferenceBaskets.child(identifier).setValue(basket);
                mAdapter.notifyDataSetChanged();
            }
        });
        basketCounter+= 1;
        Log.d("after 2 increasing", String.valueOf(basketCounter));
    }

    @Override
    public void onItemClicked(int position) {
        mBaskets.get(position);
        Log.d("check", String.valueOf(position));
        Bundle send = new Bundle();
        send.putString("VALIDATE", day() + "/" + String.valueOf(position) + "/" + mBaskets.get(position).getTitle());
        Intent intent = new Intent(this, ActivityShoppingList.class);
        intent.putExtra("getTitle", mBaskets.get(position).getTitle());
        intent.putExtras(send);
        startActivity(intent);
    }


    private void setBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.payment:
                        startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ActivityStartScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.shopping:
                        return true;
                }
                return false;
            }
        });
    }
}
