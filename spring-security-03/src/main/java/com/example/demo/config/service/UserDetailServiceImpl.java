package com.example.demo.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义登录处理逻辑
 */
public class UserDetailServiceImpl implements UserDetailsService {

/*    @Autowired
    private PasswordEncoder passwordEncoder;*/

    /**
     * @param username 登录页面输入的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 根据 username 去用户表查询出来用户的信息，然后进行验证

        // 验证成功后，返回Spring-Security 提供的 User 对象
        // 对应三个构造参数依次是： 1.用户名 2.密码（经过 passwordEncoder 加密后的密码） 3.权限列表
        return new User(username, "$2a$10$g1gzj4KvMNY1kMZT1xDx9ufLuaDvCFDpX.PdETx85zQwXI/Mn4ttC", AuthorityUtils.createAuthorityList("admin"));
    }

}
