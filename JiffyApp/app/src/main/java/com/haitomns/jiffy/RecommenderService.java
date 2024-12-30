package com.haitomns.jiffy;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Field;

public interface RecommenderService {
    @FormUrlEncoded
    @POST("/")
    Call<RecommendationResponse> getRecommendation(
            @Field("mood") String mood,
            @Field("weather") String weather,
            @Field("hunger_level") String hungerLevel,
            @Field("food_type") String foodType
    );
}