package net.torrydev.stockmanagementsystem.security;

import net.torrydev.stockmanagementsystem.authentication.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfigWithoutWebSecurityConfigAdapter {
    private static final String[] PUBLIC_ALLOW_URL_PATTERN = {"/", "index","/login", "/css/*", "/js/*"};
    private static final String[] SECURE_URL_PATTERN = {"/api/v1/secured/**"};
    private static final String[] OPEN_URL_PATTERN = {"/api/v1/open/**"};
    public static final String SECURE_DEV_URL_PATTERN = "/api/v1/secure/**";

    private final AppUserDetailsService appUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfigWithoutWebSecurityConfigAdapter(AppUserDetailsService appUserDetailsService, PasswordEncoder passwordEncoder) {
        this.appUserDetailsService = appUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authReq -> authReq.antMatchers(PUBLIC_ALLOW_URL_PATTERN).permitAll() // Whitelist any url with open and Home i.e. / or index.html
                .anyRequest()
                .authenticated())
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
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserDetailsService);
        return provider;
    }
}