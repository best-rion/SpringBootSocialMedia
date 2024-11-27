package com.example.social.Game.ws;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.social.Game.dto.ActiveUser;
import com.example.social.Game.dto.Message;
import com.example.social.Game.dto.Move;
import com.example.social.Game.util.UsersList;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;


@Controller
public class PlayController
{
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	@MessageMapping(value="/enterDashboard")
	@SendTo("/topic/updateUsers")
	public Set<ActiveUser> dashboardActive(Principal principal)
	{
		UsersList.activeUsers.add(principal.getName());
		Set<String> usersName = UsersList.activeUsers;
		Set<User> users = userRepository.findAllInSet(usersName);
		
		Set<ActiveUser> activeUsers = new HashSet<>();
		
		for (User user: users)
		{
			activeUsers.add( new ActiveUser(user));
		}
		
		return activeUsers;
	}
	
	
	@MessageMapping(value="/leaveDashboard")
	@SendTo("/topic/updateUsers")
	public Set<ActiveUser> dashboardLeave(Principal principal)
	{
		UsersList.activeUsers.remove(principal.getName());
		Set<String> usersName = UsersList.activeUsers;
		Set<User> users = userRepository.findAllInSet(usersName);
		Set<ActiveUser> activeUsers = new HashSet<>();
		for (User user: users)
		{
			activeUsers.add( new ActiveUser(user));
		}
		return activeUsers;
	}

	
	
	@MessageMapping(value="/makeMove")
	public void makeMove(Move move, Principal principal)
	{
		messagingTemplate.convertAndSendToUser( principal.getName(), "/queue/move", move);
			
		messagingTemplate.convertAndSendToUser( UsersList.opponents.get(principal.getName()) ,"/queue/move", move);
	}
	
	
	@MessageMapping(value="/enterPlay")
	public void inHome(Principal principal)
	{
		String opponentUsername = UsersList.opponents.get(principal.getName());

		if (principal.getName().equals(UsersList.opponents.get(opponentUsername)))
		{
			messagingTemplate.convertAndSendToUser( opponentUsername,"/queue/opponentMessage", new Message("Let's go") );
			messagingTemplate.convertAndSendToUser( principal.getName(),"/queue/opponentMessage", new Message("Ready") );
		}
		else
		{
			User user = userRepository.findByUsername(principal.getName());
			messagingTemplate.convertAndSendToUser( opponentUsername  ,"/queue/challenge", new ActiveUser( user ) );
		}
		
		
		for (String user: UsersList.opponents.keySet())
		{
			String hisOpponent = UsersList.opponents.get(user);
			if (hisOpponent.equals(principal.getName()) && (!user.equals(opponentUsername)))
			{
				messagingTemplate.convertAndSendToUser( user, "/queue/opponentMessage",  new Message("Rejected") );	
			}
		}
	}
	
	

	
	
	@MessageMapping(value="/leavePlay")
	public void outtaHome(Principal principal)
	{
		String opponentUsername = UsersList.opponents.get(principal.getName());
		
		if ( principal.getName().equals(UsersList.opponents.get(opponentUsername)) )
		{
			messagingTemplate.convertAndSendToUser( opponentUsername, "/queue/opponentMessage", new Message("Quitted"));
		}
		else {

			messagingTemplate.convertAndSendToUser( opponentUsername,  "/queue/unchallenge", principal.getName() );
		}
		
		UsersList.opponents.remove(principal.getName());
	}
	

	
	@MessageMapping(value="/letsPlayAgain")
	public void playAgain(Principal principal)
	{
		String opponentUsername = UsersList.opponents.get(principal.getName());
		
		messagingTemplate.convertAndSendToUser( opponentUsername, "/queue/opponentMessage", new Message("Play Again"));
	}
}