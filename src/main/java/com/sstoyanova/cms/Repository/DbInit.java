package com.sstoyanova.cms.Repository;

import com.sstoyanova.cms.Entity.User;
import com.sstoyanova.cms.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionService permissionService;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        User user = new User("super.admin",passwordEncoder.encode("pass"), "SUPER-ADMIN",
                permissionService.getAllPermissions());
        this.userRepository.save(user);
    }
}
