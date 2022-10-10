package com.example.authenticationservice.service;


import com.example.authenticationservice.entities.*;
import com.example.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


import java.util.*;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService,JwtUserDetailsService {

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public User adduser(UserInformation userinformation,HttpServletRequest httpServletRequest) {
		User user=new User(userinformation.getNom(),userinformation.getPrenom(),userinformation.getTelephone(),userinformation.getDate_de_naissance(),
				userinformation.getEmail(),this.bcryptEncoder.encode(userinformation.getPassword()),userinformation.getRole());
		this.userrepository.save(user);
		return user;
	}

	@Override
	public User updateuser(Complaint complaint) {
		User user=this.userrepository.findById(complaint.getId()).get();
		user.setEmail(complaint.getUserInformation().getEmail());
		user.setPrenom(complaint.getUserInformation().getPrenom());
		user.setTelephone(complaint.getUserInformation().getTelephone());
		user.setDate_de_naissance(complaint.getUserInformation().getDate_de_naissance());
		user.setRole(complaint.getUserInformation().getRole());
		return this.userrepository.save(user);
	}

	@Override
	public void deleteuser(Long id) {
			this.userrepository.deleteById(id);
	}

	@Override
	public void deleteall(List<Long> ids) {
		this.userrepository.deleteAllById(ids);
	}

	/**
	 * function getby?
	 * Différents type de recherche
	 **/

//	@Override
//	public List<User> getby?(Long id) {
	//	List<User> userList=(List<User>) this.userrepository.findAll();
	// 	return userList.stream().filter(user -> user.?).collect(Collectors.toList());
//	}

	@Override
	public List<User> getall() {
		return (List<User>) this.userrepository.findAll();
	}

	@Override
	public User getone(Long id) {
		return this.userrepository.findById(id).get();
	}

	@Override
	public User findByEmail(String email) {
		return this.userrepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email)
		throws UsernameNotFoundException {
		User user = userrepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}
}