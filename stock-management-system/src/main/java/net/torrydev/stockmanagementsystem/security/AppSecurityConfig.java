package net.torrydev.stockmanagementsystem.security;

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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static net.torrydev.stockmanagementsystem.model.AppUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC_ALLOW_URL_PATTERN = {"/", "index","/login", "/css/*", "/js/*"};
    private static final String[] SECURE_URL_PATTERN = {"/api/v1/secured/**"};
    private static final String[] OPEN_URL_PATTERN = {"/api/v1/open/**"};
    public static final String SECURE_DEV_URL_PATTERN = "/api/v1/secure/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // So Spring doesn't check Request Header for csrf_token
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PUBLIC_ALLOW_URL_PATTERN).permitAll() // Whitelist any url with open and Home i.e. / or index.html
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic(); // Enabling Basic Authentication - Doesn't have logout
                .formLogin() // Enabling Form-Based Authentication - Has logout
                    .loginPage("/login").permitAll() // Custom Login UI
                    .defaultSuccessUrl("/products/catalog", true) // On Successful login, user will be redirected to that page instead of default "/" or index
                    .and()
                    .logout().logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name())) // POST req method should be set, If CSRF is enabled, otherwise GET.
                // We use Get because csrf is disabled for this security config
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID","remember-me")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/login");
//                .and()
//                 .rememberMe(); // Enable Remember Option in the login
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