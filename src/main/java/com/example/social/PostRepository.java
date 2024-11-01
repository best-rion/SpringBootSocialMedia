package com.example.social;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer>
{
	public List<Post> findAll();
}