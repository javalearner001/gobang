package com.sun.gobang.service;

import com.alibaba.fastjson.JSON;
import com.sun.gobang.entity.User;
import com.sun.gobang.entity.response.BaseResult;
import com.sun.gobang.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunkai
 * @since 2021/3/17 11:18 上午
 */
@Service
public class UserServiceImpl implements IUserservice{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

   /* ThreadPoolExecutor executor = new ThreadPoolExecutor(2,5,1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
*/

    @Override
    public BaseResult<List<User>> getUserList() {
        BaseResult<List<User>> result = new BaseResult();
        User user = new User();
        user.setFlagBin(4);
        List<User> users = userMapper.listUser(user);

        logger.info("getUserList res={}", JSON.toJSONString(result));
        result.setData(users);
        return result;
    }

}
