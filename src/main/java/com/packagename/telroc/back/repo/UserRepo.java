package com.packagename.telroc.back.repo;

import com.packagename.telroc.back.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
