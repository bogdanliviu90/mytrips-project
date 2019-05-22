package com.bogdan.projects.mytrips.validator;

import com.bogdan.projects.mytrips.model.Trip;
import com.bogdan.projects.mytrips.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

/**
 * Class TripValidator does a customized validation process applied to a Trip object
 *
 * @author Bogdan Butuza
 */
@Component
public class TripValidator implements Validator {

    @Autowired
    private TripService tripService;

    /**
     * This function checks if the validation will be applied on the right object, instance of Trip
     * @param aClass
     * @return true or false
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Trip.class.equals(aClass);
    }

    /**
     * This method does the validations for every field belonging to a Trip
     * @param o an Object, as the object to be validated - casted to a Trip
     * @param errors
     */
    @Override
    public void validate(Object o, Errors errors) {

        Trip trip = (Trip) o;

        //Rejections for empty and white spaces

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateFrom", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateTo", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstPhotoTitle", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstPhotoDescription", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondPhotoTitle", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondPhotoDescription", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "impressions", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "NotEmpty");

        if (trip.getName().length() < 4 || trip.getName().length() > 32) {
            errors.rejectValue("name", "Size.trip.name");
        }

        // When updating a trip, checking also if it can keep the same trip name as before (it reffers to same instance)
        if ((tripService.findByName(trip.getName()) != null) && (tripService.isNotSameTrip(trip))) {
            errors.rejectValue("name", "Duplicate.trip.name");
        }

        // Validates if DateTo comes after DateFrom and also if the DateFrom doesn't come after the current date
        if (trip.getDateFrom() != null && trip.getDateTo() !=null) {
            if (trip.getDateFrom().isAfter(trip.getDateTo())) {
                errors.rejectValue("dateFrom", "IsAfter.trip.dateFrom");
                errors.rejectValue("dateTo", "IsAfter.trip.dateTo");
            }

            LocalDate date = LocalDate.now();
            if (trip.getDateFrom().isAfter(date) || trip.getDateTo().isAfter(date)) {
                errors.rejectValue("dateFrom", "Invalid.trip.date");
            }
        }

        if (trip.getFirstPhotoTitle().length() < 4 || trip.getFirstPhotoTitle().length() > 32) {
            errors.rejectValue("firstPhotoTitle", "Title.trip.photo");
        }

        // When creating a trip, this section checks if no images where selected
        // It also prevents the constraint violation in the updating validation process, if a user chooses to keep the
        // same images

        if (trip.getFirstPhotoLocation() == null) {
            errors.rejectValue("firstPhotoLocation", "Empty.trip.images");
        }

        if (trip.getSecondPhotoLocation() == null) {
            errors.rejectValue("secondPhotoLocation", "Empty.trip.images");
        }
    }
}