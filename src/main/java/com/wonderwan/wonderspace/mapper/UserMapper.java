package com.wonderwan.wonderspace.mapper;

import com.wonderwan.wonderspace.model.entity.User;
import com.wonderwan.wonderspace.model.param.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    User queryUserByUniqueParam(UserVO userVO);

    List<User> queryUserList(UserVO userVO);

    User createUser(User user);

    void updateUser(User user);
}
