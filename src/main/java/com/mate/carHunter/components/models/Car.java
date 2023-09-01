package com.mate.carHunter.components.models;

import org.json.JSONObject;

import java.math.BigInteger;

public class Car {

    private BigInteger marketplaceId;
    private String title;
    private String distance;
    private String price;
    private String link;
    private String image;
    private String imageContent;
    private String sourceUrl;



    public void setMarketplaceId(BigInteger marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public BigInteger getMarketplaceId() {
        return marketplaceId;
    }

    public JSONObject getJSONInfo() {

        JSONObject jsonObject = new JSONObject();
        jsonData(jsonObject);

        return jsonObject;
    }
    public JSONObject jsonData(JSONObject jsonObject){
        jsonObject.put("title", title);
        jsonObject.put("image", image);
        jsonObject.put("distance", distance);
        jsonObject.put("price", price);
        jsonObject.put("link", link);
        jsonObject.put("marketplaceId", marketplaceId);
        jsonObject.put("imageContent", imageContent);
        jsonObject.put("sourceUrl", sourceUrl);
        return jsonObject;

    }


}
