package com.hichemromdhane.youtubechannel.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hichemromdhane.youtubechannel.Model.CommentsDetails.ItemComment;
import com.hichemromdhane.youtubechannel.Interface.onViewClickListner;
import com.hichemromdhane.youtubechannel.Model.CommentsDetails.SnippedComment;
import com.hichemromdhane.youtubechannel.Model.CommentsDetails.SnippedUserCommentDetails;
import com.hichemromdhane.youtubechannel.Model.CommentsDetails.TopLevelComment;
import com.hichemromdhane.youtubechannel.R;
import com.hichemromdhane.youtubechannel.Utils.Messages;
import com.hichemromdhane.youtubechannel.Utils.MyUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    public CommentListAdapter(List<ItemComment> itemList, onViewClickListner onViewClickListner) {
        this.itemList = itemList;
        this.onViewClickListner = onViewClickListner;
    }

    List<ItemComment> itemList;
    onViewClickListner onViewClickListner;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        ;
        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.ViewHolder viewHolder, int i) {
        try
        {
            SnippedComment snippedComment = itemList.get(i).getSnippedComment();
            TopLevelComment topLevelComment = snippedComment.getTopLevelComment();
            SnippedUserCommentDetails snippedUserCommentDetails = topLevelComment.getSnippedUserCommentDetails();

    viewHolder.txt_description.setText(MyUtils.textToHtml(snippedUserCommentDetails.getTextDisplay()));
    viewHolder.txt_title.setText(MyUtils.textToHtml(snippedUserCommentDetails.getAuthorDisplayName()));

            Picasso.with(viewHolder.itemView.getContext())
                    .load(snippedUserCommentDetails.getAuthorProfileImageUrl())
                    .resize(90,90)
                    .placeholder(R.drawable.ic_youtube)
                    .error(R.drawable.ic_youtube)
                    .into(viewHolder.img_thumbnail);
        }catch (Exception e)
        {
            Messages.log(e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_title, txt_description;
        ImageView img_thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView)itemView.findViewById(R.id.user_comment);
            txt_description =(TextView)itemView.findViewById(R.id.txt_description_comment);
            img_thumbnail =(ImageView)itemView.findViewById(R.id.img_thumbnail_comment);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onViewClickListner.onClickListner(view, this.getLayoutPosition());
        }
    }
}
