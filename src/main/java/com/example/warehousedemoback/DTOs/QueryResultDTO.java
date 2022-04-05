package com.example.warehousedemoback.DTOs;

public class QueryResultDTO {
    int nodeID;
    int parentID;
    int userID;
    String nodeName;
    int nodeLVL;

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNodeLVL() {
        return nodeLVL;
    }

    public void setNodeLVL(int nodeLVL) {
        this.nodeLVL = nodeLVL;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    Boolean isLeaf;
    String serialNumber;
    Double weight;
    Double value;
    String description;


}
