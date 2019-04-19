package com.hichemromdhane.youtubechannel.Model.VideoDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDetails {
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("items")
    @Expose
private List<ItemVideo> items;


    public List<ItemVideo> getItems() {
        return items;
    }

    public void setItems(List<ItemVideo> contentDetails) {
        this.items = contentDetails;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


}

