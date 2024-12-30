package com.haitomns.jiffy.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.haitomns.jiffy.AddCardActivity;
import com.haitomns.jiffy.R;
import com.haitomns.jiffy.Reel;
import com.haitomns.jiffy.ReelAdapter;
import com.haitomns.jiffy.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private ViewPager2 reelsViewPager; // Use ViewPager2
    private ReelAdapter reelAdapter;
    private List<Reel> reelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Initialize ViewPager2
        reelsViewPager = view.findViewById(R.id.reelsViewPager);

        // Initialize the list and adapter
        initializeReelList();
        reelAdapter = new ReelAdapter(getContext(), reelList);

        // Set the adapter to the ViewPager2
        reelsViewPager.setAdapter(reelAdapter);

        return view; // Return the inflated view
    }

    private void initializeReelList() {
        reelList = new ArrayList<>();
        reelList.add(new Reel("android.resource://" + getContext().getPackageName() + "/" + R.raw.reel_1, "Pizza"));
        reelList.add(new Reel("android.resource://" + getContext().getPackageName() + "/" + R.raw.reel_2, "Tacos"));
        reelList.add(new Reel("android.resource://" + getContext().getPackageName() + "/" + R.raw.reel_3, "Burger"));
    }
}