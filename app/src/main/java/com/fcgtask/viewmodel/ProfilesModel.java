package com.fcgtask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.fcgtask.App;
import com.fcgtask.data.DataRepository;
import com.fcgtask.data.model.Favourites;
import com.fcgtask.data.model.Profile;
import com.fcgtask.data.model.ProfileDetails;

import java.util.List;

public class ProfilesModel extends AndroidViewModel {
    public ObservableBoolean loading = new ObservableBoolean(true);
    public ObservableBoolean isEmpty = new ObservableBoolean(false);
    private DataRepository mDataRepository;

    ProfilesModel(@NonNull Application application, @NonNull DataRepository repository) {
        super(application);
        this.mDataRepository = repository;
    }

    public LiveData<List<Profile>> getProfiles() {
        MediatorLiveData<List<Profile>> observable = new MediatorLiveData<>();
        LiveData<List<Profile>> profiles = mDataRepository.getProfiles();
        observable.addSource(profiles, observable::setValue);
        return observable;
    }

    public LiveData<List<Favourites>> getFavouriteProfiles() {
        MediatorLiveData<List<Favourites>> observable = new MediatorLiveData<>();
        LiveData<List<Favourites>> favourites = mDataRepository.getFavouriteProfiles();
        observable.addSource(favourites, observable::setValue);
        return observable;
    }

    public LiveData<ProfileDetails> getProfileDetails(int id) {
        MediatorLiveData<ProfileDetails> observable = new MediatorLiveData<>();
        LiveData<ProfileDetails> details = mDataRepository.getProfileDetails(id);
        observable.addSource(details, observable::setValue);
        return observable;
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
            return (T) new ProfilesModel(mApplication, mRepository);
        }
    }
}
