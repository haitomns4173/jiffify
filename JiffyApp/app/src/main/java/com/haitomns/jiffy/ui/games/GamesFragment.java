package com.haitomns.jiffy.ui.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.haitomns.jiffy.DailySpinActivity;
import com.haitomns.jiffy.GunActivity;
import com.haitomns.jiffy.QuestActivity;
import com.haitomns.jiffy.R;
import com.haitomns.jiffy.SnakeGame;
import com.haitomns.jiffy.databinding.FragmentGamesBinding;

public class GamesFragment extends Fragment {

    private FragmentGamesBinding binding;
    private ImageButton gunButton;
    private ImageButton questButton;
    private ImageButton snakeButton;
    private ImageButton spinButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GamesViewModel gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);

        binding = FragmentGamesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gunButton = root.findViewById(R.id.russian_roulette);
        gunButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GunActivity.class);
            startActivity(intent);
        });

        questButton = root.findViewById(R.id.quest);
        questButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuestActivity.class);
            startActivity(intent);
        });

        snakeButton = root.findViewById(R.id.snake);
        snakeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SnakeGame.class);
            startActivity(intent);
        });

        spinButton = root.findViewById(R.id.daily_spin);
        spinButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DailySpinActivity.class);
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