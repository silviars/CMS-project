package com.sstoyanova.cms.Service;

import com.sstoyanova.cms.Entity.Permission;
import com.sstoyanova.cms.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    //used for the super admin
    @Override
    public Set<Permission> getAllPermissions(){
        Set<Permission> permissions = new HashSet<>();
        permissions.addAll(permissionRepository.findAll());
        return permissions;
    }
}