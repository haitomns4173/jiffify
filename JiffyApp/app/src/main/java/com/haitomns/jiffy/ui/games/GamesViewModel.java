package com.haitomns.jiffy.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GamesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GamesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Games fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}