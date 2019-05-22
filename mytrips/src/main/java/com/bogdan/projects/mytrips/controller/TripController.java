package com.bogdan.projects.mytrips.controller;

import com.bogdan.projects.mytrips.model.Trip;
import com.bogdan.projects.mytrips.model.User;
import com.bogdan.projects.mytrips.service.TripService;
import com.bogdan.projects.mytrips.service.TripServiceImp;
import com.bogdan.projects.mytrips.service.UserService;
import com.bogdan.projects.mytrips.validator.TripValidator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * Class TripController defines the main controllers with paths and requests, for the available actions which will be
 * performed on trips: add a new trip, view trip details, edit existing trip and delete a trip
 *
 * @author Bogdan Butuza
 */
@Controller
@RequestMapping(path = "/trips")
public class TripController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    @Autowired
    TripValidator tripValidator;


    /**
     * This provides mapping for adding a new trip
     * @param model
     * @return "newtrip" html page
     */
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("trip", new Trip());
        return "newtrip";
    }

    /**
     * Provides mapping for a new trip
     * @param firstImage first image as a MultipartFile, in order to be saved on disk an set it's location
     * @param secondImage second image as a MultipartFile, in order to be saved on disk an set it's location
     * @param trip the new trip to be validated and saved
     * @param bindingResult
     * @return redirect to "trips" page, if no errors
     *         "newtrip" page, otherwise
     */
    @PostMapping("/new")
    public String addTrip(@RequestParam("firstPhoto") MultipartFile firstImage,
                          @RequestParam("secondPhoto") MultipartFile secondImage,
                          @ModelAttribute("trip") @Valid Trip trip, BindingResult bindingResult) {

        try {
            tripService.saveImage(firstImage, firstImage.getOriginalFilename());
            trip.setFirstPhotoLocation(firstImage.getOriginalFilename());
            tripService.saveImage(secondImage, secondImage.getOriginalFilename());
            trip.setSecondPhotoLocation(secondImage.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return "newtrip";
        }

        trip.setUser(userService.getLoggedInUser());

        tripValidator.validate(trip, bindingResult);

        if (bindingResult.hasErrors()) {
            return "newtrip";
        }

        tripService.save(trip);

        return "redirect:/trips";
    }

    /**
     * This method gets the mapping for the main Trips page, in order to display/add new/edit/delete desired trip
     * @param model
     * @return "trips" page
     */
    @GetMapping(value = "")
    public String showTrip(Model model) {

        User user = userService.getLoggedInUser();
        List<Trip> trips = tripService.findByUserId(user.getId());

        if (trips.size() == 0) {
            return "redirect:/trips/new";
        }

        Trip trip = trips.get(0);
        model.addAttribute("trip", trip);
        model.addAttribute("trips", trips);
        model.addAttribute("mapsearch", trip.getLocation());

        return "trips";
    }

    /**
     * This method is triggered by the dropdown options select menu, present on Trips page
     * It loads the page with the list of trips - belonging only to the loggedin user
     * @param tripId an integer value, representing inputted trip id
     * @param model
     * @return "trips" page
     */
    @GetMapping(value = "/show")
    public String showTrip(@ModelAttribute("id") int tripId, Model model) {

        User user = userService.getLoggedInUser();
        List<Trip> trips = tripService.findByUserId(user.getId());
        Trip trip = tripService.findByTripIdAndUserId(tripId, user.getId());

        if ((trips.size() == 0) || (trip == null)) {
            return "redirect:/trips/new";
        }

        model.addAttribute("id", tripId);
        model.addAttribute("trip", trip);
        model.addAttribute("trips", trips);

        return "trips";
    }

    /**
     * Offers mapping for editing a chosen trip
     * @param tripId an integer value, representing the inputted trip id
     * @param model
     * @return "edittrip" page, if a trip exists and the user wants to change it
     *         redirect to "/trips/new", if the user hasn't saved a trip yet
     */
    @GetMapping(value = "/edit")
    public String editTrip(@RequestParam("id") int tripId, Model model) {

        Trip trip = tripService.findByTripIdAndUserId(tripId, userService.getLoggedInUser().getId());
        if (trip == null) {
            return "redirect:/trips/new";
        }

        model.addAttribute("trip", trip);

        return "edittrip";
    }

    /**
     * Provides mapping for editing an existing trip
     * @param firstImage first image as a MultipartFile, in order to be saved on disk an set it's location
     * @param secondImage second image as a MultipartFile, in order to be saved on disk an set it's location
     * @param trip the new trip to be validated and saved
     * @param bindingResult
     * @return redirect to "trips" page, if no errors
     *         "newtrip" page, otherwise
     */
    @PostMapping(value = "/edit")
    public String saveEditedTrip(@RequestParam("firstPicture") MultipartFile firstImage,
                                 @RequestParam("secondPicture") MultipartFile secondImage,
                                 @ModelAttribute("trip") Trip trip,
                                 Model model, BindingResult bindingResult) {

        trip.setUser(userService.getLoggedInUser());

        // Checking if user has selected images to update, keeping the old images otherwise
        // If new photos will be saved, the old ones must be also deleted
        Trip currentTrip = tripService.findByTripIdAndUserId(trip.getId(), userService.getLoggedInUser().getId());

        if (tripService.canUpdateImages(firstImage)) {
            try {
                tripService.deleteImage(currentTrip.getFirstPhotoLocation());
                tripService.saveImage(firstImage, firstImage.getOriginalFilename());
                trip.setFirstPhotoLocation(firstImage.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
                return "edittrip";
            }
        }

        if (tripService.canUpdateImages(secondImage)) {
            try {
                tripService.deleteImage(currentTrip.getSecondPhotoLocation());
                tripService.saveImage(secondImage, secondImage.getOriginalFilename());
                trip.setSecondPhotoLocation(secondImage.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
                return "edittrip";
            }
        }

        if (trip.getFirstPhotoLocation() == null) {
            trip.setFirstPhotoLocation(tripService.findByTripId(trip.getId()).getFirstPhotoLocation());
        }

        if (trip.getSecondPhotoLocation() == null) {
            trip.setSecondPhotoLocation(tripService.findByTripId(trip.getId()).getSecondPhotoLocation());
        }

        tripValidator.validate(trip, bindingResult); // updateTripValidator

        if (bindingResult.hasErrors()) {
            return "edittrip";
        }

        tripService.save(trip);

        model.addAttribute("trip", trip);

        return "redirect:/trips";
    }

    /**
     * This method deletes the selected trip, based on its id and current user's id
     * @param tripId trip's id as an integer value
     * @return redirect to main "trips" page
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteTrip(@RequestParam("id") int tripId) {

        Trip trip = tripService.findByTripIdAndUserId(tripId, userService.getLoggedInUser().getId());
        tripService.deleteImage(trip.getFirstPhotoLocation());
        tripService.deleteImage(trip.getSecondPhotoLocation());
        tripService.delete(trip);

        return "redirect:/trips";
    }

}