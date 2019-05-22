package com.bogdan.projects.mytrips.repository;

import com.bogdan.projects.mytrips.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface TripRepository holds the CRUD operation methods responsable for servicing the Trip objects
 *
 * @author Bogdan Butuza
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    /**
     * This method selects all the trips registered from a user, based on users id
     * @param userId an integer value, representing the user id
     * @return a list of Trip objects, representing the trips of the given user
     */
    @Query(value = "SELECT * FROM trips WHERE user_id =:value", nativeQuery = true)
    List<Trip> findByUserId(@Param("value") int userId);

    /**
     * This method finds a certain trip by the trip id criteria
     * @param tripId an integer value, representing the trip id
     * @return a Trip object, representing the desired trip
     */
    @Query(value = "SELECT * FROM trips WHERE id =:value", nativeQuery = true)
    Trip findByTripId(@Param("value") int tripId);

    /**
     * This method returns a trip, based on two values
     * @param tripId an integer value, meaning the trip id
     * @param userId an integer value, representing user's id
     * @return a Trip object, representing the desired trip
     */
    @Query(value = "SELECT * FROM trips WHERE id =:value1 AND user_id =:value2", nativeQuery = true)
    Trip findByTripIdAndUserId(@Param("value1") int tripId, @Param("value2") int userId);

    /**
     * This method returns a trip, based on name criteria
     * @param name a String value, representing trip's name
     * @return a Trip object, representing the desired trip
     */
    @Query(value = "SELECT * FROM trips WHERE name =:value", nativeQuery = true)
    Trip findByName(@Param("value") String name);

}
