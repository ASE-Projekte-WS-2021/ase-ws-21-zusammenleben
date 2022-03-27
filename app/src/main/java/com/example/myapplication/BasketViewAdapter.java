package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entities.Basket;

import java.util.ArrayList;
import java.util.List;

public class BasketViewAdapter extends RecyclerView.Adapter<BasketViewAdapter.ViewHolder> {
    List <Basket> basketList;
    Context context;

    public BasketViewAdapter( List <Basket> basketList, Context context){
        this.basketList = basketList;
        this.context = context;
    }

    public BasketViewAdapter(ArrayList<ArrayList<String>> basketList){}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.basketTitle.setText(basketList.get(position).getTitle());
        holder.basketSubtitleactivity.setText(basketList.get(position).getSubtitle());
        holder.basketTextactivity.setText(basketList.get(position).getNotice());
    }

    @Override
    public int getItemCount(){
        return basketList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView basketTitle, basketSubtitleactivity, basketTextactivity;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            basketTitle = itemView.findViewById(R.id.basketTitle);
            basketSubtitleactivity = itemView.findViewById(R.id.basketSubtitleactivity);
            basketTextactivity = itemView.findViewById(R.id.basketTextactivity);
        }
    }
}
