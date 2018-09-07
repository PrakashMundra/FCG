package com.fcgtask;

import android.app.Application;

import com.fcgtask.data.DataRepository;
import com.fcgtask.data.local.AppDatabase;

public class App extends Application {
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(new AppExecutors(), getDatabase().profileDao());
    }
}
