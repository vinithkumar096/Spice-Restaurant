package com.apiprojects.sliceofspice.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

    String uuid;
    String name;
    String imageurl;
    String key;


    public Category(String name, String imageurl, String uuid) {
        this.name = name;
        this.imageurl = imageurl;
        this.uuid=uuid;
    }

    public Category() {
    }

    protected Category(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        imageurl = in.readString();
        key = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(name);
        parcel.writeString(imageurl);
        parcel.writeString(key);
    }
}
