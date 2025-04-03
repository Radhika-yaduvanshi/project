package com.blogwebsite.user.authConfig;

//import com.auth.config.CustomUser;
import com.blogwebsite.user.authConfig.CustomUser;
import com.blogwebsite.user.domain.UserEntity;
import com.blogwebsite.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component(value = "bean from custom user service")
public class CustomUserService implements UserDetailsService
{

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity byUserName = userRepo.findByUserName(username);
		
		if(byUserName==null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		return new CustomUser(byUserName);
	}
	
	
}
