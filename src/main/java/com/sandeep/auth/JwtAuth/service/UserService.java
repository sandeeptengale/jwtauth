package com.sandeep.auth.JwtAuth.service;

import com.sandeep.auth.JwtAuth.exception.CustomException;
import com.sandeep.auth.JwtAuth.model.Role;
import com.sandeep.auth.JwtAuth.model.User;
import com.sandeep.auth.JwtAuth.repository.UserRepository;
import com.sandeep.auth.JwtAuth.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayDeque;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createTokens(username, userRepository.findByUserName(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signUp(User user) {
        if (!userRepository.existsByUserName(user.getUserName())) {
            user.setPassword(passwordEncoder.encode(user.getUserName()));
            userRepository.save(user);
            return jwtTokenProvider.createTokens(user.getUserName(), user.getRoles());
        } else {
            throw new CustomException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public User searchUser(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        } else {
            return user;
        }
    }

    public User whomAmI(HttpServletRequest request) {
        return userRepository.findByUserName(jwtTokenProvider.getUserName(jwtTokenProvider.resolveTokens(request)));
    }

    public String refresh(String userName) {
        return jwtTokenProvider.createTokens(userName, userRepository.findByUserName(userName).getRoles());
    }
}
