package com.weixf.test.shiro.realm;


import com.weixf.test.shiro.domain.Permission;
import com.weixf.test.shiro.domain.Role;
import com.weixf.test.shiro.domain.User;
import com.weixf.test.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        String s = new Md5Hash("123456", "wangwu", 3).toString();
        System.out.println(s);
    }

    public void setName(String name) {
        super.setName("customRealm");
    }

    /**
     * 授权方法
     * 操作的时候，判断用户是否具有响应的权限
     * 先认证 -- 安全数据
     * 再授权 -- 根据安全数据获取用户具有的所有操作权限
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.获取已认证的用户数据
        User user = (User) principalCollection.getPrimaryPrincipal();// 得到唯一的安全数据
        // 2.根据用户数据获取用户的权限信息（所有角色，所有权限）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();// 所有角色
        Set<String> perms = new HashSet<>();// 所有权限
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            for (Permission perm : role.getPermissions()) {
                perms.add(perm.getCode());
            }
        }
        info.setStringPermissions(perms);
        info.setRoles(roles);
        return info;
    }

    /**
     * 认证方法
     * 参数：传递的用户名密码
     */
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 1.获取登录的用户名密码（token）
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        // 2.根据用户名查询数据库
        User user = userService.findByName(username);

        String pwd = new Md5Hash(user.getPassword(), user.getUsername(), 3).toString();

        // 3.判断用户是否存在或者密码是否一致
        // if (user != null && user.getPassword().equals(password)) {
        if (pwd.equals(password)) {
            // 4.如果一致返回安全数据
            // 构造方法：安全数据，密码，realm域名
            // SimpleAuthenticationInfo info =
            //         new SimpleAuthenticationInfo(
            //                 user, user.getPassword(), this.getName());
            return new SimpleAuthenticationInfo(
                    user, pwd, this.getName());
        }
        // 5.不一致，返回null（抛出异常）
        return null;
    }
}
