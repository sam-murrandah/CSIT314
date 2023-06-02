package com.example.csit314.EntityClass;

import java.util.Date;
import java.util.List;

public class Request {
    private String id;
    private String customerId;
    private String professionalId;
    private String description;
    private int hashTag;
    private double salary;
    private int estimatedHours;
    private int status; //0 is free, 1 is ongoing, 2 is finished, 3 is cancel
    private String location;
    private String title;
    private List<String> titleToArray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHashTag() {
        return hashTag;
    }

    public void setHashTag(int hashTag) {
        this.hashTag = hashTag;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTitleToArray() {
        return titleToArray;
    }

    public void setTitleToArray(List<String> titleToArray) {
        this.titleToArray = titleToArray;
    }
}
