package br.bruno.greenmiledesafio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationService jwtAuthenticationService = new JWTAuthenticationService(customUserDetailsService);

        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/**").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
            .and() //As duas opções setadas a seguir são para permitir o acesso ao h2-console
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
            .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtAuthenticationService))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtAuthenticationService));
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
