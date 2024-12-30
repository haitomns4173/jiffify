package com.haitomns.jiffy;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SnakeGame extends AppCompatActivity {

    private SnakeGameView snakeGameView;
    private View startScreen;
    private Button retryButton;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        // Get references to views
        startScreen = findViewById(R.id.startScreen);
        snakeGameView = findViewById(R.id.snakeGameView);
        retryButton = findViewById(R.id.retryButton);
        Button startButton = findViewById(R.id.startButton);

        // Initialize the GestureDetector
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Start the game
        startButton.setOnClickListener(v -> {
            startScreen.setVisibility(View.GONE);
            snakeGameView.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.GONE);
            snakeGameView.startGame();
        });

        // Retry the game
        retryButton.setOnClickListener(v -> {
            retryButton.setVisibility(View.GONE);
            snakeGameView.startGame();
        });

        // Show retry button when the game is over
        snakeGameView.setGameOverListener(() -> runOnUiThread(() -> retryButton.setVisibility(View.VISIBLE)));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    // Custom GestureListener for detecting swipes
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            try {
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    // Horizontal Swipe
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            snakeGameView.setDirection("RIGHT");
                        } else {
                            snakeGameView.setDirection("LEFT");
                        }
                        return true;
                    }
                } else {
                    // Vertical Swipe
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            snakeGameView.setDirection("DOWN");
                        } else {
                            snakeGameView.setDirection("UP");
                        }
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}