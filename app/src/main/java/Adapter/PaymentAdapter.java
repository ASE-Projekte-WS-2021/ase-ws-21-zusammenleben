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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ArrayList<String>> payments;

    public PaymentAdapter(Context context, ArrayList<ArrayList<String>> payments){
        this.context = context;
        this.payments = payments;
    }

    public PaymentAdapter(ArrayList<ArrayList<String>> payments){
    }

    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_payment, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.MyViewHolder holder, int position) {
        holder.purpose.setText(payments.get(position).get(0));
        holder.receiver.setText(payments.get(position).get(1));
        holder.cost.setText(payments.get(position).get(2));
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView purpose, receiver, cost;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            purpose = itemView.findViewById(R.id.payment_purpose);
            receiver = itemView.findViewById(R.id.payment_receiver);
            cost = itemView.findViewById(R.id.payment_cost);
        }

    }
}
