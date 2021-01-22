package com.lyf.dao.realm;


import java.util.Set;

public class User {

    private String id;
    private String userName;
    private String password;
    /*
    用户对应的角色集合
    理解一个用户有许多角色？
    一个用户对应不同角色，不同角色对应不同的职责
    https://www.cnblogs.com/StanleyBlogs/p/12040485.html
     */
    private Set<Role> roles;

    public User(String id, String userName, String password, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
