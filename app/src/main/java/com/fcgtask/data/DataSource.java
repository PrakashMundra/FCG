package com.fcgtask.data;

import android.arch.lifecycle.LiveData;

import com.fcgtask.data.model.Favourites;
import com.fcgtask.data.model.Profile;
import com.fcgtask.data.model.ProfileDetails;

import java.util.List;

public interface DataSource {
    LiveData<List<Profile>> getProfiles();

    LiveData<ProfileDetails> getProfileDetails(int id);

    void favouriteProfile(boolean isFav, int id);

    LiveData<List<Favourites>> getFavouriteProfiles();
}
