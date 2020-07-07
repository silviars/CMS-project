package com.sstoyanova.cms.Service;

import com.sstoyanova.cms.Entity.Permission;
import com.sstoyanova.cms.Entity.User;
import com.sstoyanova.cms.Repository.PermissionRepository;
import com.sstoyanova.cms.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> readUsers(){
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) throws Exception{
        if(userRepository.existsById(id)){
            if(!userRepository.findByUsername("super.admin").getId().equals(id)) {
                userRepository.deleteById(id);
            } else{
                throw new Exception("You can't delete the super admin!");
            }
        } else {
            throw new Exception("User with id " + id + " doesn't exist!");
        }
    }

    @Override
    public void updateUser(Long id, User userDetails) throws Exception{
        if(userRepository.existsById(id)){
            User user = userRepository.findUserById(id);
            user.setUsername(userDetails.getUsername());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRole(userDetails.getRole());
            userRepository.save(user);
        } else{
            throw new Exception("User with id " + id + " doesn't exist!");
        }
    }

    //if someone tries to add none-existent permission???? - it throws exception
    @Override
    public void updatePermission(Long id, Set<Permission> permissionSet) throws Exception {
        if(userRepository.existsById(id)){
            User user = userRepository.findUserById(id);

            for(Permission p: permissionSet){
                p.setId(permissionRepository.findPermissionId(p.getName()));
            }
            user.setPermissions(permissionSet);
            userRepository.save(user);
        } else{
            throw new Exception("User with id " + id + " doesn't exist!");
        }
    }
}