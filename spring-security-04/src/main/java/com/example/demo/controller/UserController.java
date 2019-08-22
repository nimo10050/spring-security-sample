package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 为了方便, 没有抽取 service 层
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 自定义用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return CommonResult.failed("密码错误！");
            }
            // 保存用户登录态
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);// 进行到这一步，security 才会认为你登陆成功
            String token = jwtTokenUtil.generateToken(userDetails);
            return CommonResult.success(token);// 到这里, 从我们的业务角度来说登陆成功了
        } catch (AuthenticationException e) {
            System.out.println("登录异常: " + e.getMessage());
            return CommonResult.failed("登录失败");
        }

    }

    @GetMapping("/userInfo")
    public Object getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            System.out.println(principal);
        }
        return null;
    }
}
