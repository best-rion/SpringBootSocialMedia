package com.example.social.Game.dto;

import com.example.social.Social.model.User;

public class ActiveUser
{
	private String name;
	private String username;
	private String src;
	
	public ActiveUser() {};
	
	public ActiveUser( User user )
	{
		this.name = user.getName();
		this.username = user.getUsername();
		this.src = user.getSrc();
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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}