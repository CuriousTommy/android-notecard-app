package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.homework3.androidController.SQLManager;
import com.example.homework3.androidModel.DatabaseViewModel;
import com.example.homework3.androidView.fragment.CardSetListFragment;

public class MainActivity extends AppCompatActivity {
    DatabaseViewModel shared_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared_model = ViewModelProviders.of(this).get(DatabaseViewModel.class);
        shared_model.setManager(new SQLManager(this));


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.current_layout);

        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.current_layout, new CardSetListFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
