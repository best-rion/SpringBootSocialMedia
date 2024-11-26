package com.example.social.Game.controller;

import java.security.Principal;

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
	@GetMapping(value = "/challenge/{opponent}")
	public String challenge(Model model, Principal principal, @PathVariable String opponent)
	{	
		UsersList.opponents.put(principal.getName(), opponent);
		return "redirect:/game/play";
	}
	
	
	@GetMapping(value="/play")
	public String home(Model model, Principal principal)
	{				
			model.addAttribute( "principal", principal.getName() );
			model.addAttribute( "opponent", UsersList.opponents.get(principal.getName()) );
			return "game/play";
	}
}