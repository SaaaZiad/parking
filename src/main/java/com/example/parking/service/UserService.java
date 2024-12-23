package com.example.parking.service;

import com.example.parking.exception.UserNotFound;
import com.example.parking.model.User;
import com.example.parking.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
  private final UserRepo userRepo;
  public void saveUser(User user){
    userRepo.save(user);
  }
  public User findUserById(Long userId){
    return userRepo.findById(userId).orElseThrow(()->new UserNotFound("No such user"));
  }
  public User findUserByUsername(String username){
    return userRepo.findByUserName(username);
  }
}
