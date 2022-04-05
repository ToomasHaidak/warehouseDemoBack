package com.example.warehousedemoback.controller;

import com.example.warehousedemoback.DTOs.AllUsersDataDTO;
import com.example.warehousedemoback.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("insert/{nodeName}/{parentID}")
    public void addNode(@PathVariable("nodeName") String nodeName, @PathVariable("parentID") int parentID) {
        warehouseService.addNode(nodeName, parentID);
    }

    @GetMapping("getAllDataForAUser")
    public void getAllDataForAUser() {
        warehouseService.getAllDataForAUser();
    }

    @GetMapping("getAllUsersData")
    public List<AllUsersDataDTO> getAllUsersData() {
        return warehouseService.getAllUsersData();
    }

    @GetMapping("getBusinessUserList")
    public List<String> getBusinessUserList() {
        return warehouseService.getBusinessUserList();
    }

    @PutMapping("changeBusinessUserSettings/{businessName}/{maxFreeItems}")
    public void changeBusinessUserSetting(@PathVariable("businessName") String userName, @PathVariable("maxFreeItems") int freeItemLimit) {
        warehouseService.changeBusinessUserSettings(userName, freeItemLimit);
    }
}
