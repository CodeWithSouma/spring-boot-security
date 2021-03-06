package com.codewithsouma.springbootsecurity.security;

import com.codewithsouma.springbootsecurity.auth.ApplicationUserServices;
import com.codewithsouma.springbootsecurity.jwt.JwtConfig;
import com.codewithsouma.springbootsecurity.jwt.JwtTokenVerifier;
import com.codewithsouma.springbootsecurity.jwt.JwtUsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.codewithsouma.springbootsecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserServices applicationUserServices;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig,secretKey),JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())

                //we already replaced all of this with PreAuthorize annotation
                /*.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())*/

                .anyRequest()
                .authenticated();
//                .and()
//                //.httpBasic();
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .defaultSuccessUrl("/courses",true)
//                    .passwordParameter("password")
//                    .usernameParameter("username")
//                .and()
//                .rememberMe()
//                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                    .key("somethingverysecured")
//                    .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID","remember-me")
//                    .logoutSuccessUrl("/login");
    }

   /* @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails soumaUser = User.builder()
                .username("Souma")
                .password(passwordEncoder.encode("Soumadip@1"))
//                .roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthority())
                .build();

        UserDetails soumikUser = User.builder()
                .username("Soumik")
                .password(passwordEncoder.encode("Soumik"))
//                .roles(ADMIN.name())// ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        UserDetails tomUser = User.builder()
                .username("Tom")
                .password(passwordEncoder.encode("Tom123"))
//                .roles(ADMINTRAINEE.name())// ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(soumaUser,soumikUser, tomUser);
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserServices);
        return provider;
    }
}
