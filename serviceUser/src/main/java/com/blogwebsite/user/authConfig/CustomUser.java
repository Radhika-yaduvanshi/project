package com.blogwebsite.user.authConfig;

import com.blogwebsite.user.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CustomUser implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomUser(UserEntity user) {
		super();
		this.user = user;
	}
	
	@Autowired
	private UserEntity user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(user.getRole());
		System.err.println("Role is : "+user.getRole());
		return Arrays.asList(simpleGrantedAuthority);

//		return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
//		return  null;

	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	public String getEmail(){return user.getEmail();}



}
