package com.example.social.Social.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.social.Social.dto.SignupForm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.persistence.JoinColumn;

@Entity
public class User implements UserDetails
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@Column(unique=true)
	private String username;
	private String password;
	private String description;
	private String image_suffix;
	@Transient
	private String src;

	@OneToMany(
	        mappedBy = "author",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	public Set<Post> posts;
	
	@ManyToMany
	public Set<User> sentRequests;
	@ManyToMany
	public Set<User> receivedRequests;
	@ManyToMany
	public Set<User> friends;
	@ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="user_liked_posts", 
    joinColumns={@JoinColumn(name="user_id")}, 
    inverseJoinColumns={@JoinColumn(name="post_id")})
	private Set<Post> likedPosts;
	// FetchType.LAZY by default
	
	public User() {}
	
	public User(SignupForm form)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.name = form.getName();
		this.username = form.getUsername();
		this.password = encoder.encode(form.getPassword());
		this.description = "Just Signed Up";
		this.likedPosts = new HashSet<>();
	}
	
	
	public boolean likedThisPost( Post post )
	{
		return likedPosts.contains(post);
	}
	


	public String getSrc()
	{
		if (image_suffix != null)
		{
			return String.format("/upload/static/images/%d.%s", id, image_suffix);
		}
		else
		{
			return  "/images/default.jpg";
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode(password);
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		List<GrantedAuthority> auth = new ArrayList<>();
	    auth.add(new SimpleGrantedAuthority("USER"));
		
		return auth;
	}

	public String getImage_suffix() {
		return image_suffix;
	}

	public void setImage_suffix(String image_suffix) {
		this.image_suffix = image_suffix;
	}
	
	public Set<Post> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(Set<Post> likedPosts) {
		this.likedPosts = likedPosts;
	}
}