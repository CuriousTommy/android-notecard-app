package com.example.homework3.androidView.fragment;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.example.homework3.R;
import com.example.homework3.androidModel.EditOrViewDataViewModel;
import com.example.homework3.androidView.fragment.generic.GenericDataModelListFragment;
import com.example.homework3.javaModel.CardSetDataModel;

import java.util.ArrayList;

public class CardSetListFragment extends GenericDataModelListFragment<CardSetDataModel> {
    public CardSetListFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAddMenuShown = true;

        mListener = item -> {
            mSharedEditData.setCardset(item);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.current_layout, new NoteCardListFragment());
            ft.addToBackStack(null);
            ft.commit();
        };
    }

    @Override
    protected ArrayList<CardSetDataModel> getDatabaseList() {
        return mSharedDatabase.getManager().getListOfCardSets();
    }

    //
    // Menu Options
    //

    @Override
    protected boolean activateAddActivity() {
        mSharedEditData.setStatusCode(EditOrViewDataViewModel.CREATE_NEW_OBJECT);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.current_layout, new CardSetEditFragment());
        ft.addToBackStack(null);
        ft.commit();
        return true;
    }

    @Override
    protected boolean activateEditActivity() {
        return false;
    }

    @Override
    protected boolean activateDeleteActivity() {
        return false;
    }

    @Override
    protected boolean activateSaveActivity() {
        return false;
    }
}
