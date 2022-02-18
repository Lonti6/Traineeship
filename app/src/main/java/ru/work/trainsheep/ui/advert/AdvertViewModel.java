package ru.work.trainsheep.ui.advert;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdvertViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdvertViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("fd");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
