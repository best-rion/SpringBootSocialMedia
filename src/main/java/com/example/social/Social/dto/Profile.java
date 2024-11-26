package com.example.social.Social.dto;

import com.example.social.Social.model.User;

public class Profile
{
	private long id;
	private String name;
	private String username;
	private String description;
	private String image_suffix;
	
	public Profile() {}
	
	public Profile(User user)
	{
		this.id = user.getId();
		this.name = user.getName();
		this.username = user.getUsername();
		this.description = user.getDescription();
		this.image_suffix = user.getImage_suffix();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage_suffix() {
		return image_suffix;
	}

	public void setImage_suffix(String image_suffix) {
		this.image_suffix = image_suffix;
	}
}