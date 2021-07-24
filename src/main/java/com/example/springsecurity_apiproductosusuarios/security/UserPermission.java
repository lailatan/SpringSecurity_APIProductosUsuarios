package com.example.springsecurity_apiproductosusuarios.security;

public enum UserPermission {
    PRODUCTO_READ("producto:read"),
    PRODUCTO_WRITE("producto:write"),
    USER_READ("user:read"),
    USER_WRITE ("user:write");

    private String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
