package com.example.API.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.API.entity.NguoiDung;

public class CustomUserDetails implements UserDetails{
	NguoiDung user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(user.getQuyen().getTenquyen()));
	}

	@Override
	public String getPassword() {
		return user.getMatkhau();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
	
	@Override
	public boolean isEnabled() {
		return user.isTrangthai();
	}
	
	public NguoiDung getUser() {
		return user;
	}

	public void setUser(NguoiDung user) {
		this.user = user;
	}

	public CustomUserDetails(NguoiDung user) {
		super();
		this.user = user;
	}

	public CustomUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
}
