package com.example.social;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer>
{
	public List<User> findAll();
	
	@Query(value="SELECT COUNT(1) FROM user WHERE username = :username", nativeQuery=true)
	public byte checkIfUsernameExists(String username);

	public User findById(long id);
	
	public User findByUsername(String username);
}