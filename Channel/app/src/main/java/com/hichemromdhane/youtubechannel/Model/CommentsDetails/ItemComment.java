package com.hichemromdhane.youtubechannel.Model.CommentsDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemComment {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public SnippedComment getSnippedComment() {
        return snippedComment;
    }

    public void setSnippedComment(SnippedComment snippedComment) {
        this.snippedComment = snippedComment;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("snippet")
    @Expose
    private SnippedComment snippedComment;
}
