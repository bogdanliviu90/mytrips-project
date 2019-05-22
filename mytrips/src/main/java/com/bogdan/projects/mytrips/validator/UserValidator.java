package com.bogdan.projects.mytrips.validator;

import com.bogdan.projects.mytrips.model.User;
import com.bogdan.projects.mytrips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class UserValidator does a customized validation process applied to a User object
 *
 * @author Bogdan Butuza
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    /**
     * This function checks if the validation will be applied on the right object, instance of Trip
     * @param aClass
     * @return true or false
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * This method does the validations for every field belonging to a User
     * @param o an Object, as the object to be validated - casted to a User
     * @param errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //Rejections for empty and white spaces

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty");
        if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.user.username");
        }

        // When updating a user, checking also if it can keep the same username as before (it reffers to same instance)
        if (userService.findByUsername(user.getUsername()) != null && userService.isNotSameUser(user)) {
            errors.rejectValue("username", "Duplicate.user.username");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
        }
    }
}