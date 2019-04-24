package com.bogdan.projects.mytrips.model;

import java.time.LocalDate;

/**
 *
 */
public class Impression {
    private String username;
    private LocalDate dateGiven;
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(LocalDate dateGiven) {
        this.dateGiven = dateGiven;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Impression{" +
                "username='" + username + '\'' +
                ", dateGiven=" + dateGiven +
                ", content='" + content + '\'' +
                '}';
    }
}