package com.lyf.service.impl;

import com.lyf.dao.realm.Permissions;
import com.lyf.dao.realm.Role;
import com.lyf.dao.realm.User;
import com.lyf.service.ShiroLoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ShiroLoginServiceImpl implements ShiroLoginService {

    @Override
    public User getUserByName(String name) {
        return getUser(name);
    }

    /*
    模拟数据库查询
     */

    private User getUser(String userName){
/*
  User {user_id,user_name,password,set<Role>}
  Role {role_id,role_name,set<Permission>}
  Permission {permission_id,permission_name}
  ??思考如何建表（若MySQL），另外可以使用redis进行存role吗
 */

        // 管理员权限 -add(代码 1)
        Permissions permission1 = new Permissions("1","addUser");
        // 管理员权限 -delete（代码 2）
        Permissions permission2 = new Permissions("2","deleteUser");
        Permissions permission3 = new Permissions("3","query");

        Set<Permissions> permissionsSet1 = new HashSet<>();
        Set<Permissions> permissionsSet2 = new HashSet<>();

        // 添加管理员的权限集合
        permissionsSet1.add(permission1);
        permissionsSet1.add(permission2);
        permissionsSet1.add(permission3);

        // 添加普通用户的权限集合
        permissionsSet2.add(permission3);

        // 创建角色
        Role admin = new Role("1","admin",permissionsSet1);
        Role custom = new Role("2","custom",permissionsSet2);
        // 外访人员
        Role peripheral = new Role("3","peripheral",permissionsSet2);


        Set<Role> roleSet1 = new HashSet<>();
        Set<Role> roleSet2 = new HashSet<>();

        roleSet1.add(admin);
        roleSet2.add(custom);
        roleSet2.add(peripheral);

        User usr1 = new User("1","李云飞","123321",roleSet1);
        User usr2 = new User("2","史可心","123321",roleSet2);


        Map<String,User> map = new HashMap<>();
        map.put(usr1.getUserName(),usr1);
        map.put(usr2.getUserName(),usr2);

        return map.get(userName);
    }

}
