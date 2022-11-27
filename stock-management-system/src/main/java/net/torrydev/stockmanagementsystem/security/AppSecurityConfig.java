package net.torrydev.stockmanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private String[] allowUrl = new String[]{"/", "index", "/api/v1/open/*"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(allowUrl).permitAll() // Whitelist any url with open and Home i.e. / or index.html
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // For Custom userName and Password - Using InMemoryUserDetails

    @Override @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        // Build Custom Userdetails
        UserDetails adminUser = User.builder()
                .username("secureUser")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                .build();
        UserDetails nonAdminUser = User.builder()
                .username("User")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                .build();
        return new InMemoryUserDetailsManager(adminUser, nonAdminUser);
    }

    // Password Encryption is expected by default in Spring Security - Not creating will result in something like "There is no PasswordEncoder mapped for "
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
