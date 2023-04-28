package com.example.flowers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Flower> flowers;
    private static Listener listener;

    FlowerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.flower_item_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerAdapter.ViewHolder holder, int position) {
        if (flowers != null) {
            Flower currentFlower = flowers.get(position);
            holder.flower_name.setText(currentFlower.getFlowerName());
            holder.flower_date.setText(String.valueOf(currentFlower.getDate()));
        }


    }

    @Override
    public int getItemCount() {
        if (flowers != null) {
            return flowers.size();
        } else {
            return 0;
        }
    }

    void setFlowers(List<Flower> nflowers) {
        flowers = nflowers;
        notifyDataSetChanged();
    }

    public Flower getFlowerAtPosition(int position ){
        return flowers.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flower_image;
        TextView flower_name;
        TextView flower_date;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView);
            flower_image = itemView.findViewById(R.id.rose_image);
            flower_name = itemView.findViewById(R.id.rose_name);
            flower_date = itemView.findViewById(R.id.rose_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(view,getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemListener(Listener listener){

        FlowerAdapter.listener=listener;
    }
    public interface Listener{
        void itemClicked(View v , int position );
    }
}
