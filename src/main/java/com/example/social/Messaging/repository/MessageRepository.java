package com.example.social.Messaging.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.social.Messaging.model.Message;


public interface MessageRepository extends CrudRepository<Message, Integer> 
{
	@Query(value = "SELECT * FROM message WHERE (sender = :person1 AND receiver = :person2)"
			+ " OR (sender = :person2 AND receiver = :person1)", nativeQuery = true)
	List<Message> findByPeople(String person1, String person2);
	
	@Query(value = "SELECT COUNT(*) FROM message WHERE sender = :sender AND receiver = :me AND seen=0", nativeQuery = true)
	int numOfUnseenMessageBySender(String me, String sender);
	
	@Query(value = "SELECT * FROM message WHERE sender = :sender AND receiver = :me AND seen=0", nativeQuery = true)
	List<Message> unseenMessageBySender(String me, String sender);

	@Query(value = "SELECT receiver FROM message WHERE sender = :me UNION SELECT sender FROM message WHERE receiver = :me", nativeQuery = true)
	Set<String> findPeopleIHaveTalkedTo(String me);
	@Query(value = "SELECT * FROM message WHERE (receiver = :me AND sender = :sender)" +
			" OR (sender = :me AND receiver = :sender) ORDER BY time DESC LIMIT 1\n", nativeQuery = true)
	Message lastMessage(String me, String sender);
	
	Message findById(int id);
}