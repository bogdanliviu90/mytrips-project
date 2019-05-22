package com.bogdan.projects.mytrips.validator;


import com.google.common.base.Joiner;
import org.passay.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Implements the password validation(@ValidPassword)
 *
 * @author Bogdan Butuza
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {

    }

    /**
     * Method ifValid checks if the given password respects or not the set of rules defined within
     * @param password a String value, representing the given password
     * @param context a ConstraintValidatorContext object, which manages validation and constraint violations sets
     * @return true, if the result is valid
     *         false, otherwise
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(10, 60),
                new UppercaseCharacterRule(1),
                new LowercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
