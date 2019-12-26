package com.example.homework3.androidView.fragment;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.example.homework3.R;
import com.example.homework3.androidModel.EditOrViewDataViewModel;
import com.example.homework3.androidView.fragment.generic.GenericDataModelListFragment;
import com.example.homework3.androidView.fragment.generic.ViewDataFragment;
import com.example.homework3.javaModel.NoteCardDataModel;

import java.util.ArrayList;

public class NoteCardListFragment extends GenericDataModelListFragment<NoteCardDataModel> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isAddMenuShown = true;
        isEditMenuShown = true;
        isDeleteMenuShown = true;
        mListener = item ->  {
            mSharedEditData.setNotecard(item);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.current_layout, new ViewDataFragment());
            ft.addToBackStack(null);
            ft.commit();
        };
    }

    @Override
    protected ArrayList<NoteCardDataModel> getDatabaseList() {
        return mSharedDatabase.getManager().getListOfCardsFromGroupID(mSharedEditData.getCardset().getId());
    }

    @Override
    protected boolean activateAddActivity() {
        mSharedEditData.setStatusCode(EditOrViewDataViewModel.CREATE_NEW_OBJECT);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.current_layout, new NoteCardEditFragment());
        ft.addToBackStack(null);
        ft.commit();
        return true;
    }
/*
        mSharedEditData.setStatusCode(EditOrViewDataViewModel.CREATE_NEW_OBJECT);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.current_layout, new CardSetEditFragment());
        ft.addToBackStack(null);
        ft.commit();
 */
    @Override
    protected boolean activateEditActivity() {
        mSharedEditData.setStatusCode(EditOrViewDataViewModel.EDIT_OBJECT);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.current_layout, new CardSetEditFragment());
        ft.addToBackStack(null);
        ft.commit();
        return true;
    }

    @Override
    protected boolean activateDeleteActivity() {
        mSharedDatabase.getManager().deleteCardSet(mSharedEditData.getCardset());
        requireActivity().getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    protected boolean activateSaveActivity() {
        return false;
    }
}
