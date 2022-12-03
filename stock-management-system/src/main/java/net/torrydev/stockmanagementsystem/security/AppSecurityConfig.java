package net.torrydev.stockmanagementsystem.security;

import net.torrydev.stockmanagementsystem.model.AppUserPermission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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

import static net.torrydev.stockmanagementsystem.model.AppUserPermission.ADMIN_WRITE;
import static net.torrydev.stockmanagementsystem.model.AppUserPermission.DEVELOPER_WRITE;
import static net.torrydev.stockmanagementsystem.model.AppUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC_ALLOW_URL_PATTERN = {"/", "index", "/css/*", "/js/*"};
    private static final String[] SECURE_URL_PATTERN = {"/api/v1/secured/**"};
    private static final String[] OPEN_URL_PATTERN = {"/api/v1/open/**"};
    public static final String SECURE_DEV_URL_PATTERN = "/api/v1/secure/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Prevent Spring-Security from Protecting the endpoint by default
                .authorizeRequests()
                .antMatchers(PUBLIC_ALLOW_URL_PATTERN).permitAll() // Whitelist any url with open and Home i.e. / or index.html
                //.antMatchers(SECURE_URL_PATTERN).hasRole(ADMIN.name()) // Admin Only Resource
               // .antMatchers(HttpMethod.GET, SECURE_DEV_URL_PATTERN).hasAnyRole(ADMIN.name(), DEVELOPER.name()) // Admin and Developer Only Resource
               // .antMatchers(HttpMethod.POST, SECURE_DEV_URL_PATTERN).hasAuthority(ADMIN_WRITE.getPermission()) // Only Admin with write permission can access post
               // .antMatchers(OPEN_URL_PATTERN).hasAnyRole(ADMIN.name(), USER.name(), DEVELOPER.name()) // Admin and User resource
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
                .username("admin")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                //.roles(ADMIN.name())
                .authorities(ADMIN.getSimpleGrantedAuthorities())
                .build();
        UserDetails nonAdminUser = User.builder()
                .username("User")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                //.roles(USER.name())
                .authorities(USER.getSimpleGrantedAuthorities())
                .build();
        UserDetails dev = User.builder()
                .username("Dev")
                .password(passwordEncoder().encode("Password")) // You must encode the password
                //.roles(DEVELOPER.name())
                .authorities(DEVELOPER.getSimpleGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(adminUser, nonAdminUser, dev);
    }

    // Password Encryption is expected by default in Spring Security - Not creating will result in something like "There is no PasswordEncoder mapped for "
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}