package com.lyf.dao.realm;

public class Permissions {
    private String id;
    private String permissionName;// 对应的权限名称 = >根据权限名放权？？

    public Permissions(String id, String permissionName) {
        this.id = id;
        this.permissionName = permissionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
