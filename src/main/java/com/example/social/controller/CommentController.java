package com.example.social.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.social.dto.CommentForm;
import com.example.social.model.Comment;
import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;

@Controller
public class CommentController
{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@PostMapping("/make-comment/{path}")
	public String comment(@PathVariable String path, @ModelAttribute CommentForm form, Principal principal)
	{
		User author = userRepository.findByUsername( principal.getName() );
		
		Post post = postRepository.findById( form.getPost_id() );
		
		Comment comment = new Comment( form.getContent() );
		comment.setAuthor( author );
		
		List<Comment> comments = post.getComments();
		comments.add(comment);
		
		post.setComments(comments);
		
		postRepository.save( post );
		
		
		
		String urls[] = path.split("-");
		String url = "";
		for (String u: urls)
		{
			url += "/"+u;
		}
		if(url.equals("/null"))
			url = "/";
		
		System.out.println(url);
		
		
		
		
		return "redirect:"+url;
	}
}