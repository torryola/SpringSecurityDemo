package net.torrydev.stockmanagementsystem.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserDetailsDao appUserDetailsDao;

    @Autowired
    public AppUserDetailsService(@Qualifier(AppUserDetailsDaoImp.DEMO_DAO_IMPL) AppUserDetailsDao appUserDetailsDao) {
        this.appUserDetailsDao = appUserDetailsDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserDetailsDao.findAppUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Unknown user with %s", username)));
    }
}
