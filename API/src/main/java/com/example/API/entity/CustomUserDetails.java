package com.example.API.entity;

import java.util.Collection;
import java.util.Collections;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class CustomUserDetails implements UserDetails {
//
//	User user;
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
//	}
//
//	@Override
//	public String getPassword() {
//		return user.getMatkhau();
//	}
//
//	@Override
//	public String getUsername() {
//		return user.getUsername();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public CustomUserDetails(User user) {
//		super();
//		this.user = user;
//	}
//
//	public CustomUserDetails() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//}
