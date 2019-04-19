package com.hichemromdhane.youtubechannel.Model.VideoDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemVideo {
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }
}
