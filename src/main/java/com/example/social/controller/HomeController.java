package com.example.social.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.social.dto.CommentForm;
import com.example.social.dto.EditAccountForm;
import com.example.social.dto.LikedPost;
import com.example.social.dto.Profile;
import com.example.social.model.Comment;
import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.CommentRepository;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;

@Controller
public class HomeController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Value("${images.dir}")
	private String imagesFolder;
	
	
	
	@GetMapping("/")
	public String home(Model model, Principal principal)
	{
		List<Post> posts = new ArrayList<>();
		
		User current_user = userRepository.findByUsername(principal.getName());
		Set<User> friends = current_user.friends;
		for (User friend: friends)
		{
			posts.addAll(friend.posts);
		}
		
		List<LikedPost> likedPosts = new ArrayList<>();
		
		for (Post post: posts)
		{
			//
			LikedPost likedPost = new LikedPost();
			likedPost.setPost(post);
			likedPost.setLiked(false);
			if(current_user.getLikedPosts().contains( post ))
			{
				likedPost.setLiked(true);
			}
			likedPosts.add( likedPost );
			
			
			// Make contents shorter
			String subContent = "";
			
			if (post.getContent().length() > 200)
			{
				subContent = post.getContent().substring(0, 199);
				
				post.setContent( subContent );
				
				likedPost.setSeeMore(true);
			}
			
			// Make lines shorter
			subContent = "";

			String[] lines = post.getContent().split("\r\n|\r|\n");
			
			if ( lines.length > 5)
			{
				for (int i=0; i<5; i++)
				{
					subContent += lines[i]+"\n";
				}
				
				post.setContent( subContent );
				
				likedPost.setSeeMore(true);
			}
			
			// Sort Comments
			
			Collections.sort(post.getComments());
		}
		
		Collections.sort(likedPosts);
		
		model.addAttribute("commentForm", new CommentForm());
		model.addAttribute("likedPosts", likedPosts);
		model.addAttribute("principal", principal);
		return "home";
	}
	
	
	
	@PostMapping("/make-comment")
	public String comment(@ModelAttribute CommentForm form, Principal principal)
	{
		User author = userRepository.findByUsername( principal.getName() );
		
		Post post = postRepository.findById( form.getPost_id() );
		
		Comment comment = new Comment();
		comment.setContent( form.getContent() );
		comment.setAuthor( author );
		comment.setTime( new Date() );
		comment.setPost( post );
		
		
		List<Comment> comments = post.getComments();
		comments.add(comment);
		post.setComments(comments);
		
		postRepository.save( post );
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("/profile/{username}")
	public String profile(@PathVariable String username, Model model, Principal principal)
	{
		User current_user = userRepository.findByUsername( principal.getName() );
		
		User person = userRepository.findByUsername(username);
		Profile profile = new Profile(person);
		
		List<Post> posts = postRepository.findByAuthor(person);
		
		
		List<LikedPost> likedPosts = new ArrayList<>();
		for (Post post: posts)
		{
			LikedPost likedPost = new LikedPost();
			likedPost.setPost(post);
			likedPost.setLiked(false);
			if(current_user.getLikedPosts().contains( post ))
			{
				likedPost.setLiked(true);
			}
			likedPosts.add( likedPost );
			
			

			
			// Make contents shorter
			String subContent = "";
			
			if (post.getContent().length() > 200)
			{
				subContent = post.getContent().substring(0, 199);
				
				post.setContent( subContent );
				
				likedPost.setSeeMore(true);
			}
			
			// Make lines shorter
			subContent = "";

			String[] lines = post.getContent().split("\r\n|\r|\n");
			
			if ( lines.length > 5)
			{
				for (int i=0; i<5; i++)
				{
					subContent += lines[i]+"\n";
				}
				
				post.setContent( subContent );
				
				likedPost.setSeeMore(true);
			}
			
			// Sort Comments
			
			Collections.sort(post.getComments());
		}
		
		Collections.sort(likedPosts);
		
		model.addAttribute("profile", profile);
		model.addAttribute("likedPosts", likedPosts);
		model.addAttribute("isMyProfile", person.getUsername().equals(principal.getName()));
		model.addAttribute("principal", principal);
		return "profile";
	}
	
	
	
	@GetMapping("/account")
	public String accountGet(Model model, Principal principal)
	{
		User current_user = userRepository.findByUsername(principal.getName());
		
		model.addAttribute("form", new EditAccountForm());
		model.addAttribute("current_user", current_user);
		model.addAttribute("principal", principal);
		return "account";
	}
	
	
	
	@PostMapping("/account")
	public String accountPost(@ModelAttribute EditAccountForm form, Principal principal, @RequestParam("image") MultipartFile file ) throws IOException
	{
		User current_user = userRepository.findByUsername(principal.getName());
		
		if ( !file.isEmpty() )
		{
			String fileName = file.getOriginalFilename();
			String suffix[] = fileName.split("\\.");
			Path path = Paths.get(imagesFolder, Long.toString( current_user.getId() ) + "." + suffix[1]);
			Files.write( path, file.getBytes());
			current_user.setImage_suffix( suffix[1] );
		}
		
		current_user.setName( form.getName() );
		current_user.setDescription( form.getDescription() );
		userRepository.save( current_user );
		
		
		return "redirect:/profile/" + principal.getName();
	}
	
	
	
	@GetMapping("/post/{id}")
	public String postGet(@PathVariable int id, Model model, Principal principal)
	{
		Post post = postRepository.findById(id);
		
		User current_user = userRepository.findByUsername(principal.getName());
		
		if (post != null)
		{
			LikedPost likedPost = new LikedPost();
			likedPost.setPost(post);
			likedPost.setLiked(false);
			if(current_user.getLikedPosts().contains( post ))
			{
				likedPost.setLiked(true);
			}
			
			model.addAttribute("principal", principal);
			model.addAttribute("likedPost", likedPost);
			return "post";
		}
		else
		{
			return "redirect:/error";
		}
	}
	

}

