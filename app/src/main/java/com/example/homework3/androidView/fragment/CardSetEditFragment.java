package com.example.homework3.androidView.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homework3.R;
import com.example.homework3.androidView.fragment.generic.GenericEditDataModelFragment;
import com.example.homework3.javaModel.CardSetDataModel;

public class CardSetEditFragment extends GenericEditDataModelFragment {
    @Override
    public void setTextViewDescription(TextView subject, TextView content) {
        subject.setText(R.string.cardset_name);
        content.setText(R.string.description);
    }

    @Override
    protected void createObjectAndStoreInDatabase(String subject, String content) {
        mSharedModel.getManager().insertCardSet(new CardSetDataModel(subject, content));
    }

    @Override
    protected void updateObjectInDatabase(String subject, String content) {
        CardSetDataModel cardset = mSharedEditData.getCardset();
        cardset.setSubject(subject);
        cardset.setContent(content);
        mSharedModel.getManager().updateCardSet(cardset);
    }

    @Override
    protected void setDataToTextView(EditText subject, EditText content) {
        subject.setText(mSharedEditData.getCardset().getSubject());
        content.setText(mSharedEditData.getCardset().getContent());
    }
}
