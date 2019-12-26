package com.example.homework3.androidModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.homework3.androidController.SQLManager;
import com.example.homework3.javaModel.CardSetDataModel;

import java.util.ArrayList;

public class DatabaseViewModel extends AndroidViewModel {
    private SQLManager manager;

    public DatabaseViewModel(@NonNull Application application) {
        super(application);
    }

    public SQLManager getManager() {
        return manager;
    }

    public void setManager(SQLManager manager) {
        this.manager = manager;
    }
}
