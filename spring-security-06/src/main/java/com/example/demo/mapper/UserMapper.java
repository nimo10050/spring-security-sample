package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.User;
import com.example.demo.pojo.UserWithRole;


public interface UserMapper extends BaseMapper<User> {

    UserWithRole getUserWithRoleByUsername(String username);
}