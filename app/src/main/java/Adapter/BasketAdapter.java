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

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ArrayList<String>> baskets;

    public BasketAdapter(Context context, ArrayList<ArrayList<String>> baskets){
        this.context = context;
        this.baskets = baskets;
    }

    public BasketAdapter(ArrayList<ArrayList<String>> baskets){}

    @Override
    public BasketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.MyViewHolder holder, int position) {
        holder.basketTitle.setText(baskets.get(position).get(0));
        holder.basketCreator.setText(baskets.get(position).get(1));
    }

    @Override
    public int getItemCount() {
        return baskets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView basketTitle, basketCreator;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            basketTitle = itemView.findViewById(R.id.basketTitle);
            basketCreator = itemView.findViewById(R.id.basketCreator);
        }
    }
}
