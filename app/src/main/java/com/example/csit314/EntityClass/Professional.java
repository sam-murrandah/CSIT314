package com.example.csit314.EntityClass;

import java.util.List;

public class Professional {
    private String id;
    private String fullName;
    private String username;
    private String password;
    private List<String> requestIds; //The list of request that this professional has accepted
    private int totalRequestsDone; //the total requests that professional had done
    private double calcStar; //the total rating star from customer */5

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }

    public int getTotalRequestsDone() {
        return totalRequestsDone;
    }

    public void setTotalRequestsDone(int totalRequestsDone) {
        this.totalRequestsDone = totalRequestsDone;
    }

    public double getCalcStar() {
        return calcStar;
    }

    public void setCalcStar(double calcStar) {
        this.calcStar = calcStar;
    }
}
