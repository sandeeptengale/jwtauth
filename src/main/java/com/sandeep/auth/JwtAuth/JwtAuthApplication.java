package com.sandeep.auth.JwtAuth;

import com.sandeep.auth.JwtAuth.model.Role;
import com.sandeep.auth.JwtAuth.model.User;
import com.sandeep.auth.JwtAuth.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class JwtAuthApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setUserName("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        admin.setRoles(Collections.singletonList(Role.ROLE_ADMIN));

        userService.signUp(admin);

        User client = new User();
        client.setUserName("client");
        client.setPassword("client");
        client.setEmail("client@email.com");
        client.setRoles(Collections.singletonList(Role.ROLE_CLIENT));

        userService.signUp(client);
    }
}
