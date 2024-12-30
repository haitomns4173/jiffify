package com.haitomns.jiffy;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class

GunActivity extends AppCompatActivity {

    private int bulletPosition;
    private int currentChamber;
    private int lives;
    private int score;
    private boolean isGameOver;

    // UI components
    private TextView gameStatus;
    private Button spinButton;
    private Button pullTriggerButton;
    private Button resetButton;

    // Sound effects
    private SoundPool soundPool;
    private int bangSoundId;
    private int reloadSoundId;
    private int emptySoundId; // New sound effect for empty gunshot
    private int currentlyPlayingStreamId; // To track currently playing sound

    ImageView gunImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        gunImage = findViewById(R.id.gunImage);

        // Initialize UI components
        initializeUI();

        // Initialize SoundPool and load sound effects
        initializeSoundPool();

        // Start a new game
        resetGame();
    }

    private void initializeUI() {
        gameStatus = findViewById(R.id.game_status);
        spinButton = findViewById(R.id.spin_button);
        pullTriggerButton = findViewById(R.id.pull_trigger_button);
        resetButton = findViewById(R.id.reset_button);

        spinButton.setOnClickListener(v -> spinCylinder());
        pullTriggerButton.setOnClickListener(v -> pullTrigger());
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void initializeSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        bangSoundId = soundPool.load(this, R.raw.bang, 1);
        reloadSoundId = soundPool.load(this, R.raw.reload, 1);
        emptySoundId = soundPool.load(this, R.raw.empty, 1);
    }

    private void resetGame() {
        // Reset game state
        bulletPosition = new Random().nextInt(6) + 1;
        currentChamber = 0;
        lives = 3;
        score = 0;
        isGameOver = false;

        // Update UI
        gameStatus.setText("Spin the cylinder and pull the trigger!");

        gunImage.setImageResource(R.drawable.gun);
        spinButton.setEnabled(true);
        pullTriggerButton.setEnabled(false);
    }

    private void spinCylinder() {
        if (isGameOver) return;

        // Randomize the chamber position
        currentChamber = new Random().nextInt(6) + 1;

        // Play reload sound and update UI
        resetAudio(); // Stop any currently playing audio
        currentlyPlayingStreamId = soundPool.play(reloadSoundId, 1, 1, 0, 0, 1);
        gameStatus.setText("Cylinder spun. The one who gets shot will pay for all!");
        spinButton.setEnabled(false);
        pullTriggerButton.setEnabled(true);
    }

    private void pullTrigger() {
        if (isGameOver) return;

        // Check if the current chamber matches the bullet position
        if (currentChamber == bulletPosition) {
            resetAudio(); // Stop any currently playing audio
            currentlyPlayingStreamId = soundPool.play(bangSoundId, 1, 1, 0, 0, 1); // Play bang sound
            lives--; // Lose a life

            if (lives <= 0) {
                // Game over
                gameOver();
            } else {
                // Still alive, but lost a life
                gameStatus.setText("Bang! You will pay!");

                // set gunImage to show the gunshot

                gunImage.setImageResource(R.drawable.gun_shot);

                spinButton.setEnabled(true);
                pullTriggerButton.setEnabled(false);
            }
        } else {
            // Safe shot (click)
            resetAudio(); // Stop any currently playing audio
            currentlyPlayingStreamId = soundPool.play(emptySoundId, 1, 1, 0, 0, 1); // Play empty gunshot sound
            score++;
            gameStatus.setText("Click! You're safe.");
            currentChamber = (currentChamber % 6) + 1; // Advance to the next chamber
        }
    }

    private void resetAudio() {
        if (currentlyPlayingStreamId != 0) {
            soundPool.stop(currentlyPlayingStreamId);
        }
    }

    private void gameOver() {
        isGameOver = true;
        gameStatus.setText("Game Over! You lost all lives.");
        spinButton.setEnabled(false);
        pullTriggerButton.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release SoundPool resources
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}