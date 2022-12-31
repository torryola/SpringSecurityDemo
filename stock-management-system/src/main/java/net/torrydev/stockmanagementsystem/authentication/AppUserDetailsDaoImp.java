package net.torrydev.stockmanagementsystem.authentication;

import net.torrydev.stockmanagementsystem.model.AppUserDetails;
import net.torrydev.stockmanagementsystem.model.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(AppUserDetailsDaoImp.DEMO_DAO_IMPL) // This is a Qualifier for this repo impl - Makes it easier to switch impl of the interface
public class AppUserDetailsDaoImp implements AppUserDetailsDao{

    public static final String DEMO_DAO_IMPL = "DemoDb";
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserDetailsDaoImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AppUserDetails> findAppUserByUserName(String userName) {
        return getUsers().stream().filter(appUserDetails -> userName.equals(appUserDetails.getUsername())).findFirst();
    }

    // Hard-Coded User details for brevity. This can be replaced with actual JPA repository method or
    // any other Db implementation
    private List<AppUserDetails> getUsers(){
        return List.of(new AppUserDetails(AppUserRole.ADMIN.getSimpleGrantedAuthorities(), passwordEncoder.encode("Password"), "admin",Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE),
                new AppUserDetails(AppUserRole.DEVELOPER.getSimpleGrantedAuthorities(), passwordEncoder.encode("Password"), "dev",Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE),
                new AppUserDetails(AppUserRole.USER.getSimpleGrantedAuthorities(), passwordEncoder.encode("Password"), "user",Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
    }
}
