package com.school.fee_management_system.service;


import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.User;
import com.school.fee_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        User userDetails=userRepository.findByEmail(email);
        if(userDetails==null){
            throw new ResourceNotFoundException("User Not found");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public String createUser(User user) {
        if(userRepository.findById(user.getId()).isPresent()){
            return "User Already Exists";
        }
        else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
        return "User is created";
    }

    @Override
    public String updateUser(User user) {
        if(userRepository.findById(user.getId()).isPresent()){
            userRepository.save(user);
            return "User details updated";
        }
        else {
            throw new ResourceNotFoundException("User Details Not found");
        }
    }
}
