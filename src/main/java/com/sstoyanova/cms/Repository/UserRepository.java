package com.sstoyanova.cms.Repository;

import com.sstoyanova.cms.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    User findByUsername(String username);
}
