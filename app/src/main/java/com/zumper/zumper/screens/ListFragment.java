package com.zumper.zumper.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zumper.zumper.R;
import com.zumper.zumper.model.Restaurant;
import com.zumper.zumper.model.RestaurantResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements ListContract.View {
    public static final String RESTAURANTS_KEY = "restaurants_key";

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(@NonNull RestaurantResponse restaurantResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RESTAURANTS_KEY, restaurantResponse);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.restaurant_recycler_view)
    RecyclerView restaurantRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        RestaurantResponse response = (RestaurantResponse) getArguments().getSerializable(RESTAURANTS_KEY);
        initRestaurantRecyclerView(response.restaurants);

        return view;
    }

    private void initRestaurantRecyclerView(@NonNull List<Restaurant> restaurants) {
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurants);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),
                                                                        LinearLayoutManager.VERTICAL,
                                                                        false));
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }
}
