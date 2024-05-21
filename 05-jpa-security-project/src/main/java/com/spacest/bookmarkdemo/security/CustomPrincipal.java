package com.spacest.bookmarkdemo.security;

import com.spacest.bookmarkdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.security.Principal;

public class CustomPrincipal {

    private int id;

    private Principal principal;

    private final UserDetailsService userDetailsService;

    public CustomPrincipal(Principal principal, UserService userService, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.principal = principal;
        this.id = userService.findByEmail(principal.getName()).getId();
    }

    public String getName() {
        return principal.getName();
    }

    public boolean implies(Subject subject) {
        return principal.implies(subject);
    }

    public boolean isAdmin(){
        UserDetails details = userDetailsService.loadUserByUsername(principal.getName());
        return (details != null && details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    public int getId() {
        return id;
    }
}
