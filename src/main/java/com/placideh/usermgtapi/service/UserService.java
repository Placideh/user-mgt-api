package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.entity.User;
import com.placideh.usermgtapi.Dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    List<User> getUsers();
    User getUserByEmail(String email);

    User getUserById(Integer id);

    Integer getUserByStatus(String status);
    User updateUser(UserDto user);
}
