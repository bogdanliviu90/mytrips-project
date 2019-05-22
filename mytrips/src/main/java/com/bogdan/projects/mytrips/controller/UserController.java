package com.bogdan.projects.mytrips.controller;

import com.bogdan.projects.mytrips.model.User;
import com.bogdan.projects.mytrips.service.SecurityService;
import com.bogdan.projects.mytrips.service.UserService;
import com.bogdan.projects.mytrips.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Class UserController defines the main controllers with paths and requests, for the available actions which will be
 * performed on users: login, registration, profile view, profile edit
 *
 * @author Bogdan Butuza
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;


    /**
     * This method provides mapping for registration process
     * @param model
     * @return "registration" page
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    /**
     * This method provides user registration process, by validating an incoming user and saving it
     * @param user the completed User to be validated and saved to the database
     * @param bindingResult
     * @return redirect to "profile" page, if the sign up succeeds
     *         back to "registation" page, otherwise
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(user);

        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

        return "redirect:/profile";
    }

    /**
     * This method handles the login process
     * @param model the current model to add attributes - messages in this case
     * @param error as encountered error
     * @param logout a String value which will be initialized when the user will triger the logout button
     * @return "login" page
     */
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password are invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    /**
     * When a user logs in, it will be automatically redirected to the main Trips page
     * @return redirects the user to "trips" page
     */
    @GetMapping("/")
    public String redirectToMainPage() {
        return "redirect:/trips";
    }

    /**
     * This method handles a User's profile, in order for the user to see its details - primary information
     * @param user the current user
     * @return "profile" page
     */
    @GetMapping("/profile")
    public ModelAndView showProfile(User user) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("profile");
        mv.addObject("user", userService.getLoggedInUser());
        return mv;
    }

    /**
     * This method provides handling for editing a user profile
     * @param model the current model to add atributes
     * @return "editprofile" page
     */
    @GetMapping("/profile/edit")
    public String showEditProfile(Model model) {
        model.addAttribute("user", userService.getLoggedInUser());

        return "editprofile";
    }

    /**
     * This method handles when a user chooses to save the edited profile
     * @param user an User object, containing the edited informations
     * @param bindingResult the results to check for errors
     * @return redirect to "profile" page, if binding succeeds and it saves the changes made
     *         "editprofile" page otherwise
     */
    @PostMapping("/profile/edit")
    public String saveProfile(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "editprofile";
        }

        userService.save(user);
        return "redirect:/profile";
    }

    /**
     * This method handles the logout request
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return redirects to "login" page
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

}