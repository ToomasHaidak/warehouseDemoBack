package com.example.warehousedemoback.service;

import com.example.warehousedemoback.DTOs.AllUsersDataDTO;
import com.example.warehousedemoback.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public void addNode(String nodeName, int parentID) {
        warehouseRepository.addNode(nodeName, parentID);
    }

    public List<AllUsersDataDTO> getAllUsersData() {
        List<AllUsersDataDTO> userDataList = warehouseRepository.getAllUsersData();
        for(AllUsersDataDTO user: userDataList) {
            int userID = user.getUserID();
            setUserItemCount(user, userID);
            setNrOfSlots(user, userID);
            setTotalItemWeight(user, userID);
            System.out.println(user.getUserName());
            System.out.println(user.getUserType());
            if (user.getUserType().equals("business")) {
                setNrItemsExceedingLimit(user, userID);
                setAmountToPay(user, userID);
            }
        }
        return userDataList;
    }

    public void getAllDataForAUser() {
        warehouseRepository.getAllDataForAUser(1);
    }

    public void setUserItemCount(AllUsersDataDTO user, int userID) {
        user.setItemCount(warehouseRepository.getUserItemCount(userID));
    }

    public void setNrItemsExceedingLimit(AllUsersDataDTO user, int userID) {
        user.setNrItemsExceedingLimit(user.getItemCount() - user.getFreeItemLimit());
    }

    public void setAmountToPay(AllUsersDataDTO user, int userID) {
        int itemsOverLimit = user.getItemCount() - user.getFreeItemLimit();
        if(itemsOverLimit > 0) {
            user.setAmountToPay(itemsOverLimit * 0.01);
        }
    }

    public void setNrOfSlots(AllUsersDataDTO user, int userID) {
        user.setNumberOfSlots(warehouseRepository.getUserNrOfSlots(userID));
    }

    public void setTotalItemWeight(AllUsersDataDTO user, int userID) {
        user.setTotalItemWeight(warehouseRepository.getTotalItemWeight(userID));
    }

    public List<String> getBusinessUserList(){
        return warehouseRepository.getBusinessUserList();
    }

    public void changeBusinessUserSettings(String userName, int freeItemLimit) {
        warehouseRepository.changeBusinessUserSettings(userName, freeItemLimit);
    }
}
