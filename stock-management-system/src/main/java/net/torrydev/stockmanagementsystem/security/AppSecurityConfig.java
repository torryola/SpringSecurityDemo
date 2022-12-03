package net.torrydev.stockmanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static net.torrydev.stockmanagementsystem.model.AppUserRole.ADMIN;
import static net.torrydev.stockmanagementsystem.model.AppUserRole.USER;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC_ALLOW_URL_PATTERN = {"/", "index", "/css/*", "/js/*"};
    private static final String[] SECURE_URL_PATTERN = {"/api/v1/secured/**"};
    private static final String[] OPEN_URL_PATTERN = {"/api/v1/open/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PUBLIC_ALLOW_URL_PATTERN).permitAll() // Whitelist any url with open and Home i.e. / or index.html
                .antMatchers(SECURE_URL_PATTERN).hasRole(ADMIN.name()) // Admin Only Resource
                .antMatchers(OPEN_URL_PATTERN).hasAnyRole(ADMIN.name(), USER.name()) // Admin and User resource
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // For Custom userName and Password - Using InMemoryUserDetails

    @Override @Bean
    public UserDetailsService userDetailsServiceBean() throws UsernameNotFoundException {
        // Build Custom Userdetails
        UserDetails adminUser = User.builder()
                .username("secureUser")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                .roles(ADMIN.name())
                .build();
        UserDetails nonAdminUser = User.builder()
                .username("User")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                .roles(USER.name())
                .build();
        return new InMemoryUserDetailsManager(adminUser, nonAdminUser);
    }

    // Password Encryption is expected by default in Spring Security - Not creating will result in something like "There is no PasswordEncoder mapped for "
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}