package com.example.social.Game.ws;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.social.Game.dto.Message;
import com.example.social.Game.dto.Move;
import com.example.social.Game.util.UsersList;


@Controller
public class PlayController
{
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping(value="/enterDashboard")
	@SendTo("/topic/updateUsers")
	public  Set<String> dashboardActive(Principal principal)
	{
		UsersList.activeUsers.add(principal.getName());
		Set<String> activeUsers = UsersList.activeUsers;
		return activeUsers;
	}
	
	
	@MessageMapping(value="/leaveDashboard")
	@SendTo("/topic/updateUsers")
	public Set<String> dashboardLeave(Principal principal)
	{
		UsersList.activeUsers.remove(principal.getName());
		Set<String> activeUsers = UsersList.activeUsers;
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
			messagingTemplate.convertAndSendToUser( opponentUsername  ,"/queue/challenge", principal.getName() );
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
			System.out.println("sjfsjdfksjdflk-----------------------------------------");
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