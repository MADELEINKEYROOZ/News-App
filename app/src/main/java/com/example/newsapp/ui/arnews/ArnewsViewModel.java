package com.example.newsapp.ui.arnews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArnewsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ArnewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Arabic News fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}