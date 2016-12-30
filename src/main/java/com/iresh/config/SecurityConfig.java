package com.iresh.config;

import com.iresh.model.FlashMessage;
import com.iresh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.FlashMap;

/**
 * Created by iresh on 11/23/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/signup").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                //.failureHandler(loginFailureHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
        http.formLogin().failureUrl("/login?error");

    }



    public AuthenticationSuccessHandler loginSuccessHandler(){
        return ((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.sendRedirect("/"));
    }

//    public AuthenticationFailureHandler loginFailureHandler(){
//        return (httpServletRequest, httpServletResponse, e) -> {
//
//            httpServletRequest.setAttribute("param", "error");
//            httpServletResponse.sendRedirect("/login?error");
//
//        };
//    }


//    public AuthenticationFailureHandler loginFailureHandler() {
//        return (request, response, exception) -> {
//            //request.getSession().setAttribute("flash", new FlashMessage("Incorrect username and/or password. Please try again.", FlashMessage.Status.FAILURE));
//            response.sendRedirect("/login?error");
//        };
//    }


}
