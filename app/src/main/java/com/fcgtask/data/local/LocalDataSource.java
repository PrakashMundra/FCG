package com.fcgtask.data.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.fcgtask.AppExecutors;
import com.fcgtask.data.DataSource;
import com.fcgtask.data.model.Favourites;
import com.fcgtask.data.model.Profile;
import com.fcgtask.data.model.ProfileDetails;

import java.util.List;

public class LocalDataSource implements DataSource {
    private static volatile LocalDataSource INSTANCE;
    private AppExecutors mAppExecutors;
    private FavouritesDao mFavouritesDao;

    private LocalDataSource(@NonNull AppExecutors executors, @NonNull FavouritesDao dao) {
        this.mAppExecutors = executors;
        this.mFavouritesDao = dao;
    }

    public static LocalDataSource getInstance(@NonNull AppExecutors executors, @NonNull FavouritesDao dao) {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null)
                    INSTANCE = new LocalDataSource(executors, dao);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Profile>> getProfiles() {
        return null;
    }

    @Override
    public LiveData<ProfileDetails> getProfileDetails(int id) {
        return null;
    }

    @Override
    public void favouriteProfile(boolean isFav, int id) {
        if (isFav) {
            mAppExecutors.diskIO().execute(() ->
                    mFavouritesDao.insert(new Favourites(id))
            );
        } else {
            mAppExecutors.diskIO().execute(() -> {
                        int i = mFavouritesDao.delete(id);
                        System.out.print(":::" + i);
                    }
            );
        }
    }

    @Override
    public LiveData<List<Favourites>> getFavouriteProfiles() {
        return mFavouritesDao.getFavourites();
    }
}
