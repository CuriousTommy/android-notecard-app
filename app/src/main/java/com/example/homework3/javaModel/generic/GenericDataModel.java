package com.example.homework3.javaModel.generic;

import android.os.Parcel;
import android.os.Parcelable;

public class GenericDataModel implements Parcelable, Comparable<GenericDataModel> {
    private long id;
    private String subject;
    private String content;

    public GenericDataModel() {
        this("","");
    }

    public GenericDataModel(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //
    // Parcelable Functions
    //

    public static final Creator<GenericDataModel> CREATOR = new Creator<GenericDataModel>() {
        @Override
        public GenericDataModel createFromParcel(Parcel in) {
            return new GenericDataModel(in);
        }

        @Override
        public GenericDataModel[] newArray(int size) {
            return new GenericDataModel[size];
        }
    };

    protected GenericDataModel(Parcel in) {
        this.id = in.readLong();
        this.subject = in.readString();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.subject);
        dest.writeString(this.content);
    }

    @Override
    public int compareTo(GenericDataModel genericDataModel) {
        return this.subject.compareTo(genericDataModel.subject);
    }
}
