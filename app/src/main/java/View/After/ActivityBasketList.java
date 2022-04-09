package View.After;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Entities.Basket;
import Entities.Flat;
import Presenter.BasketList.BasketListContract;
import Presenter.BasketList.BasketListPresenter;

public class ActivityBasketList extends AppCompatActivity implements BasketListContract.View, BasketListContract.onBasketSuccessListener {

    FloatingActionButton basketButton;
    BasketListPresenter mBasketListPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUserEmail;
    Flat currentFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketlist);
        basketButton = findViewById(R.id.basketActionButton);
        mBasketListPresenter = new BasketListPresenter(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUserEmail();
        mBasketListPresenter.retrieveFlat(currentUserEmail);
    }

    private void getCurrentUserEmail(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onButtonClicked(){
        mBasketListPresenter.createBasket(currentUserEmail);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onFlatFound(Flat flat) {
        currentFlat = flat;
        onButtonClicked();
    }

    @Override
    public void onBasketItemFound(Basket basket) {

    }

    @Override
    public void startIntent() {

    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {

    }

    @Override
    public void onBasketSucces() {

    }
}
