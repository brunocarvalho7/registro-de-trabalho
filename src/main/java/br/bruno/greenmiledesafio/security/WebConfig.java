package br.bruno.greenmiledesafio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

        JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler =
                new JWTAuthenticationSuccessHandler(jwtAuthenticationService);

        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager(),
                                            jwtAuthenticationService);
        http.authorizeRequests()
                .antMatchers("/h2-console/**").hasRole("ADMIN")//allow h2 console access to admins only
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()//all other urls can be access by any authenticated role
                .and().formLogin().successHandler(jwtAuthenticationSuccessHandler).defaultSuccessUrl("/")//enable form login instead of basic login
                .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
                .and().headers().frameOptions().sameOrigin()//allow use of frame to same origin urls
                .and().addFilterBefore(jwtAuthorizationFilter, JWTAuthorizationFilter.class);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        //TODO: EXCEPTION QUANDO USUÁRIO É INVÁLIDO
        builder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder())
        ;
    }
}
