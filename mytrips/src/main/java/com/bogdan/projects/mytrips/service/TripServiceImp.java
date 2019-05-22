package com.bogdan.projects.mytrips.service;

import com.bogdan.projects.mytrips.model.Trip;
import com.bogdan.projects.mytrips.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * This class represents the implementation of TripService interface
 * It hosts the methods that are servicing the trip objects and their contents
 *
 * @author Bogdan Butuza
 */
@Service
public class TripServiceImp implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Override
    public void save(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

    /**
     * This method uses tripRepository to find all the trips based on a user is
     *
     * @param userId an integer value, meaning trip's id
     * @return a list of Trip objects
     */
    @Override
    public List<Trip> findByUserId(int userId) {
        return tripRepository.findByUserId(userId);
    }

    /**
     * This method uses tripRepository to a certain trip, based on id value
     *
     * @param tripId an integer value, representing the trip id
     * @return a Trip object
     */
    @Override
    public Trip findByTripId(int tripId) {
        return tripRepository.findByTripId(tripId);
    }

    /**
     * This method uses tripRepository in order to obtain a certain trip, based on trip's id and user's id criteria
     *
     * @param tripId an integer value, trip's id
     * @param userId an integer value, user's id
     * @return a Trip object
     */
    @Override
    public Trip findByTripIdAndUserId(int tripId, int userId) {
        return tripRepository.findByTripIdAndUserId(tripId, userId);
    }

    /**
     * This method uses tripRepository to obtain a trip, by name criteria
     *
     * @param name a String value, representing the name
     * @return a Trip object, representing the desired trip
     */
    @Override
    public Trip findByName(String name) {
        return tripRepository.findByName(name);
    }

    /**
     * This function is used to find out if two trips with same name represent the same trip, by comparing their id
     * This is called when the validation is done, in order to update a trip safely
     *
     * @param trip a Trip object, as source
     * @return true, if the two objects don't represent the same trip
     * false, otherwise
     */
    @Override
    public boolean isNotSameTrip(Trip trip) {
        boolean sameName = false;
        Trip targetTrip = findByName(trip.getName());
        if (targetTrip != null) {
            if (targetTrip.getId() != trip.getId()) {
                sameName = true;
            }
        }
        return sameName;
    }

    /**
     * This method checks if an image can be updated, by checking its content
     * This is used in validation process, after performing a trip update
     *
     * @param photo an image, as an MultipartFile object
     * @return true, if the image can be updated
     * false, otherwise
     */
    @Override
    public boolean canUpdateImages(MultipartFile photo) {
        if (photo.isEmpty()) {
            return false;
        }

        return true;
    }

    public static final String dir = System.getProperty("user.dir") + "/myimages";

    /**
     * Method saveImage is used to save an image to a local directory
     *
     * @param imageFile a MultipartFile object, as an image
     * @param fileName  a String value, as the file's name
     * @throws Exception if the process can't succeed
     */
    @Override
    public void saveImage(MultipartFile imageFile, String fileName) throws Exception {
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(dir, fileName);
        Files.write(path, bytes);
    }

    /**
     * This method deletes an image, based on it's name, from the local directory
     *
     * @param fileName a String value, representing file's name
     */
    @Override
    public void deleteImage(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(dir, fileName));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
    }
}