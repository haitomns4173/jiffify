package com.haitomns.jiffy;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recommender extends AppCompatActivity {

    String[] mood = {"Happy", "Sad", "Angry", "Calm", "Excited", "Scared", "Tired", "Bored", "Stressed", "Anxious"};
    String[] weather = {"Sunny", "Cloudy", "Rainy", "Snowy", "Windy", "Foggy", "Stormy"};
    String[] hungerLevel = {"Starving", "Very Hungry", "Hungry", "Slightly Hungry", "Not Hungry"};
    String[] foodType = {"Vegetarian", "Non-Vegetarian"};

    AutoCompleteTextView moodInput;
    AutoCompleteTextView weatherInput;
    AutoCompleteTextView hungerLevelInput;
    AutoCompleteTextView foodTypeInput;
    ArrayAdapter<String> moodAdapter;
    ArrayAdapter<String> weatherAdapter;
    ArrayAdapter<String> hungerLevelAdapter;
    ArrayAdapter<String> foodTypeAdapter;

    Button recommendButton;
    TextView recommendationText;
    Button cartButton;
    RecommenderService recommenderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recommender);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        moodInput = findViewById(R.id.moodInput);
        moodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mood);

        weatherInput = findViewById(R.id.weatherInput);
        weatherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weather);

        hungerLevelInput = findViewById(R.id.hungerLevelInput);
        hungerLevelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hungerLevel);

        foodTypeInput = findViewById(R.id.foodTypeInput);
        foodTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodType);

        moodInput.setAdapter(moodAdapter);
        weatherInput.setAdapter(weatherAdapter);
        hungerLevelInput.setAdapter(hungerLevelAdapter);
        foodTypeInput.setAdapter(foodTypeAdapter);

        moodInput.setOnClickListener(v -> moodInput.showDropDown());
        weatherInput.setOnClickListener(v -> weatherInput.showDropDown());
        hungerLevelInput.setOnClickListener(v -> hungerLevelInput.showDropDown());
        foodTypeInput.setOnClickListener(v -> foodTypeInput.showDropDown());

        recommendButton = findViewById(R.id.recommendButton);
        recommendationText = findViewById(R.id.recommendationText);
        cartButton = findViewById(R.id.cartButton);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.16.69:8000") // Replace with your FastAPI server URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recommenderService = retrofit.create(RecommenderService.class);

        recommendButton.setOnClickListener(v -> {
            String mood = moodInput.getText().toString();
            String weather = weatherInput.getText().toString();
            String hungerLevel = hungerLevelInput.getText().toString();
            String foodType = foodTypeInput.getText().toString();

            getRecommendation(mood, weather, hungerLevel, foodType);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getRecommendation(String mood, String weather, String hungerLevel, String foodType) {
        recommenderService.getRecommendation(mood, weather, hungerLevel, foodType).enqueue(new Callback<RecommendationResponse>() {
            @Override
            public void onResponse(Call<RecommendationResponse> call, Response<RecommendationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String recommendedFood = response.body().getResponseData();
                    recommendationText.setText(String.format("You should order, %s", recommendedFood));
                    cartButton.setVisibility(Button.VISIBLE);
                } else {
                    Toast.makeText(Recommender.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RecommendationResponse> call, Throwable t) {
                Toast.makeText(Recommender.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}