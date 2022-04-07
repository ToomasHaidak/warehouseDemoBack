package com.example.warehousedemoback.controller;

import com.example.warehousedemoback.DTOs.AllUsersDataDTO;
import com.example.warehousedemoback.DTOs.DefaultUserSettingsDTO;
import com.example.warehousedemoback.DTOs.NodeDTO;
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

    @GetMapping("getNodesOfParentX/{userName}/{parentID}")
    public List<NodeDTO> getNodesOfPArentX(@PathVariable("userName") String userName, @PathVariable("parentID") int parentID) {
        return warehouseService.getNodesOfParentX(userName, parentID);
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

    @PutMapping("registerUser/{nameToRegister}/{userPassword}/{userType}/{representingPerson}")
    public void registerUser(@PathVariable("nameToRegister") String nameToRegister,
                             @PathVariable("userPassword") String userPassword,
                             @PathVariable("userType") String userType,
                             @PathVariable("representingPerson") String representingPerson) {
        warehouseService.registerUser(nameToRegister, userPassword, userType, representingPerson);
    }

    @GetMapping("login/{userName}/{userPassword}")
    public String login(@PathVariable("userName") String userName, @PathVariable("userPassword") String userPassword ) {
        return warehouseService.login(userName, userPassword);
    }

    @GetMapping("api/test")
    public void test() {
        System.out.println("Password toimib");
    }

    @PutMapping("/api/setDefaultFreeItemLimit/{defaultFreeItemLimit}")
    public void setDefaultFreeItemLimit(@PathVariable("defaultFreeItemLimit") int defaultFreeItemLimit) {
        warehouseService.setDefaultFreeItemLimit(defaultFreeItemLimit);
    }

    @PutMapping("/api/setDefaultItemCost/{defaultItemCost}")
    public void setDefaultItemCost(@PathVariable("defaultItemCost") Double defaultItemCost) {
        warehouseService.setDefaultItemCost(defaultItemCost);
    }

    @GetMapping("/api/getDefaultSettings")
    public DefaultUserSettingsDTO getDefaultSettings() {
        return warehouseService.getDefaultSettings();
    }
}
