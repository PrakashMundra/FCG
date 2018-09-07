package com.fcgtask.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favourites")
public class Favourites {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int fav_profile_id;

    public Favourites(int id) {
        this.fav_profile_id = id;
    }
}
