package com.sun.gobang.service;


import com.sun.gobang.entity.User;
import com.sun.gobang.entity.response.BaseResult;

import java.util.List;

/**
 * @author sunkai
 * @since 2021/3/17 11:13 上午
 */
public interface IUserservice {

    BaseResult<List<User>> getUserList();
}
