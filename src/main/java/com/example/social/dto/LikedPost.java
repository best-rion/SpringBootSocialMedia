package com.example.social.dto;

import com.example.social.model.Post;

public class LikedPost implements Comparable<LikedPost>
{
	private Post post;
	private boolean liked;
	private boolean seeMore = false;
	
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
	public boolean isSeeMore() {
		return seeMore;
	}
	public void setSeeMore(boolean seeMore) {
		this.seeMore = seeMore;
	}
	
	@Override
	public int compareTo(LikedPost o) {
		// TODO Auto-generated method stub
		return -1 * getPost().getTime().compareTo(o.getPost().getTime());
	}
}