package com.blogwebsite.user.authConfig;

//import com.auth.config.CustomUser;
import com.blogwebsite.user.authConfig.CustomUser;
import com.blogwebsite.user.domain.UserEntity;
import com.blogwebsite.user.proxy.UserProxy;
import com.blogwebsite.user.repository.UserRepo;
import com.blogwebsite.user.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component(value = "bean from custom user service")
public class CustomUserService implements UserDetailsService
{

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private Helper helper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		UserEntity byUserName = userRepo.findByUserName(username);
//		UserEntity userEntity = userRepo.findByEmail(username);
//		System.err.println("email of usr in loaduserbyusername function in userdetailservices : "+userEntity.getEmail());
//		if(userEntity==null)
//		{
//			throw new UsernameNotFoundException("user not found with email "+username);
//		}
////		return new CustomUser(byUserName);
//		return new CustomUser(userEntity);

		UserEntity userbyemail = userRepo.findByEmail(email);
		UserEntity user=helper.convert(userbyemail,UserEntity.class);

		if(user==null){
			throw  new UsernameNotFoundException("User not found with email : " +email);
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
	
	
}
