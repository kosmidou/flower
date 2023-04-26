package com.example.flowers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Flower> flowers;

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
            holder.flower_date.setText(currentFlower.getDate());
        }

        holder.flower_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flower_image;
        TextView flower_name;
        TextView flower_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flower_image = itemView.findViewById(R.id.rose_image);
            flower_name = itemView.findViewById(R.id.rose_name);
            flower_date = itemView.findViewById(R.id.rose_date);

        }
    }
}
