package com.example.warehousedemoback.repository;

import com.example.warehousedemoback.DTOs.AllUsersDataDTO;
import com.example.warehousedemoback.DTOs.QueryResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WarehouseRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void registerUser(String nameToRegister, String encodedPassword, String userType) {
        String sql = "INSERT INTO users (user_name, encoded_password, user_type) VALUES (:un, :ep, :ut)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", nameToRegister);
        paramMap.put("ep", encodedPassword);
        paramMap.put("ut", userType);
        jdbcTemplate.update(sql, paramMap);
    }

    public void setFreeItemLimitForBusiness(String businessName, int freeItemLimit) {
        String sql = "UPDATE users SET free_item_limit = :fil WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", businessName);
        paramMap.put("fil", freeItemLimit);
        jdbcTemplate.update(sql, paramMap);
    }

    public int getUserID(String userName) {
        String sql = "SELECT user_id FROM users WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", userName);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public int getBusinessID(String businessName) {
        String sql = "SELECT user_id FROM users WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", businessName);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public void setRepresentingBusinessID(String representingPerson, int businessID) {
        String sql = "UPDATE users SET rep_business_user_id = :rpuid WHERE user_name = :rp";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("rpuid", businessID);
        paramMap.put("rp", representingPerson);
        jdbcTemplate.update(sql, paramMap);
    }

    public String getEncodedPassword(String userName) {
        String sql = "SELECT encoded_password FROM users WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", userName);
        return jdbcTemplate.queryForObject(sql, paramMap, String.class);
    }

    public void addNode (String nodeName, int parentID) {
        String sql = "INSERT INTO nodes (node_name, parent_id) VALUES (:nn, :pid)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("nn", nodeName);
        paramMap.put("pid", parentID);
        jdbcTemplate.update(sql, paramMap);
    }

    public List<AllUsersDataDTO> getAllUsersData() {
        String sql = "Select * FROM users";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new WarehouseRepository.AllUsersDataDTORowMapper());
    }

    public static class AllUsersDataDTORowMapper implements RowMapper<AllUsersDataDTO> {

        @Override
        public AllUsersDataDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            AllUsersDataDTO result = new AllUsersDataDTO();
            result.setUserID(resultSet.getInt("user_id"));
            result.setUserName(resultSet.getString("user_name"));
            result.setUserType(resultSet.getString("user_type"));
            result.setFreeItemLimit(resultSet.getInt("free_item_limit"));
            return result;
        }
    }

    public List<QueryResultDTO> getNodesOfParentX(int userID, int nodeLVL) {
        String sql = "SELECT * FROM nodes WHERE user_id = :uid AND parent_id = :nl";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", userID);
        paramMap.put("nl", nodeLVL);
        return jdbcTemplate.query(sql, paramMap, new WarehouseRepository.QueryResultDTORowMapper());
    }

    public static class QueryResultDTORowMapper implements RowMapper<QueryResultDTO> {

        @Override
        public QueryResultDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            QueryResultDTO result = new QueryResultDTO();
            result.setNodeID(resultSet.getInt("node_id"));
            result.setParentID(resultSet.getInt("parent_id"));
            result.setUserID(resultSet.getInt("user_id"));
            result.setNodeName(resultSet.getString("node_name"));
            result.setNodeLVL(resultSet.getInt("node_lvl"));
            result.setLeaf(resultSet.getBoolean("is_leaf"));
            result.setSerialNumber(resultSet.getString("serialnumber"));
            result.setWeight(resultSet.getDouble("weight"));
            result.setValue(resultSet.getDouble("value"));
            result.setDescription(resultSet.getString("description"));
            return result;
        }
    }

    public List<Integer> getIDOfChildrenOfNodeX(int userID, int parentID) {
        String sql = "SELECT node_id FROM nodes WHERE user_id = :uid AND parent_id = :pi";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", userID);
        paramMap.put("pi", parentID);
        return jdbcTemplate.queryForList(sql, paramMap, Integer.class);
    }

    public int getUserItemCount(int userID) {
        String sql = "SELECT COUNT(*) FROM nodes WHERE user_id = :uid AND is_leaf = true";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", userID);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public int getUserNrOfSlots(int userID) {
        String sql = "SELECT COUNT(*) FROM nodes WHERE user_id = :uid AND is_leaf = false";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", userID);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public Double getTotalItemWeight(int userID) {
        String sql = "SELECT SUM(weight) FROM nodes WHERE user_id = :uid AND is_leaf = true";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", userID);
        return jdbcTemplate.queryForObject(sql, paramMap, Double.class);
    }

    public List<String> getBusinessUserList() {
        String sql = "SELECT user_name FROM users WHERE user_type = :ut";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ut", "Business");
        return jdbcTemplate.queryForList(sql, paramMap, String.class);
    }

    public void changeBusinessUserSettings(String userName, int freeItemLimit) {
        String sql = "UPDATE users SET free_item_limit = :fil WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fil", freeItemLimit);
        paramMap.put("un", userName);
        jdbcTemplate.update(sql,paramMap);
    }

    public void setDefaultFreeItemLimit(int defaultFreeItemLimit) {
        String sql = "UPDATE defaultusersettings SET default_free_item_limit = :dfil";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dfil", defaultFreeItemLimit);
        jdbcTemplate.update(sql,paramMap);
    }

    public void setDefaultItemCost(Double defaultItemCost) {
        String sql = "UPDATE defaultusersettings SET default_itemcost_eur = :dic";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dic", defaultItemCost);
        jdbcTemplate.update(sql,paramMap);
    }

    public int getDefaultFreeItemLimit() {
        String sql = "SELECT default_free_item_limit FROM defaultusersettings";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForObject(sql,paramMap, Integer.class);
    }

    public Double getDefaultItemCost() {
        String sql = "SELECT default_itemcost_eur FROM defaultusersettings";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForObject(sql,paramMap, Double.class);
    }

    public String getUserType(String userName) {
        String sql = "SELECT user_type FROM users WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", userName);
        return jdbcTemplate.queryForObject(sql,paramMap, String.class);
    }

    public int ifUserRepresentsBusiness(String userName) {
        String sql = "SELECT rep_business_user_id FROM users WHERE user_name = :un";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("un", userName);
        return jdbcTemplate.queryForObject(sql,paramMap, Integer.class);
    }



//    public void getAllDataForAUser() {
//        String sql =
//                "WITH RECURSIVE nodes_tree AS (" +
//                "SELECT n1.node_id," +
//                        "n1.parent_id," +
//                        "n1.node_name," +
//                        "0 as node_level," +
//                        "n1.node_id::VARCHAR as n_id" +
//                        "FROM nodes n1 WHERE n1.node_id = 1 UNION ALL" +
//                        "SELECT n2.node_id," +
//                        "n2.parent_id," +
//                        "n2.node_name," +
//                        "node_level+1," +
//                        "n_id::VARCHAR || ',' || n2.node_id::VARCHAR" +
//                        "FROM nodes n2 JOIN nodes_tree nt ON nt.node_id = n2.parent_id)" +
//                        "SELECT * FROM nodes_tree";
//        Map<String, Object> paramMap = new HashMap<>();
//        jdbcTemplate.update(sql, paramMap);
//    }
}
