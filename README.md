# SpringSecurityDemo
========================================================
### Basic Authentication

Implementing basic auth in SpringBoot:
- Add sprint-boot-start-security dependency
- Create a Config class to have all security configuration i.e. create a class and annotate it wit (@Configuration and @EnableWebSecurity)
- Extend WebSecurityConfigurerAdapter
- Override configure(HttpSecurity http)

#### Downside
- You can't logout - No Logout form like spring-boot builtin logout form
- Every request requires Base64 encoded (UserName and Password)

Example:
```java
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
```
========================================================
### Role and Permission Based Using Annotations 

<font color="red">**Note** ==>></font> Authority refers to Permission

#### Pre-requisite 
- Add Spring security dependencies if not already added 
- Create Security Config class 
- Add annotation 
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class MySecurityConfig{
    // Todo: 
}
```
With @EnableGlobalMethodSecurity(prePostEnabled = true) added we can use E.g.
```java
@RestController
@RequestMapping(path = "/api/v1/")
public class DeveloperController {

    @GetMapping(path = "secure/dev")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEVELOPER')")
    public String developerEndpoint(){
        return "Admin and Developer only Resource";
    }

    @PostMapping(path = "secure/dev") // Admin Only
    @PreAuthorize("hasAuthority('admin:write')")
//    @PreAuthorize("hasRole('ROLE_ADMIN'))")
    public String createNewResource(@RequestBody String newRes){
        return String.format("New %s created", newRes);
    }
}
```
### Form-Based Authentication 
Instead of using spring security built-in **Login Form**, we can create and use custom Login UI.
#### Pre-requisite 
- Add ThymLeaf dependency to the pom.xml
- Create a dir **templates** under resource dir
- Create your custom html file (Hint: For brevity copy the spring login html file from browser :wink:)
- Create a controller to load the view 
- Permit access to the new login ui path unauthorised like below
```java
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC_ALLOW_URL_PATTERN = {"/", "index", "/login", "/css/*", "/js/*"};
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
                .defaultSuccessUrl("/products/catalog"); // On Successful login, user will be redirected to that page instead of default "/" or index
    }
}
```
View Controller Example 
```java
@Controller // This is the guy not @RestController for view
@RequestMapping("/")
public class TemplateController {

    // Login View
    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }
}
```
