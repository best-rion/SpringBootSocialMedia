package com.example.social.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;

@Controller
public class PostController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Value("${videos.dir}")
	private String videosFolder;
	

	@GetMapping("/makePost")
	public String makePostGet(Model model, Principal principal)
	{
		model.addAttribute("post", new Post());
		model.addAttribute("principal", principal);
		return "makePost";
	}
	
	
	@PostMapping("/makePost")
	public String postPost(@ModelAttribute Post post, Principal principal, @RequestParam("video_file") MultipartFile file) throws IOException
	{
		User author = userRepository.findByUsername(principal.getName());

		
		post.setAuthor(author);
		post.setTime(new Date());
		
		if ( !file.isEmpty() )
		{
			post.setVideo(true);
		}
		else
		{
			post.setVideo(false);
		}
		
		postRepository.save(post);
		
		if ( !file.isEmpty() )
		{
			String fileName = file.getOriginalFilename();
			String suffix[] = fileName.split("\\.");
	
			Path path = Paths.get(videosFolder, Long.toString( post.getId() ) + "." + suffix[1]);
			Files.write( path, file.getBytes());
		}
		
		author.posts.add(post);
		
		userRepository.save(author);
		
		return "redirect:/";
	}
}