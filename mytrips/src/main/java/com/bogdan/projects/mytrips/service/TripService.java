package com.bogdan.projects.mytrips.service;

import com.bogdan.projects.mytrips.model.Trip;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface TripService holds the main methods which will service the Trip objects
 *
 * @author Bogdan Butuza
 */
public interface TripService {

    void save(Trip trip);

    void delete(Trip trip);

    void saveImage(MultipartFile file, String fileName) throws Exception;

    void deleteImage(String fileName);

    boolean canUpdateImages(MultipartFile photo);

    List<Trip> findByUserId(int userId);

    Trip findByName(String name);

    boolean isNotSameTrip(Trip trip);

    Trip findByTripId(int tripId);

    Trip findByTripIdAndUserId(int tripId, int userId);
}
