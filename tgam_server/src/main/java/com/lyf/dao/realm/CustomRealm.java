package com.lyf.dao.realm;

import com.lyf.service.ShiroLoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


    // 权限认证自定义Realm用于查询用户的角色和权限信息并保存到权限管理器
    public class CustomRealm extends AuthorizingRealm {
        @Autowired
        private ShiroLoginService loginService;

        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

            // 获取登录用户名？？
            String name = (String)principalCollection.getPrimaryPrincipal();
            // 查询用户名称
            User usr = loginService.getUserByName(name);

            // 添加角色和权限
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            // 添加角色（名称）
            for(Role role: usr.getRoles()){
                simpleAuthorizationInfo.addRole(role.getRoleName());

                // 添加权限
                for(Permissions permissions:role.getPermissions()){
                    // 添加的权限名称（也可权限对象）
                    simpleAuthorizationInfo.addStringPermission(permissions.getPermissionName());
                }

            }
            return simpleAuthorizationInfo;
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

            if(StringUtils.isEmpty(authenticationToken.getPrincipal())){
                return null;
            }

            String name = authenticationToken.getPrincipal().toString();
            User user = loginService.getUserByName(name);

            if(user == null){
                return  null;
            }else{
                // 验证
                //  SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(name,user.getPassword());
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,user.getPassword(),getName());
                return simpleAuthenticationInfo;
            }
        }
    }


