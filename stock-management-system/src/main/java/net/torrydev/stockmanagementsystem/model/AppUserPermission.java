package net.torrydev.stockmanagementsystem.model;

public enum AppUserPermission {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELET("admin:delete"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),

    DEVELOPER_READ("dev:read"),
    DEVELOPER_WRITE("dev:write");

    private String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
