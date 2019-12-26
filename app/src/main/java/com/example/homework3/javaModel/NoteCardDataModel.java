package com.example.homework3.javaModel;

import android.os.Parcel;

import com.example.homework3.javaModel.generic.GenericDataModel;

public class NoteCardDataModel extends GenericDataModel {
    private long parent_id;

    public NoteCardDataModel() {
        super();
    }

    public NoteCardDataModel(String front, String back, long parent_id) {
        super(front, back);
        this.parent_id = parent_id;
    }

    public long getParentID() {
        return parent_id;
    }

    public void setParentID(long parent_id) {
        this.parent_id = parent_id;
    }

    protected NoteCardDataModel(Parcel in) {
        super(in);
        parent_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(parent_id);
    }
}
