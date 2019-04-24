package com.bogdan.projects.mytrips.model;

import java.sql.Blob;

/**
 *
 */
public class Photo {
    private Blob image;
    private String description;

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "image=" + image +
                ", description='" + description + '\'' +
                '}';
    }
}