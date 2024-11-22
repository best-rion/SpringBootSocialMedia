package com.example.social.model;

import java.util.Date;

import com.example.social.dto.LikedPost;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment implements Comparable<Comment>
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private User author;
	@ManyToOne
	private Post post;
	@Column(columnDefinition="TEXT")
	private String content;
	private Date time;
	
	public Comment()
	{
		
	}
	
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}


	@Override
	public int compareTo(Comment o) {
		// TODO Auto-generated method stub
		return getTime().compareTo(o.getTime());
	}
}