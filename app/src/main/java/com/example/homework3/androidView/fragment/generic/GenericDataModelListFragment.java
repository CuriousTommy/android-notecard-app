package com.example.homework3.androidView.fragment.generic;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.homework3.R;
import com.example.homework3.androidModel.DatabaseViewModel;
import com.example.homework3.androidModel.EditOrViewDataViewModel;
import com.example.homework3.androidView.adapter.GenericDataRecyclerViewAdapter;
import com.example.homework3.androidView.adapter.OnClickedElementListener;
import com.example.homework3.javaModel.generic.GenericDataModel;

import java.util.ArrayList;


abstract public class GenericDataModelListFragment <A extends GenericDataModel> extends Fragment {
    protected OnClickedElementListener<A> mListener;
    protected DatabaseViewModel mSharedDatabase;
    protected EditOrViewDataViewModel mSharedEditData;

    protected boolean isAddMenuShown = false;
    protected boolean isEditMenuShown = false;
    protected boolean isDeleteMenuShown = false;
    protected boolean isSaveMenuShown = false;


    public GenericDataModelListFragment() {
    }

//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static GenericDataModelListFragment newInstance(int columnCount) {
//        GenericDataModelListFragment fragment = new GenericDataModelListFragment();
//        return fragment;
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedDatabase = ViewModelProviders.of(requireActivity()).get(DatabaseViewModel.class);
        mSharedEditData = ViewModelProviders.of(requireActivity()).get(EditOrViewDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cardset_list, container, false);
        setHasOptionsMenu(true);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            ArrayList<A> arrayList = getDatabaseList();

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new GenericDataRecyclerViewAdapter<A>(arrayList, mListener));
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem add_item = menu.findItem(R.id.add_item);
        MenuItem edit_item = menu.findItem(R.id.edit_item);
        MenuItem delete_item = menu.findItem(R.id.delete_item);
        MenuItem save_item = menu.findItem(R.id.save_item);

        add_item.setVisible(isAddMenuShown);
        edit_item.setVisible(isEditMenuShown);
        delete_item.setVisible(isDeleteMenuShown);
        save_item.setVisible(isSaveMenuShown);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //
    // Helper
    //

    abstract protected ArrayList<A> getDatabaseList();

    //
    // Options Menu Management
    //

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.add_item:
                return activateAddActivity();
            case R.id.edit_item:
                return activateEditActivity();
            case R.id.delete_item:
                return activateDeleteActivity();
            case R.id.save_item:
                return activateSaveActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    abstract protected boolean activateAddActivity();
    abstract protected boolean activateEditActivity();
    abstract protected boolean activateDeleteActivity();
    abstract protected boolean activateSaveActivity();
}
