package com.example.flowers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for the RecyclerView that displays a list of flowers
 */
public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private List<Flower> flowers = new ArrayList<>();
    private static Listener listener;

    FlowerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.flower_item_list, parent, false);
        ViewHolder flowerViewHolder = new ViewHolder(itemView);
        return flowerViewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull FlowerAdapter.ViewHolder holder, int position) {
        if (flowers != null) {
            Flower currentFlower = flowers.get(position);
            holder.flowerName.setText(currentFlower.getFlowerName());

            //if data field is completed we set the data.If it is not completed we set an empty content
            if (currentFlower.getFlowerDate() != 0) {
                holder.flowerDate.setText(String.valueOf(currentFlower.getDateFromLong(currentFlower.getFlowerDate())));
            } else {
                holder.flowerDate.setText("");
            }
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

    void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
        notifyDataSetChanged();
    }

    /**
     * Gets the flower at the given position
     * This method is useful for identifying which flower is clicked
     *
     * @param position The position of the flower in the RecyclerView
     * @return The flower at the given position
     */
    public Flower getFlowerAtPosition(int position) {

        return flowers.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flowerImage;
        TextView flowerName;
        TextView flowerDate;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            flowerImage = itemView.findViewById(R.id.rose_image);
            flowerName = itemView.findViewById(R.id.rose_name);
            flowerDate = itemView.findViewById(R.id.rose_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemListener(Listener listener) {
        FlowerAdapter.listener = listener;
    }

    public interface Listener {
        void itemClicked(View v, int position);
    }

    public int getListSize(){
        return flowers.size();
    }


}
