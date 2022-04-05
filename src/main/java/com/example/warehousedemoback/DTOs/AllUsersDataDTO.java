package com.example.warehousedemoback.DTOs;

public class AllUsersDataDTO {

    int userID;
    String userName;
    String userType;
    int itemCount;
    int freeItemLimit;
    int nrItemsExceedingLimit;
    Double amountToPay;
    int numberOfSlots;
    Double totalItemWeight;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getFreeItemLimit() {
        return freeItemLimit;
    }

    public void setFreeItemLimit(int freeItemLimit) {
        this.freeItemLimit = freeItemLimit;
    }

    public int getNrItemsExceedingLimit() {
        return nrItemsExceedingLimit;
    }

    public void setNrItemsExceedingLimit(int nrItemsExceedingLimit) {
        this.nrItemsExceedingLimit = nrItemsExceedingLimit;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public Double getTotalItemWeight() {
        return totalItemWeight;
    }

    public void setTotalItemWeight(Double totalItemWeight) {
        this.totalItemWeight = totalItemWeight;
    }
}
