package com.insidelinestudios.athena.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        //DEV ONLY
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();

//            http
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests().regexMatchers("/health").permitAll()
//                    .antMatchers("/prometheus").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                    .authorizeRequests()
//                    .anyRequest()
//                    .permitAll();
//            http.csrf().disable();

        //OR

//        http.authorizeRequests()
//                .antMatchers("/resources/**", "/signup", "/signup/form", "/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login/form").permitAll().loginProcessingUrl("/login").permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/login/form?logout").permitAll()
//                .and()
//                .csrf().disable();
    }

}