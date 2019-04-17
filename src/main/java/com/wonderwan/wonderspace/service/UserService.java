package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.mapper.UserMapper;
import com.wonderwan.wonderspace.model.entity.User;
import com.wonderwan.wonderspace.model.param.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserByUniqueParam(UserVO userVO) {
        return userMapper.queryUserByUniqueParam(userVO);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
