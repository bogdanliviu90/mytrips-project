package com.bogdan.projects.mytrips.model;


import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;


/**
 * Class Trip represents the model of a trip/ the trips that the users will store to their accounts
 *
 * @author Bogdan Butuza
 */
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "datefrom")
    private LocalDate dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateto")
    private LocalDate dateTo;

    private String impressions;

    @Column(name = "firstphoto")
    private String firstPhotoLocation;

    @Column(name = "firsttitle")
    private String firstPhotoTitle;

    @Column(name = "firstdescr")
    private String firstPhotoDescription;

    @Column(name = "secondphoto")
    private String secondPhotoLocation;

    @Column(name = "secondtitle")
    private String secondPhotoTitle;

    @Column(name = "seconddescr")
    private String secondPhotoDescription;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getImpressions() {
        return impressions;
    }

    public void setImpressions(String impressions) {
        this.impressions = impressions;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstPhotoLocation() {
        return firstPhotoLocation;
    }

    public void setFirstPhotoLocation(String firstPhotoLocation) {
        this.firstPhotoLocation = firstPhotoLocation;
    }

    public String getFirstPhotoDescription() {
        return firstPhotoDescription;
    }

    public void setFirstPhotoDescription(String firstPhotoDescription) {
        this.firstPhotoDescription = firstPhotoDescription;
    }

    public String getSecondPhotoLocation() {
        return secondPhotoLocation;
    }

    public void setSecondPhotoLocation(String secondPhotoLocation) {
        this.secondPhotoLocation = secondPhotoLocation;
    }

    public String getSecondPhotoDescription() {
        return secondPhotoDescription;
    }

    public void setSecondPhotoDescription(String secondPhotoDescription) {
        this.secondPhotoDescription = secondPhotoDescription;
    }

    public String getFirstPhotoTitle() {
        return firstPhotoTitle;
    }

    public void setFirstPhotoTitle(String firstPhotoTitle) {
        this.firstPhotoTitle = firstPhotoTitle;
    }

    public String getSecondPhotoTitle() {
        return secondPhotoTitle;
    }

    public void setSecondPhotoTitle(String secondPhotoTitle) {
        this.secondPhotoTitle = secondPhotoTitle;
    }
}