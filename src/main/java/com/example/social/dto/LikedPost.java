package com.example.social.dto;

import com.example.social.model.Post;

public class LikedPost
{
	private Post post;
	private boolean liked;
	
	public boolean isLiked() {
		return liked;
	}
	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
}