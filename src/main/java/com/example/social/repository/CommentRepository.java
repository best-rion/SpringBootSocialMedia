package com.example.social.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.social.model.Comment;

public interface CommentRepository extends CrudRepository<Comment,Integer>
{
	public List<Comment> findAll();
}