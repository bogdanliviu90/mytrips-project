package com.bogdan.projects.mytrips.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class implements the phone number validation (@ValidPhoneNumber)
 *
 * @author Bogdan Butuza
 */
public class PhoneNumberConstraintValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public void initialize(ValidPhoneNumber number) {

    }

    /**
     * This function checks if a given phone number represents a valid Romanian phone number
     * It checks for the first 2 digits to be "07" and to contain exactly 10 digits
     * @param phone the given phone number, as a String value
     * @param context a ConstraintValidatorContext object, which manages validation and constraint violations sets
     * @return true, if it's valid
     *         false, otherwise
     */
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return (phone != null && phone.matches("^07[0-9]+") && phone.length() == 10);
    }
}