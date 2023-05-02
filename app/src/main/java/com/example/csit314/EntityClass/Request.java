package com.example.csit314.EntityClass;

import java.util.Date;
import java.util.List;

public class Request {
    private String id;
    private String customerId;
    private String professionalId;
    private List<String> serviceIds;
    private List<String> hashTag;
    private double totalPrice;
    private int status; //0 is free, 1 is ongoing, 2 is finished, 3 is cancel
    private Date startDate;
    private Date endDate; //If no one takes the job after end Date, the job will change to status 3 is cancel
    private String location;
    private String title;
    private List<String> titleToArray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public List<String> getHashTag() {
        return hashTag;
    }

    public void setHashTag(List<String> hashTag) {
        this.hashTag = hashTag;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
