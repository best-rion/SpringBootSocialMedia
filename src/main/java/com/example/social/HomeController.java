package com.example.social;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	
	@GetMapping("/")
	public String home(Model model)
	{
		User rion = userRepository.findById(1);
		
		List<Post> posts = postRepository.findAll();
		Post post1 = new Post();
		post1.setId(1);
		post1.setTitle("Rion is the best");
		post1.setAuthor(rion);
		post1.setTime(new Date());
		post1.setContent("Rion is the best.\nRion is the greatest.");
		
		Post post2 = new Post();
		post2.setId(2);
		post2.setTitle("Salim Khan Sensible");
		post2.setAuthor(rion);
		post2.setTime(new Date());
		post2.setContent("Salim Khan is very sensible person; and a smart person.");
		
		posts.add(post1);
		posts.add(post2);
		
		model.addAttribute("posts", posts);
		return "home";
	}
	
	@GetMapping("/friends")
	public String friends(Model model)
	{
		User user1 = new User();
		user1.setId(1);
		user1.setName("Rion");
		user1.setDescription("The best guy");
		
		User user2 = new User();
		user2.setId(2);
		user2.setName("Sameer");
		user2.setDescription("Cool dude of class");
		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		
		model.addAttribute("users", users);
		return "friends";
	}
	
	@GetMapping("/login")
	public String loginGet()
	{
		return "login";
	}
	
	@GetMapping("/post")
	public String postGet(Model model)
	{
		model.addAttribute("post", new Post());
		return "post";
	}
	
	@PostMapping("/post")
	public String postPost(@ModelAttribute Post post, Principal principal)
	{
		System.out.println("The username of author is "+principal.getName());
		
		User author = userRepository.findByUsername(principal.getName());
		
		post.setAuthor(author);
		post.setTime(new Date());
		
		postRepository.save(post);
		
		return "redirect:/";
	}
	
	
	@GetMapping("/signup")
	public String signupGet(Model model)
	{
		model.addAttribute("UserForm", new UserForm());
		return "signup";
	}
	
	
	
	@PostMapping("/signup")
	public String signupPost(@ModelAttribute UserForm form, Model model)
	{	
		if (userRepository.checkIfUsernameExists(form.getUsername()) != 0)
		{
			model.addAttribute("usernameAlreadyExists", true);
			return "signup";
		}
		userRepository.save(new User(form));
		return "redirect:/login";
	}
}