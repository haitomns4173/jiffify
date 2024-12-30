package com.haitomns.jiffy.ui.menuItems;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.haitomns.jiffy.AddCardActivity;
import com.haitomns.jiffy.ArActivity;
import com.haitomns.jiffy.R;
import com.haitomns.jiffy.databinding.FragmentMenuItemBinding;
import com.haitomns.jiffy.databinding.FragmentOrdersBinding;
import com.haitomns.jiffy.ui.orders.OrdersViewModel;

public class MenuItemFragment extends Fragment {

    ImageButton backButton;
    Button viewInAR;
    Button addToCart;

    private FragmentMenuItemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MenuItemViewModel menuItemViewModel = new ViewModelProvider(this).get(MenuItemViewModel.class);

        binding = FragmentMenuItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        viewInAR = root.findViewById(R.id.viewInAR);
        viewInAR.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ArActivity.class);
            startActivity(intent);
        });

        addToCart = root.findViewById(R.id.addToCart);
        addToCart.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddCardActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}