package com.example.warehousedemoback.DTOs;

import java.util.List;

public class NodeDTO {
    int nodeID;
    String nodeName;
    int nodeLVL;
    Boolean isLeaf;
    List<Integer> parentOf;

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
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

    public List<Integer> getParentOf() {
        return parentOf;
    }

    public void setParentOf(List<Integer> parentOf) {
        this.parentOf = parentOf;
    }
}
