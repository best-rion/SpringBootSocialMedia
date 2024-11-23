package com.example.social.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;

@RestController
public class LikeRestController
{
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	UserRepository userRepo;
	
	
	@PutMapping("/like")
	public String like(@RequestBody String id, Principal principal)
	{
		User current_user = userRepo.findByUsername( principal.getName() );
		Set<Post> myLikedPosts = current_user.getLikedPosts();

		Post post = postRepo.findById( Integer.parseInt( id ) );
		
		if (myLikedPosts.contains(post))
		{
			return "0";
		}
		else
		{
			post.setNumOfLikes( post.getNumOfLikes() + 1 );
			postRepo.save( post );
			
			
			myLikedPosts.add(post);
			current_user.setLikedPosts( myLikedPosts );
			userRepo.save( current_user );
		}
		
		return "1";
	}
	
	
	
	
	@PutMapping("/dislike")
	public String dislike(@RequestBody String id, Principal principal)
	{
		User current_user = userRepo.findByUsername( principal.getName() );
		Set<Post> myLikedPosts = current_user.getLikedPosts();

		Post post = postRepo.findById( Integer.parseInt( id ) );
		
		if (myLikedPosts.contains(post))
		{
			post.setNumOfLikes( post.getNumOfLikes() - 1 );
			postRepo.save( post );
			
			myLikedPosts.remove(post);
			current_user.setLikedPosts( myLikedPosts );
			userRepo.save( current_user );
			return "1";
		}
		
		return "0";
	}
}