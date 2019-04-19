package com.hichemromdhane.youtubechannel.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hichemromdhane.youtubechannel.Adapter.CommentListAdapter;
import com.hichemromdhane.youtubechannel.Model.CommentsDetails.CommentDetails;
import com.hichemromdhane.youtubechannel.Model.CommentsDetails.ItemComment;
import com.hichemromdhane.youtubechannel.Model.VideoDetails.ItemVideo;
import com.hichemromdhane.youtubechannel.Model.VideoDetails.VideoDetails;
import com.hichemromdhane.youtubechannel.R;
import com.hichemromdhane.youtubechannel.Retrofit.ApiClient;
import com.hichemromdhane.youtubechannel.Retrofit.ApiInterface;
import com.hichemromdhane.youtubechannel.Utils.Constants;
import com.hichemromdhane.youtubechannel.Utils.Messages;
import com.hichemromdhane.youtubechannel.Utils.MyDialog;
import com.hichemromdhane.youtubechannel.Interface.onViewClickListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivtiy extends AppCompatActivity  {
    ImageView imageViewThumbnail;
    TextView textViewDescription,textViewTitle,textViewDatePublished,textViewDuration;
    List<ItemVideo> itemList;
    List<ItemComment>itemCommentList;
    RecyclerView recyclerView;
    MyDialog dialog;
    Button buttonPlayVideo;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        textViewDatePublished =(TextView)findViewById(R.id.date_published);
        textViewDescription = (TextView)findViewById(R.id.description_detailds);
        textViewTitle = (TextView)findViewById(R.id.title_details);
        textViewDuration =(TextView)findViewById(R.id.duration);
        imageViewThumbnail =(ImageView)findViewById(R.id.image_thub_details);
        init();
        if(getIntent().getStringExtra("title")!=null){
            textViewTitle.setText(getIntent().getStringExtra("title"));
        }
        try {
            Picasso.with(this)
                    .load(getIntent().getStringExtra("thumbnail"))
                    .placeholder(R.drawable.ic_youtube)
                    .error(R.drawable.ic_youtube)
                    .into(imageViewThumbnail);
        }catch (Exception e)
        {
            Messages.log(e.toString());
        }

        if(getIntent().getStringExtra("datePublished")!=null){

            textViewDatePublished.setText(getIntent().getStringExtra("datePublished"));
        }
        if(getIntent().getStringExtra("description")!=null){
            textViewDescription.setText(getIntent().getStringExtra("description"));
        }
        getYoutubeVideoList(getIntent().getStringExtra("videoid"));
        getListComment(getIntent().getStringExtra("videoid"));
        buttonPlayVideo =(Button)findViewById(R.id.play_video);
        buttonPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         String videoId = getIntent().getStringExtra("videoid");
                Intent intent = new Intent(DetailsActivtiy.this, PlayerActivity.class);
                intent.putExtra("videoid", videoId);
                startActivity(intent);
            }
        });



    }
    private void init() {
        dialog = new MyDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewComment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getYoutubeVideoList(String videoId) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("id", videoId);
            data.put("part", "contentDetails");
            data.put("key", Constants.API_KEY);

   Call<VideoDetails> videoDetailsCall=ApiClient.getClient(this).create(ApiInterface.class).getVideoDetails(data);
            videoDetailsCall.enqueue(new Callback<VideoDetails>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response) {
                    VideoDetails videoListResponse = response.body();
                   itemList= videoListResponse.getItems();
                    String duration = itemList.get(0).getContentDetails().getDuration();
                    duration= duration.replace("PT","");
                    duration= duration.replace("M",":");
                    duration = duration.replace("H",":");



                    textViewDuration.setText(duration.toString());
                }

                @Override
                public void onFailure(Call<VideoDetails> call, Throwable t) {
                }
            });

        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }

    private void getListComment(String videoId){
        try {

            dialog.show();

        Map<String, String> data = new HashMap<>();
        data.put("part", "snippet");
        data.put("videoId", videoId);
        data.put("key", Constants.API_KEY);
        data.put("maxResults", "100");
        data.put("order", "time");
        dialog.setMessage("Finding ...");
        Call<CommentDetails> call = ApiClient.getClient(this).create(ApiInterface.class).getCommentsDetails(data);
        call.enqueue(new Callback<CommentDetails>() {
            @Override
            public void onResponse(Call<CommentDetails> call, Response<CommentDetails> response) {
                dialog.close();
                if (!response.isSuccessful()) {
                    setNoDataView(true, response.message());
                    return;
                }
                CommentDetails commentDetails = response.body();
                itemCommentList = commentDetails.getItems();
                CommentListAdapter adapter = new CommentListAdapter(itemCommentList, listner);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommentDetails> call, Throwable t) {
                dialog.close();
                setNoDataView(true, "E: " + t.getLocalizedMessage());

            }
        });  } catch (Exception e) {
            Messages.log(e.toString());
        }

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
    onViewClickListner listner = new onViewClickListner() {
        @Override
        public void onClickListner(View view, int position) {
        }
    };
    private void initializeAdapter() {
        try {
            if (itemCommentList == null)
                itemCommentList = new ArrayList<>();
            CommentListAdapter adapter = new CommentListAdapter(itemCommentList, listner);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            setNoDataView(adapter.getItemCount() == 0, "No Video Found");
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }


}
