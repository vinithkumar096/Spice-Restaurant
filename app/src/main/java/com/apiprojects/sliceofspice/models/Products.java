package com.apiprojects.sliceofspice.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable {
String name;
String category;
String price;
String desc;
String image;
String key;
String uuid;
String offer;

    public Products(String name, String category,String image, String price, String desc,String uuid,String offer) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.desc = desc;
        this.image=image;
        this.uuid=uuid;
        this.offer=offer;
    }

    public Products() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public static Creator<Products> getCREATOR() {
        return CREATOR;
    }

    protected Products(Parcel in) {
        name = in.readString();
        category = in.readString();
        price = in.readString();
        desc = in.readString();
        image = in.readString();
        key = in.readString();
        uuid = in.readString();
        offer = in.readString();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(price);
        parcel.writeString(desc);
        parcel.writeString(image);
        parcel.writeString(key);
        parcel.writeString(uuid);
        parcel.writeString(offer);
    }
}
