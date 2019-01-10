package com.zumper.zumper.screens;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zumper.zumper.R;
import com.zumper.zumper.model.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    @NonNull
    private List<Restaurant> restaurants;

    public RestaurantAdapter(@NonNull List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_layout, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        restaurantViewHolder.bindView(restaurants.get(position));
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_text_view)
        TextView nameTextView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(@NonNull Restaurant restaurant) {
            nameTextView.setText(restaurant.name);
        }
    }
}
