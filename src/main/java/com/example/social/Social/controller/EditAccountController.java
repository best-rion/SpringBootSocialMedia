package com.example.social.Social.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.social.Social.dto.EditAccountForm;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;

@Controller
public class EditAccountController
{
	@Autowired
	UserRepository userRepository;

	private String imagesDir = System.getProperty("user.dir") + "/src/main/upload/static/images";
	
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
			Path path = Paths.get(imagesDir, Long.toString( current_user.getId() ) + "." + suffix[1]);
			Files.write( path, file.getBytes());
			current_user.setImage_suffix( suffix[1] );
		}
		
		current_user.setName( form.getName() );
		current_user.setDescription( form.getDescription() );
		userRepository.save( current_user );
		
		
		return "redirect:/profile/" + principal.getName();
	}
}