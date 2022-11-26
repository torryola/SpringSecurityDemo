package net.torrydev.stockmanagementsystem.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private String[] allowUrl = new String[]{"/","index", "/api/v1/open/*"};
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(allowUrl).permitAll() // Whitelist any url with open and Home i.e. / or index.html
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
