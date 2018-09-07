package com.fcgtask.api;

import com.fcgtask.data.model.Profile;
import com.fcgtask.data.model.ProfileDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("profiles")
    Call<List<Profile>> getProfiles();

    @GET("profiles/{id}")
    Call<ProfileDetails> getProfileDetails(@Path("id") int id);
}
