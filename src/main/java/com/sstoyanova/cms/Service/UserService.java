package com.sstoyanova.cms.Service;

import com.sstoyanova.cms.Entity.Permission;
import com.sstoyanova.cms.Entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> readUsers();
    void createUser(User user);
    void deleteUser(Long id) throws Exception;
    void updateUser(Long id, User user) throws Exception;
    void updatePermission(Long id, Set<Permission> permissionSet) throws Exception;
}
