package com.bogdan.projects.mytrips.service;

/**
 * Interface SecurityService holds methods responsable for the logged in user
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}