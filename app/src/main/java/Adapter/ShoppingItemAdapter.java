package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<ArrayList<String>> shoppingItems;

    public ShoppingItemAdapter(Context context, ArrayList<ArrayList<String>> shoppingItems){
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    public ShoppingItemAdapter(ArrayList<ArrayList<String>> shoppingItems){}

    @Override
    public ShoppingItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglist_layout, parent, false);
        ShoppingItemAdapter.MyViewHolder myViewHolder = new ShoppingItemAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingItemAdapter.MyViewHolder holder, int position) {
        holder.shoppingItemTitle.setText(shoppingItems.get(position).get(0));
        holder.shoppingItemAmount.setText(shoppingItems.get(position).get(1));
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView shoppingItemTitle, shoppingItemAmount;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            shoppingItemTitle = itemView.findViewById(R.id.shoppingItemTitle);
            shoppingItemAmount = itemView.findViewById(R.id.shoppingItemAmount);
        }
    }

}