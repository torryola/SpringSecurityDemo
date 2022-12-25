package net.torrydev.stockmanagementsystem.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * To convert this to an Entity for e.g. JPA use
 * - Change the instance fields to non-Final
 * - Create Setter for all fields
 * - Annotate the class @Entity and other Table properties for each fields
 */
public class AppUserDetails implements UserDetails {

    public AppUserDetails(Set<? extends GrantedAuthority> grantedAuthorities,
                          String password,
                          String username,
                          boolean isAccountExpired,
                          boolean isAccountLocked,
                          boolean isCredentialExpired,
                          boolean isEnabled) {
        this.grantedAuthorities = grantedAuthorities;
        this.password = password;
        this.username = username;
        this.isAccountExpired = isAccountExpired;
        this.isAccountLocked = isAccountLocked;
        this.isCredentialExpired = isCredentialExpired;
        this.isEnabled = isEnabled;
    }

    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final String password;
    private final String username;
    private final boolean isAccountExpired;
    private final boolean isAccountLocked;
    private final boolean isCredentialExpired;
    private final boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
