package com.sandeep.auth.JwtAuth.service;

import com.sandeep.auth.JwtAuth.model.User;
import com.sandeep.auth.JwtAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) throw new UsernameNotFoundException("User " + userName + " not found!");
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(user.getPassword()).authorities(user.getRoles()).accountExpired(false)
                .accountLocked(false).credentialsExpired(false).disabled(false).build();


    }
}
