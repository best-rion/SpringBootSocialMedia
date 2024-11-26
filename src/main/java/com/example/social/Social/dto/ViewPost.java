package com.example.social.Social.dto;

import com.example.social.Social.model.Post;

public class ViewPost implements Comparable<ViewPost>
{
	private Post post;
	private boolean liked;
	private boolean seeMore;
	
	public ViewPost(){}
	
	
	public ViewPost( Post post )
	{
		this.post = post;
		this.liked = false;
		this.seeMore = false;
	}
	
	
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
	public int compareTo(ViewPost o) {
		// TODO Auto-generated method stub
		return -1 * getPost().getTime().compareTo(o.getPost().getTime());
	}
}