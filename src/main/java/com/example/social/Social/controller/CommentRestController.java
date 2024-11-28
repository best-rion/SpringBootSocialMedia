package com.example.social.Social.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.social.Social.dto.CommentDto;
import com.example.social.Social.dto.CommentForm;
import com.example.social.Social.model.Comment;
import com.example.social.Social.model.Post;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.PostRepository;
import com.example.social.Social.repository.UserRepository;

@RestController
public class CommentRestController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/getComments/{post_id}")
	public List<CommentDto> getComments(@PathVariable String post_id)
	{
		Post post = postRepository.findById(Integer.parseInt(post_id));
		
		List<CommentDto> commentDtos = new ArrayList<>();
		
		for (Comment comment: post.getComments())
		{
			commentDtos.add( new CommentDto(comment) );
		}
		
		return commentDtos;
	}
	
	@PostMapping("/make-comment-rest/{post_id}")
	public CommentDto comment(@PathVariable String post_id, @RequestBody String content, Principal principal)
	{
		System.out.println(content);
		
		User author = userRepository.findByUsername( principal.getName() );
		
		Post post = postRepository.findById( Integer.parseInt(post_id) );
		
		Comment comment = new Comment( content );
		comment.setAuthor( author );
		
		List<Comment> comments = post.getComments();
		comments.add(comment);
		
		post.setComments(comments);
		
		postRepository.save( post );
		
		CommentDto commentDto = new CommentDto(comment);
		
		return commentDto;
	}
}