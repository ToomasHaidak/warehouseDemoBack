package com.example.warehousedemoback.DTOs;

import java.util.List;

public class NodeHierarchyDTO {
    int nodeID;
    String nodeName;
    int nodeLVL;
    List<NodeHierarchyDTO> parentOf;
}
