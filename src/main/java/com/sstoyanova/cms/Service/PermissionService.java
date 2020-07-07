package com.sstoyanova.cms.Service;

import com.sstoyanova.cms.Entity.Permission;

import java.util.Set;

public interface PermissionService {
    Set<Permission> getAllPermissions();
}