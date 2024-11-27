package com.example.social.Messaging.dto;

import com.example.social.Messaging.model.Message;
import com.example.social.Social.model.User;

public class MessageNotification
{
	public User sender;
	public int numberOfMessages;
	public Message lastMessage;
}