package com.example.csit314.EntityClass;

import java.util.List;

public class Customer {

    private String id;
    private String fullName;
    private String username;
    private String password;
    private int membershipStatus;
    private List<String> requestIds;

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

    public int getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(int membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }
}
