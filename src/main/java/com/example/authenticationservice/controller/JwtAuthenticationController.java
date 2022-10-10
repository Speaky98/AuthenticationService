package com.example.authenticationservice.controller;

import com.example.authenticationservice.config.JwtTokenUtil;
import com.example.authenticationservice.entities.*;
import com.example.authenticationservice.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/auth/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		User userinfo=this.userDetailsService.findByEmail(userDetails.getUsername());
		Object[] object = new Object[2];
		object[0]=new JwtResponse(token);
		object[1]=userinfo;
		return ResponseEntity.ok(object);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<?> adduser(@RequestBody UserInformation userinformation, HttpServletRequest httpServletRequest) throws MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		return ResponseEntity.ok(userDetailsService.adduser(userinformation,httpServletRequest));
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateuser(@RequestBody Complaint complaint) {
		return ResponseEntity.ok(userDetailsService.updateuser(complaint));
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public void deleteuser(@PathVariable Long id) {
		this.userDetailsService.deleteuser(id);
	}

	@RequestMapping(value = "/user/deleteall", method = RequestMethod.POST)
	public void deleteall(@RequestBody List<Long> ids){
		this.userDetailsService.deleteall(ids);
	}

	@RequestMapping(value = "/user/findbyemail/{email}", method = RequestMethod.GET)
	public User findByEmail(@PathVariable String email){
		return this.userDetailsService.findByEmail(email);
	}

	@RequestMapping(value = "/user/findall", method = RequestMethod.GET)
	public List<User> getall(){
		return this.userDetailsService.getall();
	}

	@RequestMapping(value = "/user/findone/{id}", method = RequestMethod.GET)
	public User getone(@PathVariable Long id){
		return this.userDetailsService.getone(id);
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
