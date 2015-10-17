package com.cyclone.custom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 17/10/2015.
 */
public class ClubFeedAdapter extends RecyclerView.Adapter<ClubFeedAdapter.ViewHolder> {

	private List<Post> datas;

	public ClubFeedAdapter(){
		datas = new ArrayList<>();
	}

	public void add(Post post){
		datas.add(post);
	}

	public void add(Post post, int position){
		datas.add(position, post);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_club_feed,
				parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Post p = datas.get(position);
		holder.imgUser.setImageResource(R.drawable.background_login);
		holder.txtHeaderName.setText(p.headerName);
		holder.txtHeaderInfo.setText(p.timestamp + " | " + p.playlistType);
		holder.imgPost.setImageResource(R.drawable.background_login);
		holder.txtPostTitle.setText(p.postTitle);
		holder.txtPostContent.setText(p.postContent);
		holder.txtPostInfo.setText(p.postInfo);
		holder.txtLikesInfo.setText(p.likesCount + " likes | " + p.commentCount + " comments");
		holder.btnLike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		holder.btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		holder.btnComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		if(p.type == Post.TYPE_NEWS) {
			holder.imgUser.setVisibility(View.GONE);
			holder.txtHeaderInfo.setText(p.timestamp);
			holder.txtPostInfo.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{

		public ImageView imgUser;
		public TextView txtHeaderName;
		public TextView txtHeaderInfo;
		public ImageView imgPost;
		public TextView txtPostTitle;
		public TextView txtPostContent;
		public TextView txtPostInfo;
		public TextView txtLikesInfo;
		public ImageButton btnLike;
		public ImageButton btnShare;
		public ImageButton btnComment;

		public ViewHolder(View v) {
			super(v);
			imgUser = (ImageView) v.findViewById(R.id.img_user);
			txtHeaderName = (TextView) v.findViewById(R.id.txt_header_name);
			txtHeaderInfo = (TextView) v.findViewById(R.id.txt_header_info);
			imgPost = (ImageView) v.findViewById(R.id.img_post);
			txtPostTitle = (TextView) v.findViewById(R.id.txt_post_title);
			txtPostContent = (TextView) v.findViewById(R.id.txt_post_content);
			txtPostInfo = (TextView) v.findViewById(R.id.txt_post_info);
			txtLikesInfo = (TextView) v.findViewById(R.id.txt_likes_info);
			btnLike = (ImageButton) v.findViewById(R.id.btn_like);
			btnShare = (ImageButton) v.findViewById(R.id.btn_share);
			btnComment = (ImageButton) v.findViewById(R.id.btn_comment);
		}
	}
}
