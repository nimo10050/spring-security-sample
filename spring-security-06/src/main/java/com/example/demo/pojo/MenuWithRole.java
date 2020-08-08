package com.example.demo.pojo;

import com.example.demo.model.Role;
import lombok.Data;

import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2020/8/4
 */
@Data
public class MenuWithRole {

    private Integer id;

    private String name;

    private String url;

    private List<Role> roleList;
}
