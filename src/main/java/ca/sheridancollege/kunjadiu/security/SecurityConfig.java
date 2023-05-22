package ca.sheridancollege.kunjadiu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	LoginAccessDeniedHandler accessDeniedhandler;
	//Authorization
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		// remove this code Before production
		http.csrf().disable();
		http.headers().frameOptions().disable();		
//		
//		
//		
		http
			.authorizeHttpRequests((authz) -> authz
			// Identity the Mapping and the roles that can access it
			// .antMatchers(Http method, URL).hasRole(specific Role)
			//.antMatchers(Http method, URL).hasAnyRole(all Role)
				.antMatchers(HttpMethod.GET,"/add").hasRole("VENDER")
				.antMatchers(HttpMethod.POST,"/processAdd").hasRole("VENDER")	
				.antMatchers(HttpMethod.GET,"/edit/{id}").hasRole("VENDER")
				.antMatchers(HttpMethod.GET,"/delete/{id}").hasRole("VENDER")
				.antMatchers(HttpMethod.POST,"/editTicket").hasRole("VENDER")
				.antMatchers(HttpMethod.GET,"/view").hasAnyRole("VENDER","GUEST")
			//pages that we don't require a login 
			//.antMatchers(HTTP method,URL).permitAll();
				.antMatchers(HttpMethod.GET,"/").permitAll()
//				.antMatchers(HttpMethod.GET,"/view").permitAll()
				.antMatchers(HttpMethod.GET,"/register").permitAll()
				.antMatchers(HttpMethod.POST,"/register").permitAll()
				.antMatchers("/css/**","/images/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin().loginPage("/Login")
			.permitAll()
			.and()
				.logout()
				.invalidateHttpSession(false)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/?logout")
				
				.and().exceptionHandling().accessDeniedHandler(accessDeniedhandler);
//				
		return http.build();

	}
	//Authentication
			@Autowired
			private UserDetailsServiceImpl userDetailsService;
			
			@Autowired
			private BCryptPasswordEncoder encoder; 
			
			@Bean
			public AuthenticationManager authManager(HttpSecurity http)throws Exception {
					
				return http.getSharedObject(AuthenticationManagerBuilder.class)
					.userDetailsService(userDetailsService)
					.passwordEncoder(encoder)
					.and()
					.build();
			}
}
