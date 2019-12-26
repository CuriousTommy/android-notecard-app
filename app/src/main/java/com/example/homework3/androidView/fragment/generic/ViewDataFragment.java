package com.example.homework3.androidView.fragment.generic;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.homework3.R;
import com.example.homework3.androidModel.DatabaseViewModel;
import com.example.homework3.androidModel.EditOrViewDataViewModel;
import com.example.homework3.androidView.fragment.NoteCardEditFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDataFragment extends Fragment implements View.OnClickListener {
    private boolean isFront;
    private Button mCardButton;
    private DatabaseViewModel mDatabase;
    private EditOrViewDataViewModel mEditViewDatabase;


    public ViewDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditViewDatabase = ViewModelProviders.of(requireActivity()).get(EditOrViewDataViewModel.class);
        mDatabase = ViewModelProviders.of(requireActivity()).get(DatabaseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_data, container, false);
        setHasOptionsMenu(true);

        mCardButton = view.findViewById(R.id.card_button);
        mCardButton.setText(mEditViewDatabase.getNotecard().getSubject());
        mCardButton.setOnClickListener(this);
        isFront = true;

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem add_item = menu.findItem(R.id.add_item);
        MenuItem edit_item = menu.findItem(R.id.edit_item);
        MenuItem delete_item = menu.findItem(R.id.delete_item);
        MenuItem save_item = menu.findItem(R.id.save_item);

        add_item.setVisible(false);
        edit_item.setVisible(true);
        delete_item.setVisible(true);
        save_item.setVisible(false);
    }

    @Override
    public void onClick(View view) {
        if (isFront) {
            mCardButton.setText(mEditViewDatabase.getNotecard().getContent());
        } else {
            mCardButton.setText(mEditViewDatabase.getNotecard().getSubject());
        }

        isFront = !isFront;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_item:
                return onDeleteItemClicked();
            case R.id.edit_item:
                return onEditItemSelected();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean onDeleteItemClicked() {
        mDatabase.getManager().deleteNoteCard(mEditViewDatabase.getNotecard());
        mEditViewDatabase.setNotecard(null);
        requireActivity().getSupportFragmentManager().popBackStack();
        return true;
    }

    public boolean onEditItemSelected() {
        mEditViewDatabase.setStatusCode(EditOrViewDataViewModel.EDIT_OBJECT);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.current_layout, new NoteCardEditFragment());
        ft.addToBackStack(null);
        ft.commit();
        return true;
    }
}
