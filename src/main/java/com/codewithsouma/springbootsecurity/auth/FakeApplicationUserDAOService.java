package com.codewithsouma.springbootsecurity.auth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.codewithsouma.springbootsecurity.security.ApplicationUserRole.*;

@AllArgsConstructor
@Repository("fake")
public class FakeApplicationUserDAOService implements ApplicationUserDAO{
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> Objects.equals(applicationUser.getUsername(),username))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser("Souma",passwordEncoder.encode("Soumadip@1"), STUDENT.getGrantedAuthority(),true,true,true,true),
                new ApplicationUser("Soumik",passwordEncoder.encode("Soumik"), ADMIN.getGrantedAuthority(),true,true,true,true),
                new ApplicationUser("Tom",passwordEncoder.encode("Tom123"), ADMINTRAINEE.getGrantedAuthority(),true,true,true,true)
        );

        return applicationUsers;
    }
}
