package com.darkthor.Service;

import com.darkthor.Exceptions.EmailNotFoundException;
import com.darkthor.Exceptions.UserNotFoundException;
import com.darkthor.Model.User;
import com.darkthor.Request.UserRequest;

import java.util.List;

public interface IUserService {
    User createUser (final UserRequest userRequest);
    User getUserByEmail (final String email)throws EmailNotFoundException;
    User updateUser (final Long userId,final UserRequest userRequest);
    boolean deleteUser (final String email)throws UserNotFoundException;
    List<User> getUsers();



}
