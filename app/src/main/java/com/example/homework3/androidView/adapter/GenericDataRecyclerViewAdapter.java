package com.example.homework3.androidView.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homework3.R;
import com.example.homework3.androidView.adapter.holder.GenericDataHolder;
import com.example.homework3.javaModel.generic.GenericDataModel;

import java.util.List;


public class GenericDataRecyclerViewAdapter<A extends GenericDataModel> extends RecyclerView.Adapter<GenericDataHolder<A>> {

    private final List<A> mValues;
    private final OnClickedElementListener<A> mListener;

    public GenericDataRecyclerViewAdapter(List<A> items, OnClickedElementListener<A> listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public GenericDataHolder<A> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardset_element, parent, false);
        return new GenericDataHolder<>(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericDataHolder<A> holder, int position) {
        holder.mItem = mValues.get(position);
        holder.setAndroidView();
        holder.createOnClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /*
        Container that holds the view components.
     */


}
