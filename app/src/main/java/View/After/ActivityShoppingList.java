package View.After;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import Entities.Basket;
import Entities.ShoppingItem;
import Presenter.ShoppingList.ShoppingListContract;
import Presenter.ShoppingList.ShoppingListPresenter;

public class ActivityShoppingList extends AppCompatActivity implements ShoppingListContract.View, ShoppingListContract.onShoppingSuccessListener {

    private ShoppingListPresenter shoppingListPresenter;
    String basketID;

    ListView listView, listViewCost;
    TextView sumCosts, costCheckout, inputCheckoutName;
    EditText editTextCost, inputItem, costItem, numItem;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        shoppingListPresenter = new ShoppingListPresenter(this);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_shoppinglist);
        listView = findViewById(R.id.list_view);
        listViewCost = findViewById(R.id.list_view_cost);
        listViewCost.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewCost.setItemChecked(position, false);
        sumCosts = findViewById(R.id.sumcosts);
        editTextCost = findViewById(R.id.insert_costs);
    }

    @Override
    protected void onResume(){
        super.onResume();
        unpackIntentData();
    }

    private void unpackIntentData(){
        Bundle receivedData = getIntent().getExtras();
        basketID = receivedData.getString("BASKETID");
        shoppingListPresenter.retrieveBasketItem(basketID);
    }

    @Override
    public void onBasketItemRetrieved(Basket basket) {

    }

    @Override
    public void onShoppingItemAdded(ShoppingItem item) {

    }

    @Override
    public void onShoppingItemRetrieved(String basketID) {

    }

    @Override
    public void onShoppingItemAdded(String basketID) {

    }
}
