package com.apiprojects.sliceofspice.models;

public class CartDetails {
    String name;
    String price;
    String desc;
    String image;
    String key;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    String uuid;
    String status;
    String offer;
    String offerPrice;
    public CartDetails(String name, String price, String desc, String image, String uuid,String status, String offer,String offerPrice) {
        this.name = name;
        this.status=status;
        this.price = price;
        this.desc = desc;
        this.image = image;
        this.uuid = uuid;
        this.offer=offer;
        this.offerPrice=offerPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public CartDetails() {
    }

}
