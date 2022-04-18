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
    private ItemClickListener itemClickListener;

    public BasketAdapter(ArrayList<ArrayList<String>> baskets, ItemClickListener itemClickListener){
        this.baskets = baskets;
        this.itemClickListener = itemClickListener;
    }

    public BasketAdapter(ArrayList<ArrayList<String>> baskets){}

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, itemClickListener);
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

    public interface ItemClickListener{
        void onItemClicked(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView basketTitle, basketCreator;
        ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView, ItemClickListener itemClickListener){
            super(itemView);
            basketTitle = itemView.findViewById(R.id.basketTitle);
            basketCreator = itemView.findViewById(R.id.basketCreator);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
