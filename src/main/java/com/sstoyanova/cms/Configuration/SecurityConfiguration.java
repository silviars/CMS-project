package com.sstoyanova.cms.Configuration;

import com.sstoyanova.cms.Service.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/view-users").hasAuthority("read_user")
                .antMatchers("/create-user").hasAuthority("create_user")
                .antMatchers("/delete-user").hasAuthority("delete_user")
                .antMatchers("/update-user").hasAuthority("update_user")
                .antMatchers("/view-contacts").hasAuthority("read_contact")
                .antMatchers("/create-contact").hasAuthority("create_contact")
                .antMatchers("/delete-contact").hasAuthority("delete_contact")
                .antMatchers("/update-contact").hasAuthority("update_contact")
                .antMatchers("/update-permissions").hasAnyRole("SUPER-ADMIN")//or hasAuthority("update_user")?
                .antMatchers("/search-contact").hasAuthority("read_contact")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}