package net.torrydev.stockmanagementsystem.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static net.torrydev.stockmanagementsystem.model.AppUserPermission.*;

public enum AppUserRole {
    // A role can have multiple(Unique) Permissions/Authorities
    ADMIN(Set.of(ADMIN_READ, ADMIN_WRITE)), // Read and Write Permission
    USER(Set.of(USER_READ)), // Read only permission

    DEVELOPER(Set.of(DEVELOPER_READ));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    // Custom GrantAuthority - to have role and permission
    public Set<? extends GrantedAuthority> getSimpleGrantedAuthorities(){
        // Convert each Permission to SimpleGrantedAuthority - that it accepted by authorities method of UserDetails Interface
       Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions().stream().map(appUserPermission -> new SimpleGrantedAuthority(appUserPermission.getPermission()))
                .collect(Collectors.toSet());
       simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.name())); // Add the role that we created permission for
        return simpleGrantedAuthorities;
    }
}
