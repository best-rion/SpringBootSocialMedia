package com.example.social.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social.dto.ViewPost;
import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.PostRepository;
import com.example.social.util.PostShortener;

@Service
public class PostService
{
	@Autowired
	PostRepository postRepository;
	
	public List<ViewPost> postsForHome( User current_user )
	{
		List<Post> posts = new ArrayList<>();
		
		Set<User> friends = current_user.friends;
		for (User friend: friends)
		{
			posts.addAll(friend.posts);
		}
		
		List<ViewPost> viewPosts = new ArrayList<>();
		
		for (Post post: posts)
		{
			ViewPost viewPost = new ViewPost( post );
			
			if(current_user.authorsPost( post ))
			{
				viewPost.setLiked(true);
			}
			viewPosts.add( viewPost );
			
			
			// Make contents and lines shorter
			
			String subContent = PostShortener.shortContent( post.getContent() );
			
			subContent = PostShortener.shortLine( subContent );
			
			viewPost.setSeeMore(  !subContent.equals( post.getContent() )   );
			
			post.setContent( subContent );
			
			// Sort Comments
			
			Collections.sort(post.getComments());
		}
		
		Collections.sort(viewPosts);
		
		return viewPosts;
	}
	
	
	public List<ViewPost> postsForProfile( User current_user, User profile_owner )
	{
		List<Post> posts = postRepository.findByAuthor( profile_owner );
		
		List<ViewPost> viewPosts = new ArrayList<>();
		
		for (Post post: posts)
		{
			ViewPost viewPost = new ViewPost( post );
			
			if(current_user.authorsPost( post ))
			{
				viewPost.setLiked(true);
			}
			viewPosts.add( viewPost );
			
			
			// Make contents and lines shorter
			
			String subContent = PostShortener.shortContent( post.getContent() );
			
			subContent = PostShortener.shortLine( subContent );
			
			viewPost.setSeeMore(  !subContent.equals( post.getContent() )   );
			
			post.setContent( subContent );
			
			// Sort Comments
			
			Collections.sort(post.getComments());
		}
		
		Collections.sort(viewPosts);
		
		return viewPosts;
	}
}










