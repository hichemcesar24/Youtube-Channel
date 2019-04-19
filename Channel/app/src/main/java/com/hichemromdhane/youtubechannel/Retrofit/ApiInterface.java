package com.hichemromdhane.youtubechannel.Retrofit;

import com.hichemromdhane.youtubechannel.Model.CommentsDetails.CommentDetails;
import com.hichemromdhane.youtubechannel.Model.VideoDetails.VideoDetails;
import com.hichemromdhane.youtubechannel.Model.ChannelDetails.VideoListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET(MyURL.SEARCH_VIDEO)
    Call<VideoListResponse> getVideoList(@QueryMap Map<String, String> data);
   @GET(MyURL.GET_VIDEO)
    Call<VideoDetails>getVideoDetails(@QueryMap Map<String, String> data);
   @GET(MyURL.GET_COMMENT)
    Call<CommentDetails>getCommentsDetails(@QueryMap Map<String, String> data);

}
