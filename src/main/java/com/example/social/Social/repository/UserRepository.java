package com.example.social.Social.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.social.Social.model.User;

public interface UserRepository extends CrudRepository<User,Integer>
{
	public List<User> findAll();
	
	@Query(value="SELECT COUNT(1) FROM user WHERE username = :username", nativeQuery=true)
	public byte checkIfUsernameExists(String username);

	public User findById(long id);
	
	public User findByUsername(String username);
	
	@Query(value = "SELECT * FROM user WHERE username <> :username", nativeQuery = true)
	List<User> findAllExcept(String username);
	
	@Query(value = "SELECT * FROM user WHERE username IN :set", nativeQuery = true)
	Set<User> findAllInSet(Set<String> set);
}