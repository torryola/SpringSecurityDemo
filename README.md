# SpringSecurityDemo

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