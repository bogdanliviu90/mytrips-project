package com.bogdan.projects.mytrips.service;

import com.bogdan.projects.mytrips.model.User;

/**
 * UserService holds the methods to operate with User objects
 *
 * @author Bogdan Butuza
 */
public interface UserService {

    void save(User user);

    boolean isNotSameUser(User user);

    User findByUsername(String username);

    User getLoggedInUser();

}
