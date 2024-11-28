package com.example.social.Social.dto;

import com.example.social.Social.model.Comment;

public class CommentDto
{
	public Profile author;
	public String content;
	
	public CommentDto() {}
	
	public CommentDto(Comment comment)
	{
		this.author = new Profile(comment.getAuthor());
		this.content = comment.getContent();
	}
}