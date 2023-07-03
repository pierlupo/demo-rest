package com.demorest.service;


import com.demorest.entity.User;
import com.demorest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    public boolean getUserUserName(String userName) {
        User user = userRepository.findUserByUsername(userName);
        return user != null ? true : false;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public User updateUser(Integer id, User user){
    Optional<User> user1 = userRepository.findById(id);
    if(user1.isPresent()){
        User user2 = user1.get();
        user2.setUsername(user.getUsername());
        return userRepository.save(user2);
    }
    return null;
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
