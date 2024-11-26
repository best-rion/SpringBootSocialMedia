package com.example.social.Social.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.social.Social.model.Comment;

public interface CommentRepository extends CrudRepository<Comment,Integer>
{
	public List<Comment> findAll();
}