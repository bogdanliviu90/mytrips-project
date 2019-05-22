package com.bogdan.projects.mytrips.service;

import com.bogdan.projects.mytrips.model.User;
import com.bogdan.projects.mytrips.repository.RoleRepository;
import com.bogdan.projects.mytrips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;

/**
 * Class UserSeriveImpl holds the method implementations of the interface UserService
 * Servicing with the User objects
 *
 * @author Bogdan Butuza
 */
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    /**
     * Saves a user/ updates a user, by saving the modified values
     * @param user an User object, representingt the user
     */
    @Override
    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    /**
     * Finds a user, by using the username criteria
     * @param username the username to search with, as a String value
     * @return the User, found from userRepository
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * This method uses the current the security context holder and the current authentication, in order to get the
     * loggedin user details
     * @return the logged in user, as an User object
     */
    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        return findByUsername(currentUserName);
    }

    /**
     * This method checks if two users are not reffered to the same user
     * This helps in the update's validation process, to avoid prompting the user to insert a different username
     * It litteraly checks this by looking if the same username is shared with the new user values, provided by the user
     * update form, then compares their ids
     * @param user an User object, as the updated user values
     * @return false, if these two values (username and id) are matching
     *         true, otherwise
     */
    @Override
    public boolean isNotSameUser(User user) {
        boolean sameName = false;
        User targetUser = findByUsername(user.getUsername());
        if (targetUser != null) {
            if (targetUser.getId() != user.getId()) {
                sameName = true;
            }
        }
        return sameName;
    }
}
