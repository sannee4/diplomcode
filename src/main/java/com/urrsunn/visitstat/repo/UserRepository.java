package com.urrsunn.visitstat.repo;

import com.urrsunn.visitstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
