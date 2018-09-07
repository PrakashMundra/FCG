package com.fcgtask.data;

import android.arch.lifecycle.LiveData;

import com.fcgtask.AppExecutors;
import com.fcgtask.data.local.FavouritesDao;
import com.fcgtask.data.local.LocalDataSource;
import com.fcgtask.data.model.Favourites;
import com.fcgtask.data.model.Profile;
import com.fcgtask.data.model.ProfileDetails;
import com.fcgtask.data.remote.RemoteDataSource;

import java.util.List;

public class DataRepository implements DataSource {
    private static DataRepository INSTANCE;
    private RemoteDataSource mRemoteDataRepository;
    private LocalDataSource mLocalDataSource;

    private DataRepository(AppExecutors executors, FavouritesDao dao) {
        this.mRemoteDataRepository = new RemoteDataSource();
        this.mLocalDataSource = LocalDataSource.getInstance(executors, dao);
    }

    public static DataRepository getInstance(AppExecutors executors, FavouritesDao dao) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                INSTANCE = new DataRepository(executors, dao);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Profile>> getProfiles() {
        return mRemoteDataRepository.getProfiles();
    }

    @Override
    public LiveData<ProfileDetails> getProfileDetails(int id) {
        return mRemoteDataRepository.getProfileDetails(id);
    }

    @Override
    public void favouriteProfile(boolean isFav, int id) {
        mLocalDataSource.favouriteProfile(isFav, id);
    }

    @Override
    public LiveData<List<Favourites>> getFavouriteProfiles() {
        return mLocalDataSource.getFavouriteProfiles();
    }
}
