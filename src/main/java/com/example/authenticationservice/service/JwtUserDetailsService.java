package com.example.authenticationservice.service;

import com.example.authenticationservice.entities.Complaint;
import com.example.authenticationservice.entities.User;
import com.example.authenticationservice.entities.UserInformation;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface JwtUserDetailsService {

    public UserDetails loadUserByUsername(String email);

    public User adduser(UserInformation userinformation, HttpServletRequest httpServletRequest) throws MessagingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    public User updateuser(Complaint complaint);

    public void deleteuser(Long id);

    public void deleteall(List<Long> ids);

    public List<User> getall();

    public User getone(Long id);

    public User findByEmail(String email);

}
