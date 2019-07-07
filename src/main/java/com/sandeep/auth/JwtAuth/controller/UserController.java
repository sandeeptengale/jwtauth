package com.sandeep.auth.JwtAuth.controller;

import com.sandeep.auth.JwtAuth.dto.UserResponseDto;
import com.sandeep.auth.JwtAuth.model.User;
import com.sandeep.auth.JwtAuth.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signin")
    public String login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        return userService.signIn(userName, password);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        return userService.signUp(user);
    }

    @DeleteMapping(value = "/{username}")
    public String delete(@PathVariable("username") String userName) {
        userService.delete(userName);
        return userName;
    }

    @GetMapping(value = "/me")
    public UserResponseDto whoAmI(HttpServletRequest httpServletRequest) {
        return modelMapper.map(userService.whomAmI(httpServletRequest), UserResponseDto.class);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest httpServletRequest) {
        return userService.refresh(httpServletRequest.getRemoteUser());
    }
}
