package com.haitomns.jiffy;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class DailySpinActivity extends AppCompatActivity {

    private ImageView wheelImage;
    private Button spinButton;
    private TextView resultText;

    private Random random = new Random();
    private String[] rewards = {
            "Drink Upgrade", "20% Off", "Free Delivery", "Free Desert",
            "10% Off", "Free Meal", "Suprise Gift", "Free Side Dish"
    };
    private int[] angles = {0, 45, 90, 135, 180, 225, 270, 315}; // Angles for each segment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_spin);

        wheelImage = findViewById(R.id.wheelImage);
        spinButton = findViewById(R.id.spinButton);
        resultText = findViewById(R.id.resultText);

        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinWheel();
            }
        });
    }

    private void spinWheel() {
        // Randomly pick a segment
        int randomIndex = random.nextInt(rewards.length);

        // Calculate the final rotation angle (add extra spins for visual effect)
        int finalAngle = angles[randomIndex] + (360 * 5);

        // Animate the wheel spinning
        ObjectAnimator animator = ObjectAnimator.ofFloat(wheelImage, "rotation", 0, finalAngle);
        animator.setDuration(3000); // Duration in milliseconds
        animator.start();

        // Show the result after the spin
        wheelImage.postDelayed(() -> {
            resultText.setText("You won: " + rewards[randomIndex]);
        }, 3000); // Delay to match animation duration
    }
}