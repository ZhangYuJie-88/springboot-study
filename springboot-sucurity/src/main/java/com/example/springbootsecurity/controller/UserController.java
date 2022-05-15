package com.example.springbootsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-15 17:01
 **/
@RestController
public class UserController {

    @RequestMapping("/all")
    String all() {
        return "在WebSecurityConfig中配置了放行，任何人都可以进行访问";
    }

    @PreAuthorize("permitAll()")
    @RequestMapping("/test")
    String test() {
        return "所有登录的人都可以访问";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/user/userList")
    String userList() {
        return "role: user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin/updateUser")
    String updateUser() {
        return "role: admin";
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping("/admin/superAdmin")
    String superAdmin() {
        return "role: superAdmin";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("/userAndAdmin")
    String userAndAdminTest() {
        return "role: admin and user";
    }

    @PreAuthorize("hasAnyRole('ADMIN')or hasAnyRole('SUPER_ADMIN')")
    @RequestMapping("/AdminAndSuperAdminTest")
    String AdminAndSuperAdminTest() {
        return "role: admin and super_admin";
    }

    /**
     * hasAnyAuthority() 也是可以多个字符串 权限验证，可以不跟ROLE_前缀
     * @return
     */
    @PreAuthorize("hasAuthority('TEST') ")
    @RequestMapping("/ceshi2")
    String ceshi2() {
        return "hasAuthority：权限验证，不过查的也是role那个字段，不过不用拼接上ROLE而已";
    }
}
