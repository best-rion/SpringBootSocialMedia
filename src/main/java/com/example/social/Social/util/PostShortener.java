package com.example.social.Social.util;


public class PostShortener
{
	public static String shortContent(String content)
	{
		String subContent = content;
		
		if (content.length() > 200)
		{
			subContent = content.substring(0, 199);
		}
		
		return subContent;
	}
	
	public static String shortLine(String content)
	{
		String subContent = content;

		String[] lines = content.split("\r\n|\r|\n");
		
		if ( lines.length > 5)
		{
			subContent = "";
			
			for (int i=0; i<5; i++)
			{
				subContent += lines[i]+"\n";
			}
		}
		
		return subContent;
	}
}