package com.hichemromdhane.youtubechannel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hichemromdhane.youtubechannel.Adapter.VideoListAdapter;
import com.hichemromdhane.youtubechannel.Model.ChannelDetails.Item;
import com.hichemromdhane.youtubechannel.Model.ChannelDetails.Medium;
import com.hichemromdhane.youtubechannel.Model.ChannelDetails.Snippet;
import com.hichemromdhane.youtubechannel.Model.ChannelDetails.VideoListResponse;
import com.hichemromdhane.youtubechannel.R;
import com.hichemromdhane.youtubechannel.Retrofit.ApiClient;
import com.hichemromdhane.youtubechannel.Retrofit.ApiInterface;
import com.hichemromdhane.youtubechannel.Utils.Constants;
import com.hichemromdhane.youtubechannel.Utils.Messages;
import com.hichemromdhane.youtubechannel.Utils.MyDialog;
import com.hichemromdhane.youtubechannel.Interface.onViewClickListner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    MyDialog dialog;
    RecyclerView recyclerView;
    List<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setNoDataView(true, "No Video Found");

        getYoutubeVideoList();
    }

    private void init() {
        dialog = new MyDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void setNoDataView(boolean flag, String errorMsg) {
        try {
            findViewById(R.id.rl_no_data).setVisibility(!flag ? View.GONE : View.VISIBLE);
            recyclerView.setVisibility(flag ? View.GONE : View.VISIBLE);
            ((TextView) findViewById(R.id.txt_msg)).setText(errorMsg);
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }

    private void getYoutubeVideoList() {
        try {
            dialog.show();
            Map<String, String> data = new HashMap<>();
            data.put("part", "snippet");
            data.put("channelId", Constants.CHANNEL_ID);
            data.put("key", Constants.API_KEY);
            data.put("maxResults", "50");
            data.put("order", "date");

            dialog.setMessage("Finding videos...");
            Call<VideoListResponse> call = ApiClient.getClient(this).create(ApiInterface.class).getVideoList(data);
            call.enqueue(new Callback<VideoListResponse>() {
                @Override
                public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                    dialog.close();
                    if (!response.isSuccessful()) {
                        setNoDataView(true, response.message());
                        return;
                    }
                    VideoListResponse videoListResponse = response.body();
                    itemList = videoListResponse.getItems();
                    initializeAdapter();
                }

                @Override
                public void onFailure(Call<VideoListResponse> call, Throwable t) {
                    dialog.close();
                    setNoDataView(true, "E: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }

    private void initializeAdapter() {
        try {
            if (itemList == null)
                itemList = new ArrayList<>();
            VideoListAdapter adapter = new VideoListAdapter(itemList, listner);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            setNoDataView(adapter.getItemCount() == 0, "No Video Found");
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }


    onViewClickListner listner = new onViewClickListner() {
        @Override
        public void onClickListner(View view, int position) {
            Snippet snippet=itemList.get(position).getSnippet();
            Medium medium=snippet.getThumbnails().getMedium();


            playVideo(itemList.get(position).getId().getVideoId(),itemList.get(position).getSnippet().getPublishedAt(),
                    snippet.getTitle(),
                    itemList.get(position).getSnippet().getDescription(),
                    medium.getUrl());
        }
    };

    private void playVideo(String videoId,String datePublished,String title,String description,String thumbnail) {
        Intent intent = new Intent(this, DetailsActivtiy.class);
        intent.putExtra("videoid", videoId);
        intent.putExtra("title",title);
        intent.putExtra("datePublished",datePublished);
        intent.putExtra("description",description);
        intent.putExtra("thumbnail",thumbnail);
        startActivity(intent);
    }
}
