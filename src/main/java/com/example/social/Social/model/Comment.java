package com.example.social.Social.model;

import java.util.Date;

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
	@Column(columnDefinition="TEXT")
	private String content;
	private Date time;
	
	public Comment()
	{
		
	}
	
	public Comment(String content)
	{
		this.content =  content;
		this.time = new Date();
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