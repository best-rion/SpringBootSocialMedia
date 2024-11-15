package com.example.social.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.social.model.Post;
import com.example.social.model.User;

public interface PostRepository extends CrudRepository<Post,Integer>
{
	public List<Post> findAll();
	public List<Post> findByAuthor(User author);
	public Post findById(int id);
}