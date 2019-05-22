package com.bogdan.projects.mytrips.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This section declares a custom annotation, in order to create a custom validation for user's phone number
 * The checking method is defined in PhoneNumberConstraintValidator class
 *
 * @author Bogdan Butuza
 */
@Documented
@Constraint(validatedBy = PhoneNumberConstraintValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}