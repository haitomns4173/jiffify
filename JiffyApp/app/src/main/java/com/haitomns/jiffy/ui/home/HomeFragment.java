package com.haitomns.jiffy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.haitomns.jiffy.R;
import com.haitomns.jiffy.Recommender;
import com.haitomns.jiffy.RecommenderService;
import com.haitomns.jiffy.databinding.FragmentHomeBinding;
import com.haitomns.jiffy.ui.restaurants.RestaurantsActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ImageButton recommendButton;
    private ImageButton theGardens;
    private ImageView bigMenu;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recommendButton = root.findViewById(R.id.recommendButton);
        recommendButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Recommender.class);
            startActivity(intent);
        });

        theGardens = root.findViewById(R.id.theGardens);
        theGardens.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.navigation_restaurants);
        });

        bigMenu = root.findViewById(R.id.bigMenu);
        bigMenu.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.navigation_menu_item);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}