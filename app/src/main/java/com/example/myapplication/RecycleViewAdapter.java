package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<PaymentMemo> paymentList;
    Context context;

    public RecycleViewAdapter(List<PaymentMemo> paymentList, Context context) {
        this.paymentList = paymentList;
        this.context = context;
    }

    public RecycleViewAdapter(ArrayList<ArrayList<String>> paymentList) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_payment, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.purpose.setText(paymentList.get(position).getPurpose());
        holder.receiver.setText(paymentList.get(position).getReceiverName());
        holder.cost.setText(String.valueOf(paymentList.get(position).getCost()));
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView purpose, receiver, cost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            purpose = itemView.findViewById(R.id.payment_purpose);
            receiver = itemView.findViewById(R.id.payment_receiver);
            cost = itemView.findViewById(R.id.payment_cost);
        }
    }
}
