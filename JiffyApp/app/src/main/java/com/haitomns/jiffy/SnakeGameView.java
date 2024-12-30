package com.haitomns.jiffy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SnakeGameView extends View {

    private int TILE_SIZE;
    private int NUM_TILES_X;
    private int NUM_TILES_Y;
    private ArrayList<Point> snake;
    private Point food;
    private String direction = "RIGHT";
    private boolean gameOver = false;
    private int score = 0;
    private Paint paint;
    private Handler handler;
    private Runnable gameLoop;
    private int gameSpeed = 300; // Snake movement interval in milliseconds
    private GameOverListener gameOverListener;

    public SnakeGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnakeGameView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        snake = new ArrayList<>();
        handler = new Handler();

        // Initialize the game loop
        gameLoop = new Runnable() {
            @Override
            public void run() {
                if (!gameOver) {
                    moveSnake();
                    invalidate(); // Redraw the screen
                    handler.postDelayed(this, gameSpeed); // Repeat after a delay
                }
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Dynamically calculate the tile size and grid dimensions
        TILE_SIZE = Math.min(w, h) / 20; // Make tiles fit on the screen
        NUM_TILES_X = w / TILE_SIZE;
        NUM_TILES_Y = h / TILE_SIZE;

        // Start game
        startGame();
    }

    private void spawnFood() {
        if (NUM_TILES_X <= 0 || NUM_TILES_Y <= 0) return; // Prevent invalid food spawning

        Random random = new Random();
        boolean validPosition = false;

        while (!validPosition) {
            int x = random.nextInt(NUM_TILES_X); // Generate random x-coordinate
            int y = random.nextInt(NUM_TILES_Y); // Generate random y-coordinate

            Point newFood = new Point(x, y);

            // Ensure the food does not overlap with the snake
            if (!snake.contains(newFood)) {
                food = newFood;
                validPosition = true;
            }
        }
    }


    public void startGame() {
        if (NUM_TILES_X <= 0 || NUM_TILES_Y <= 0) {
            // Prevent starting the game until the grid size is initialized
            return;
        }

        // Reset game state
        score = 0;
        gameOver = false;
        direction = "RIGHT";
        snake.clear();
        snake.add(new Point(NUM_TILES_X / 2, NUM_TILES_Y / 2)); // Center the snake
        spawnFood(); // Spawn the first food
        handler.removeCallbacks(gameLoop); // Stop any previous loops
        handler.post(gameLoop); // Start the game loop
    }

    private void moveSnake() {
        if (snake.isEmpty()) return;

        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case "UP":
                newHead.y -= 1;
                break;
            case "DOWN":
                newHead.y += 1;
                break;
            case "LEFT":
                newHead.x -= 1;
                break;
            case "RIGHT":
                newHead.x += 1;
                break;
        }

        // Check for collisions with walls or self
        if (newHead.x < 0 || newHead.x >= NUM_TILES_X || newHead.y < 0 || newHead.y >= NUM_TILES_Y || snake.contains(newHead)) {
            gameOver = true;

            if (gameOverListener != null) {
                gameOverListener.onGameOver();
            }
            return;
        }

        snake.add(0, newHead);

        // Check if the snake eats the food
        if (newHead.equals(food)) {
            score += 10; // Increase score
            spawnFood(); // Spawn new food
        } else {
            snake.remove(snake.size() - 1); // Remove the tail
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background
        canvas.drawColor(Color.BLACK);

        // Draw snake
        paint.setColor(Color.GREEN);
        for (Point point : snake) {
            canvas.drawRect(
                    point.x * TILE_SIZE,
                    point.y * TILE_SIZE,
                    (point.x + 1) * TILE_SIZE,
                    (point.y + 1) * TILE_SIZE,
                    paint
            );
        }

        // Draw food
        paint.setColor(Color.RED);
        canvas.drawRect(
                food.x * TILE_SIZE,
                food.y * TILE_SIZE,
                (food.x + 1) * TILE_SIZE,
                (food.y + 1) * TILE_SIZE,
                paint
        );

        // Draw game over text
        if (gameOver) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(100);
            canvas.drawText("Game Over", getWidth() / 4f, getHeight() / 3f, paint);
        }
    }

    public void setDirection(String newDirection) {
        if ((newDirection.equals("UP") && direction.equals("DOWN")) ||
                (newDirection.equals("DOWN") && direction.equals("UP")) ||
                (newDirection.equals("LEFT") && direction.equals("RIGHT")) ||
                (newDirection.equals("RIGHT") && direction.equals("LEFT"))) {
            return;
        }
        direction = newDirection;
    }


    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    public interface GameOverListener {
        void onGameOver();
    }
    public void pauseGame() {
        handler.removeCallbacks(gameLoop); // Stop the game loop
    }

    public void cleanup() {
        handler.removeCallbacksAndMessages(null); // Remove all callbacks
    }
}
