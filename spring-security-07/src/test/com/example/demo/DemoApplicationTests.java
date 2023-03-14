
package com.example.demo;



import com.example.demo.config.service.MyUserDetailsService;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.MenuRole;
import com.example.demo.model.User;
import com.example.demo.pojo.MenuWithRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest// 指定启动类
public class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Test
    public  void test_select_user() {
        List<User> users = userMapper.selectList(null);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public  void test_select_menuWithRole() {
        List<MenuWithRole> menusWithRole = menuMapper.getMenusWithRole();
        Assert.assertEquals(1, menusWithRole.size());

    }

    @Test
    public void loadUserByUsername() {
        UserDetails nimo = userDetailsService.loadUserByUsername("nimo");
        System.out.println(nimo);
    }

    @Test
    public void encode() {

        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

}
