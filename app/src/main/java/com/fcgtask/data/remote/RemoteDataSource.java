package com.fcgtask.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.fcgtask.api.Api;
import com.fcgtask.data.DataSource;
import com.fcgtask.data.model.Favourites;
import com.fcgtask.data.model.Profile;
import com.fcgtask.data.model.ProfileDetails;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource implements DataSource {
    private static final String BASE_URL = "https://fierce-harbor-90458.herokuapp.com/";
    private Api mApi;

    public RemoteDataSource() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public LiveData<List<Profile>> getProfiles() {
        final MediatorLiveData<List<Profile>> data = new MediatorLiveData<>();
        mApi.getProfiles().enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(@NonNull Call<List<Profile>> call, @NonNull Response<List<Profile>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Profile>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    @Override
    public LiveData<ProfileDetails> getProfileDetails(int id) {
        final MediatorLiveData<ProfileDetails> data = new MediatorLiveData<>();
        mApi.getProfileDetails(id).enqueue(new Callback<ProfileDetails>() {
            @Override
            public void onResponse(@NonNull Call<ProfileDetails> call, @NonNull Response<ProfileDetails> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProfileDetails> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    @Override
    public void favouriteProfile(boolean isFav, int id) {

    }

    @Override
    public LiveData<List<Favourites>> getFavouriteProfiles() {
        return null;
    }
}
