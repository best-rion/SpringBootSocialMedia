package com.example.social.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Post
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String title;
	@ManyToOne
	private User author;
	private Date time;
	@Column(columnDefinition="TEXT")
	private String content;
	@OneToMany(
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<Comment> comments;
	private int numOfLikes;
	@OneToOne
	public Media media;
	
	
	
	public Post()
	{
		this.comments = new ArrayList<>();
		this.media = new Media();
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public int getNumOfLikes() {
		return numOfLikes;
	}


	public void setNumOfLikes(int numOfLikes) {
		this.numOfLikes = numOfLikes;
	}
	
}