package com.spacest.bookmarkdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class BookmarkSecurityConfig {
    //add support for JDBC authentication
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //Define query to retrieve the user information by username
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password, enabled FROM users WHERE email=?");

        //Define query to retrieve the role by username
        //if not provided table 'authorities' is used with fields 'user' and 'authority'

        return jdbcUserDetailsManager;
    }

}
