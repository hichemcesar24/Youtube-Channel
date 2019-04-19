package com.hichemromdhane.youtubechannel.Model.CommentsDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopLevelComment {
    @SerializedName("id")
    @Expose
  private String id;
    @SerializedName("kind")
    @Expose
  private String kind;
    @SerializedName("snippet")
    @Expose
  private SnippedUserCommentDetails snippedUserCommentDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public SnippedUserCommentDetails getSnippedUserCommentDetails() {
        return snippedUserCommentDetails;
    }

    public void setSnippedUserCommentDetails(SnippedUserCommentDetails snippedUserCommentDetails) {
        this.snippedUserCommentDetails = snippedUserCommentDetails;
    }
}
