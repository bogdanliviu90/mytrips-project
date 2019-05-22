package com.bogdan.projects.mytrips.repository;

import com.bogdan.projects.mytrips.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface RoleRepository inherits the methods responsable for CRUD operations
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
