package com.school.fee_management_system.service;

import com.school.fee_management_system.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    User getUserById(String id);
    User getUserByEmail(String email);
    String createUser(User user);
    String updateUser(User user);
}
