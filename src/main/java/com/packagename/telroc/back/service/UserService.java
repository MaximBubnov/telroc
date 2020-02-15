package com.packagename.telroc.back.service;

import com.packagename.telroc.back.domain.Role;
import com.packagename.telroc.back.domain.User;
import com.packagename.telroc.back.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //для первого запуска
    @PostConstruct
    public void createTestUser() {
        if(userRepo.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("123");
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
        }
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User createUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        return userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
