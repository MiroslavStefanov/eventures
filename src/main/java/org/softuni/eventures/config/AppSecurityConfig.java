package org.softuni.eventures.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/login", "/register").anonymous()
                    .antMatchers("/css/**", "/js/**").permitAll()
//                    .antMatchers("/viruses/add, /viruses/edit, viruses/delete")
//                        .hasAnyAuthority("MODERATOR", "ADMIN")
//                    .antMatchers("/admin/users/edit/{userId}")
//                        .access("hasAuthority('ADMIN') and @appSecurityConfig.checkIfAuthorizedToEdit(principal, #userId)")
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home", true)
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/unauthorized")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                .and()
                    .rememberMe()
                    .rememberMeParameter("rememberMe")
                    .key("remember Me Encryption Key")
                    .rememberMeCookieName("rememberMeCookieName")
                    .tokenValiditySeconds(10000)
        ;
    }
}
