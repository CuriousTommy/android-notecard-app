package com.example.homework3.androidView.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.homework3.R;
import com.example.homework3.androidView.adapter.OnClickedElementListener;
import com.example.homework3.androidView.fragment.generic.GenericDataModelListFragment;
import com.example.homework3.javaModel.generic.GenericDataModel;

public class GenericDataHolder<B extends GenericDataModel> extends RecyclerView.ViewHolder {
    final View mView;
    final TextView mSubjectView;
    final TextView mContentView;
    public B mItem;

    public GenericDataHolder(View view) {
        super(view);
        mView = view;
        mSubjectView = (TextView) view.findViewById(R.id.item_number);
        mContentView = (TextView) view.findViewById(R.id.content);
    }

    public void setAndroidView() {
        mSubjectView.setText(mItem.getSubject());
        mContentView.setText(mItem.getContent());
    }

    public void createOnClickListener(OnClickedElementListener<B> listener) {
        mView.setOnClickListener((view) -> {
            if (listener != null) {
                listener.onInteraction(mItem);
            }
        });
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}