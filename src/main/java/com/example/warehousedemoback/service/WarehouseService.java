package com.example.warehousedemoback.service;

import com.example.warehousedemoback.DTOs.AllUsersDataDTO;
import com.example.warehousedemoback.DTOs.DefaultUserSettingsDTO;
import com.example.warehousedemoback.DTOs.NodeDTO;
import com.example.warehousedemoback.DTOs.QueryResultDTO;
import com.example.warehousedemoback.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired LoginService loginService;

    public void registerUser(String nameToRegister, String userPassword, String userType, String representingPerson) {
        if(userType.equals("Private")){
        String encodedPassword = encodePassword(userPassword);
        warehouseRepository.registerUser(nameToRegister, encodedPassword, userType);
        } else {
            int freeItemLimit = warehouseRepository.getDefaultFreeItemLimit();
            warehouseRepository.registerUser(nameToRegister, "N/A", userType);
            warehouseRepository.setFreeItemLimitForBusiness(nameToRegister, freeItemLimit);
            int businessID = warehouseRepository.getBusinessID(nameToRegister);
            warehouseRepository.setRepresentingBusinessID(representingPerson, businessID);
        }
    }

    public String login(String userName, String userPassword, String userType) {
        System.out.println(warehouseRepository.getUserType(userName));
        if(!warehouseRepository.getUserType(userName).equals(userType) && warehouseRepository.ifUserRepresentsBusiness(userName)<1){
            throw new RuntimeException("Vale kasutaja tüüp");
        }
        if(validatePassword(userName, userPassword)) {
            return loginService.login(userName);
        } else {
            throw new RuntimeException("Vale salasõna");
        }
    }

    public String encodePassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }

    public boolean validatePassword(String userName, String userPassword) {
        String encodedPassword = warehouseRepository.getEncodedPassword(userName);
        return passwordEncoder.matches(userPassword, encodedPassword);

    }

    public void addNode(String nodeName, int parentID) {
        warehouseRepository.addNode(nodeName, parentID);
    }

    public List<AllUsersDataDTO> getAllUsersData() {
        List<AllUsersDataDTO> userDataList = warehouseRepository.getAllUsersData();
        try {
            for (AllUsersDataDTO user : userDataList) {
                int userID = user.getUserID();
                setUserItemCount(user, userID);
                setNrOfSlots(user, userID);
                setTotalItemWeight(user, userID);
                if (user.getUserType().equals("Business")) {
                    setNrItemsExceedingLimit(user);
                    setAmountToPay(user);
                }
            }
        } catch(Exception e) {

        }
        return userDataList;
    }

    public List<NodeDTO> getNodesOfParentX(String userName, int parentID) {
        int userID = warehouseRepository.getUserID(userName);
        List<QueryResultDTO> queryResult = warehouseRepository.getNodesOfParentX(userID, parentID);
        List<NodeDTO> nodesList = new ArrayList<NodeDTO>();
        for(QueryResultDTO node : queryResult) {
            NodeDTO nodeInList = convertQueryResultElementToNodeListElement(userID, node);
            nodesList.add(nodeInList);
        }
        return nodesList;
    }

    public NodeDTO convertQueryResultElementToNodeListElement(int userID, QueryResultDTO node) {
        NodeDTO nodeInList = new NodeDTO();
        nodeInList.setNodeID(node.getNodeID());
        nodeInList.setNodeName(node.getNodeName());
        nodeInList.setNodeLVL(node.getNodeLVL());
        nodeInList.setLeaf(node.getLeaf());
        nodeInList.setParentOf(getIDOfChildrenOfNodeX(userID, node.getNodeID()));
        return nodeInList;
    }

    public List<Integer> getIDOfChildrenOfNodeX(int userID, int nodeID) {
        return warehouseRepository.getIDOfChildrenOfNodeX(userID, nodeID);
    }

    public void setUserItemCount(AllUsersDataDTO user, int userID) {
        user.setItemCount(warehouseRepository.getUserItemCount(userID));
    }

    public void setNrItemsExceedingLimit(AllUsersDataDTO user) {
        System.out.println(user.getItemCount() + " - " + user.getFreeItemLimit());
        user.setNrItemsExceedingLimit(user.getItemCount() - user.getFreeItemLimit());
    }

    public void setAmountToPay(AllUsersDataDTO user) {
        int itemsOverLimit = user.getItemCount() - user.getFreeItemLimit();
        if(itemsOverLimit > 0) {
            user.setAmountToPay(itemsOverLimit * warehouseRepository.getDefaultItemCost());
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

    public void setDefaultFreeItemLimit(int defaultFreeItemLimit) {
        warehouseRepository.setDefaultFreeItemLimit(defaultFreeItemLimit);
    }

    public void setDefaultItemCost(Double defaultItemCost) {
        warehouseRepository.setDefaultItemCost(defaultItemCost);
    }

    public DefaultUserSettingsDTO getDefaultSettings() {
        DefaultUserSettingsDTO defaultUserSettingsDTO = new DefaultUserSettingsDTO();
        defaultUserSettingsDTO.setDefaultFreeItemLimit(warehouseRepository.getDefaultFreeItemLimit());
        defaultUserSettingsDTO.setDefaultItemCost(warehouseRepository.getDefaultItemCost());
        return defaultUserSettingsDTO;
    }

}
