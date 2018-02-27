package org.rachidcorp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication()
		.withUser("admin").password("1234").roles("ADMIN", "USER")
		.and()
		.withUser("user").password("1234").roles("USER");*/
		
		/*auth.jdbcAuthentication()
		.usersByUsernameQuery("")
		.authoritiesByUsernameQuery("");*/
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable() //Pour désactiver la génération du champ hidden csrf dans le formulaire d'authentification avec le secure token
		
		// Pour ne pas créer de session
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and()
			.authorizeRequests()
			.antMatchers("/login/**", "/register/**").permitAll()
		
			//User api restriction
			.antMatchers(HttpMethod.POST, "/api/users/**").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN")
		
			//Task api restriction
			.antMatchers(HttpMethod.POST, "/api/tasks/**").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/tasks/**").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/tasks/**").hasAuthority("ADMIN")
		
			.anyRequest().authenticated()
			
		.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		
	}
}
