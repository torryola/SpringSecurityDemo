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
