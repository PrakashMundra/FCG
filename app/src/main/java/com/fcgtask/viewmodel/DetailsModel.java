package com.fcgtask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.fcgtask.App;
import com.fcgtask.data.DataRepository;

public class DetailsModel extends AndroidViewModel {
    public ObservableBoolean isFav = new ObservableBoolean(false);
    private DataRepository mDataRepository;

    DetailsModel(@NonNull Application application, @NonNull DataRepository repository) {
        super(application);
        this.mDataRepository = repository;
    }

    public void favourite(boolean isFav, int id) {
        mDataRepository.favouriteProfile(isFav, id);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final DataRepository mRepository;

        public Factory(@NonNull Application application) {
            this.mApplication = application;
            this.mRepository = ((App) application).getRepository();
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DetailsModel(mApplication, mRepository);
        }
    }
}
