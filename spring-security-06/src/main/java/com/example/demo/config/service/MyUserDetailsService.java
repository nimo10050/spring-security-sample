package com.example.demo.config.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.model.User;
import com.example.demo.pojo.UserWithRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @auther zgp
 * @desc
 * @date 2020/8/4
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // userMapper.selectOne()
        // 查询用户信息（角色、权限等）
        UserWithRole user = userMapper.getUserWithRoleByUsername(username);
        if (user == null) {
            return null;
        }
        return new MyUserDetails(username, user.getPassword(), user.getRoleList());
    }
}
