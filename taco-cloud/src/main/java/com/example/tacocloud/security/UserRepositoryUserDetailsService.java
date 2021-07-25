package com.example.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.
                                              UserDetailsService;
import org.springframework.security.core.userdetails.
                                       UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tacocloud.User;
import com.example.tacocloud.data.UserRepository;

@Service// spring 的另一种构造性注解，表明这个类要包含到组件扫描里，被初始化为一个bean。
public class UserRepositoryUserDetailsService 
        implements UserDetailsService {

  private UserRepository userRepo;

  @Autowired
  public UserRepositoryUserDetailsService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UsernameNotFoundException(
                    "User '" + username + "' not found");
  }

}
