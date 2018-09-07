package com.fcgtask.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.fcgtask.data.model.Favourites;

import java.util.List;

@Dao
public interface FavouritesDao {
    @Query("SELECT * FROM favourites ORDER BY id ASC")
    LiveData<List<Favourites>> getFavourites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favourites favourites);

    @Query("DELETE FROM favourites WHERE fav_profile_id = :id")
    int delete(int id);
}
