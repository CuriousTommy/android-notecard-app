package com.example.homework3.androidView.adapter;

import com.example.homework3.javaModel.generic.GenericDataModel;

public interface OnClickedElementListener <A extends GenericDataModel> {
    void onInteraction(A item);
}
