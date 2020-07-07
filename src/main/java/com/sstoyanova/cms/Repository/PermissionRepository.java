package com.sstoyanova.cms.Repository;

import com.sstoyanova.cms.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Query("select id from Permission where name =:name")
    Long findPermissionId(String name);
}
