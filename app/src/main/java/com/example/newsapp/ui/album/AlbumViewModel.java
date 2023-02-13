package com.example.newsapp.ui.album;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlbumViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AlbumViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Album fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}