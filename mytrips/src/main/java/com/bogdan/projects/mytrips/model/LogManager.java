package com.bogdan.projects.mytrips.model;

/**
 *
 */
public class LogManager {

    private static final String ERROR_INCORRECT_USERNAME = "The username you entered does not exist. Please try again";
    private static final String ERROR_INCORRECT_PASSWORD = "The password you entered is incorrect. Please try again";

    public boolean validUsername(String insertedUsername, User user) {
        boolean found = false;
        if (insertedUsername.equals(user.getUsername())) {
            found = true;
        }
        return found;
    }

    public boolean validPassword(String insertedPassword, User user) {
        boolean found = false;
        if (insertedPassword.equals(user.getPassword())) {
            found = true;
        }
        return found;
    }

    public boolean validUser(String username, String password, User user) {
        if (validUsername(username, user) && validPassword(password, user)) {
            return true;
        }
        return false;
    }

    public String getErrorMessage(String username, String password, User user) {
        if (!validUsername(username, user)) {
            return ERROR_INCORRECT_USERNAME;
        } else if (!validPassword(password, user)) {
            return ERROR_INCORRECT_PASSWORD;
        } else {
            return null;
        }
    }
}