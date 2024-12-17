package com.example.social.Social.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.social.Social.model.Post;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.MediaRepository;
import com.example.social.Social.repository.PostRepository;
import com.example.social.Social.repository.UserRepository;

@Controller
public class MakePostController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MediaRepository mediaRepository;

	private String photosDir = System.getProperty("user.dir") + "/src/main/upload/static/photos";

	private String videosDir = System.getProperty("user.dir") + "/src/main/upload/static/videos";
	

	@GetMapping("/makePost")
	public String makePostGet(Model model, Principal principal)
	{
		model.addAttribute("post", new Post());
		model.addAttribute("principal", principal);
		return "makePost";
	}
	
	
	@PostMapping("/makePost")
	public String postPost(@ModelAttribute Post post, Principal principal, @RequestParam("file") MultipartFile file) throws IOException
	{
		User author = userRepository.findByUsername(principal.getName());
		
		post.setAuthor(author);
		post.setTime(new Date());
		
		String[] fileName;
		String suffix = "";
		
		if ( !file.isEmpty() )
		{
			fileName  = file.getOriginalFilename().split("\\.");
			suffix= fileName[fileName.length-1].toLowerCase();
			
			
			if(suffix.equals("mp4") || suffix.equals("webm"))
			{
				post.media.setSuffix(suffix);
				post.media.setType("video");
			}
			else if( suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png") )
			{
				post.media.setSuffix(suffix);
				post.media.setType("photo");
			}
		}
		
		mediaRepository.save( post.media );
		
		postRepository.save( post );
		
		if ( !file.isEmpty() )
		{
			if( post.media.getType().equals("video") )
			{
				Path path = Paths.get(videosDir, Long.toString( post.getId() ) + "." + suffix);
				Files.write( path, file.getBytes());
			}
			else if(  post.media.getType().equals("photo") )
			{
				Path path = Paths.get(photosDir, Long.toString( post.getId() ) + "." + suffix);
				Files.write( path, file.getBytes());
			}
		}
		
		author.posts.add(post);
		
		userRepository.save(author);
		
		return "redirect:/";
	}
}