package com.apiprojects.sliceofspice.models;

public class Favourites {
    String name;
    String price;
    String desc;
    String image;
    String key;
    String uuid;

    public Favourites() {
    }

    public Favourites(String name, String price, String desc, String image, String uuid) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.image = image;
        this.uuid = uuid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
