// References:
// https://www.springboottutorial.com/spring-boot-react-full-stack-with-spring-security-basic-and-jwt-authentication
package com.vsta.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security Configuration class
 * to authenticate API requests.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Authenticate headers of API requests with the exception
     * of /resetPasswordRequest and /resetPassword.
     * @param http HttpSecurity object.
     * @throws Exception Exception if any is thrown.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/resetPasswordRequest", "/resetPassword").permitAll()
                    .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

}
