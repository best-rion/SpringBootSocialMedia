package com.example.social.Game.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.social.Game.util.UsersList;

@Controller
@RequestMapping("/game")
public class GameController
{
	@GetMapping(value = "/dashboard")
	public String dashboard(Model model, Principal principal)
	{	
		Set<String> activeUsers = new HashSet<>();
		activeUsers.addAll(UsersList.activeUsers);
		
		List<String> challengers = new ArrayList<>();
		for (String user: UsersList.opponents.keySet())
		{
			if (principal.getName().equals(UsersList.opponents.get(user)))
			{
				challengers.add(user);
			}
		}
		
		model.addAttribute("principal", principal.getName());
		model.addAttribute("activeUsers", activeUsers);
		model.addAttribute("challengers", challengers);
		return "game/dashboard";
	}
	
	
	@GetMapping(value = "/challenge/{opponent}")
	public String challenge(Model model, Principal principal, @PathVariable String opponent)
	{	
		UsersList.opponents.put(principal.getName(), opponent);
		return "redirect:/play";
	}
	
	
	@GetMapping(value="/play")
	public String home(Model model, Principal principal)
	{				
			model.addAttribute( "principal", principal.getName() );
			model.addAttribute( "opponent", UsersList.opponents.get(principal.getName()) );
			return "play";
	}
}