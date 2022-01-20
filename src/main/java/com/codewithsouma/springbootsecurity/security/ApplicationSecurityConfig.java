package com.codewithsouma.springbootsecurity.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.codewithsouma.springbootsecurity.security.ApplicationUserRole.ADMIN;
import static com.codewithsouma.springbootsecurity.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails soumaUser = User.builder()
                .username("Souma")
                .password(passwordEncoder.encode("Soumadip@1"))
                .roles(STUDENT.name()) // ROLE_STUDENT
                .build();

        UserDetails soumikUser = User.builder()
                .username("Soumik")
                .password(passwordEncoder.encode("Soumik"))
                .roles(ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(soumaUser,soumikUser);
    }
}
