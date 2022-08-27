package com.example.SpringZhanyl.Entity;

public enum Permission {
    CAN_READ("can:read"),
    CAN_WRITE("can:write");

    private final String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
