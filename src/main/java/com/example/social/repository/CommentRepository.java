package com.example.social.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.social.model.Comment;
import com.example.social.model.Post;

public interface CommentRepository extends CrudRepository<Comment,Integer>
{
	public List<Comment> findAll();
	public List<Comment> findByPost(Post post);
}