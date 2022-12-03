package net.torrydev.stockmanagementsystem.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.torrydev.stockmanagementsystem.model.AppUserPermission.*;

public enum AppUserRole {
    // A role can have multiple(Unique) Permissions/Authorities
    ADMIN(Set.of(ADMIN_READ, ADMIN_WRITE)), // Read and Write Permission
    USER(Set.of(USER_READ)); // Read only permission

    private static Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }
}
