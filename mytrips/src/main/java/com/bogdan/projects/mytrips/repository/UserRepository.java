package com.bogdan.projects.mytrips.repository;

import com.bogdan.projects.mytrips.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface holds the methods used for operating on User objects
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}