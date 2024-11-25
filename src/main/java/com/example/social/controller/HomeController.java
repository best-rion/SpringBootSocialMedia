package com.example.social.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.social.dto.CommentForm;
import com.example.social.dto.ViewPost;
import com.example.social.dto.Profile;
import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;
import com.example.social.service.PostService;

@Controller
public class HomeController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostService postService;
	
	
	@GetMapping("/")
	public String home(Model model, Principal principal)
	{
		User current_user = userRepository.findByUsername(principal.getName());
		
		List<ViewPost> viewPosts = postService.postsForHome(current_user);
		
		model.addAttribute("commentForm", new CommentForm());
		model.addAttribute("viewPosts", viewPosts);
		model.addAttribute("principal", principal);
		return "home";
	}
	
	
	
	@GetMapping("/profile/{username}")
	public String profile(@PathVariable String username, Model model, Principal principal)
	{
		User current_user = userRepository.findByUsername( principal.getName() );
		
		User profile_owner = userRepository.findByUsername(username);
		
		Profile profile = new Profile( profile_owner );
		
		List<ViewPost> viewPosts = postService.postsForProfile(current_user, profile_owner);
		
		model.addAttribute("profile", profile);
		model.addAttribute("viewPosts", viewPosts);
		model.addAttribute("isMyProfile", profile_owner.getUsername().equals(principal.getName()));
		model.addAttribute("principal", principal);
		return "profile";
	}
	
	
	
	@GetMapping("/post/{id}")
	public String postGet(@PathVariable int id, Model model, Principal principal)
	{
		Post post = postRepository.findById(id);
		
		User current_user = userRepository.findByUsername(principal.getName());
		
		if (post != null)
		{
			ViewPost viewPost = new ViewPost( post );

			if(current_user.likedThisPost( post ))
			{
				viewPost.setLiked(true);
			}
			
			model.addAttribute("principal", principal);
			model.addAttribute("viewPost", viewPost);
			model.addAttribute("isMyPost", post.isAuthoredBy(current_user));
			return "post";
		}
		else
		{
			return "redirect:/error";
		}
	}
}

