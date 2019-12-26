package com.example.homework3.androidView.fragment;

import android.widget.EditText;
import android.widget.TextView;

import com.example.homework3.R;
import com.example.homework3.androidView.fragment.generic.GenericEditDataModelFragment;
import com.example.homework3.javaModel.NoteCardDataModel;

public class NoteCardEditFragment extends GenericEditDataModelFragment {
    @Override
    public void setTextViewDescription(TextView subject, TextView content) {
        subject.setText(R.string.front_card);
        content.setText(R.string.back_card);
    }

    @Override
    protected void createObjectAndStoreInDatabase(String subject, String content) {
        mSharedModel.getManager().insertNodeCard(
                new NoteCardDataModel(subject, content, mSharedEditData.getCardset().getId())
        );
    }

    @Override
    protected void updateObjectInDatabase(String subject, String content) {
        NoteCardDataModel note_card =  mSharedEditData.getNotecard();
        note_card.setSubject(subject);
        note_card.setContent(content);
        mSharedModel.getManager().updateNodeCard(note_card);
    }

    @Override
    protected void setDataToTextView(EditText subject, EditText content) {
        subject.setText(mSharedEditData.getNotecard().getSubject());
        content.setText(mSharedEditData.getNotecard().getContent());
    }
}
