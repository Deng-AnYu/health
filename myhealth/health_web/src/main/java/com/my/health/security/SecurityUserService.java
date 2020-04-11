package com.my.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.pojo.Permission;
import com.my.health.pojo.Role;
import com.my.health.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: Deng
 * @date: 2020-04-11 23:16
 * @description:
 */
@Component
public class SecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(s);
            if (user == null) {
                return null;
            }
            Set<Role> roles = user.getRoles();
            ArrayList<GrantedAuthority> list = new ArrayList<>();
            //把权限全都放进去
            for (Role role : roles) {
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                }
            }

            return new org.springframework.security.core.userdetails.User(s,user.getPassword(),list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
