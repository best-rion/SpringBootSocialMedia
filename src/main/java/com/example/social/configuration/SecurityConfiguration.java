package com.example.social.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http
			.csrf((csrf)->
				csrf.disable()
			)
			.authorizeHttpRequests((auth)->
				auth
					.requestMatchers("/signup", "/css/**", "/js/**").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin((login)->
				login
					.loginPage("/login") // My custom login route
					.defaultSuccessUrl("/", true)
					.permitAll()
			)
			.logout((logout)->
				logout
				.permitAll()
			);
		return http.build();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}