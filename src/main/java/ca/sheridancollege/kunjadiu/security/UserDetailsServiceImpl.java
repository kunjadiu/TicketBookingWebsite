package ca.sheridancollege.kunjadiu.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.kunjadiu.Repository.SecurityRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	public SecurityRepository secRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
					throws UsernameNotFoundException {
		// Find the user based on their user name
		ca.sheridancollege.kunjadiu.Beans.User user 
						=secRepo.findUserFindAccount(username);
		// if the user  doesn't exist then throw and exception
		
		if(user==null)
		{
			System.out.println("User Not found");
			throw new UsernameNotFoundException("Username Not Found");
			
		}
		// GET A LIST 	of roles for that user
		
		List <String >roles =secRepo.getRolesById(user.getUserId());
		
		// Change the list of roles a list of Granted Authorities
		
		List <GrantedAuthority> grantList=new ArrayList <GrantedAuthority>();
		
		for(String role:roles)
		{
			grantList.add(new SimpleGrantedAuthority(role));
		}
		// create a spring Security USER
		User springUser =new User(user.getUserName(),user.getEncryptedPassword(),grantList);
		// RETURN the spring security user as UserDetails
		
		return (UserDetails)springUser;
	}

}
