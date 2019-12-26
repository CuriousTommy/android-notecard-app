package com.example.homework3.androidView.fragment.generic;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homework3.R;
import com.example.homework3.androidModel.DatabaseViewModel;
import com.example.homework3.androidModel.EditOrViewDataViewModel;


public abstract class GenericEditDataModelFragment extends Fragment {
    protected EditText mSubjectField;
    protected EditText mContentField;

    protected DatabaseViewModel mSharedModel;
    protected EditOrViewDataViewModel mSharedEditData;

//    private OnFragmentInteractionListener mListener;

    public GenericEditDataModelFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedModel = ViewModelProviders.of(requireActivity()).get(DatabaseViewModel.class);
        mSharedEditData = ViewModelProviders.of(requireActivity()).get(EditOrViewDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_generic_data_model, container, false);
        setHasOptionsMenu(true);

        mSubjectField = view.findViewById(R.id.edittext_subject);
        mContentField = view.findViewById(R.id.edittext_content);
        setTextViewDescription(view.findViewById(R.id.textview_subject), view.findViewById(R.id.textview_content));

        if (mSharedEditData.getStatusCode() == EditOrViewDataViewModel.EDIT_OBJECT) {
            setDataToTextView(mSubjectField, mContentField);
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

        add_item.setVisible(false);
        edit_item.setVisible(false);
        delete_item.setVisible(false);
        save_item.setVisible(true);
    }

    abstract public void setTextViewDescription(TextView subject, TextView content);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    abstract protected void createObjectAndStoreInDatabase(String subject, String content);
    abstract protected void updateObjectInDatabase(String subject, String content);

    abstract protected void setDataToTextView(EditText subject, EditText content);

    //
    // Options Menu Management
    //

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.save_item:
                return onSaveButtonPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean onSaveButtonPressed() {
        String subject = mSubjectField.getText().toString();
        String content = mContentField.getText().toString();

        switch (mSharedEditData.getStatusCode()) {
            case EditOrViewDataViewModel.CREATE_NEW_OBJECT:
                createObjectAndStoreInDatabase(subject, content);
                break;
            case EditOrViewDataViewModel.EDIT_OBJECT:
                updateObjectInDatabase(subject, content);
                break;
        }

        mSharedEditData.setStatusCode(EditOrViewDataViewModel.NOT_APPLICABLE);
        requireActivity().getSupportFragmentManager().popBackStack();
        return true;
    }
}
