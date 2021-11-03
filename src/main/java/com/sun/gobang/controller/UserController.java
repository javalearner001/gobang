package com.sun.gobang.controller;

import com.sun.gobang.entity.User;
import com.sun.gobang.entity.response.BaseResult;
import com.sun.gobang.service.IUserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sunkai
 * @since 2021/3/17 11:29 上午
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserservice userservice;

    @GetMapping("/listUser")
    public BaseResult<List<User>> listUser(){
        return userservice.getUserList();
    }
}
