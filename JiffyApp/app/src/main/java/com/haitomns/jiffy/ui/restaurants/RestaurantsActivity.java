package com.haitomns.jiffy.ui.restaurants;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.haitomns.jiffy.R;
import com.haitomns.jiffy.Recommender;
import com.haitomns.jiffy.databinding.FragmentHomeBinding;
import com.haitomns.jiffy.databinding.FragmentRestaurantsBinding;
import com.haitomns.jiffy.ui.home.HomeViewModel;

public class RestaurantsActivity extends Fragment {

    private FragmentRestaurantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RestaurantsActivityViewModel homeViewModel = new ViewModelProvider(this).get(RestaurantsActivityViewModel.class);

        binding = FragmentRestaurantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}