package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Menu;
import com.example.demo.model.MenuRole;
import com.example.demo.pojo.MenuWithRole;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
    List<MenuWithRole> getMenusWithRole();
}