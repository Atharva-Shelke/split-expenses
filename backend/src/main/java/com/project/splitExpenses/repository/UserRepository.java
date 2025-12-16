//package com.project.splitExpenses.repository;
//
//import java.util.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.project.splitExpenses.SplitExpensesController;
//import com.project.splitExpenses.util.SqlProperty;
//import com.project.splitExpenses.dto.*;
//
//@Repository
//public class UserRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
//
//
//    public UserRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public void createUser(String name) {
//        jdbcTemplate.update(SqlProperty.get("createUser"), name);
//    }
//    
//    public List<UserDTO> getUsers() {
//        return jdbcTemplate.query(
//            SqlProperty.get("getUsers"),
//            (rs, rowNum) -> new UserDTO(
////                rs.getInt("ID"),
////                rs.getString("NAME")
//            )
//        );
//    }
////    public List<String> getUsers() {
////    	logger.debug("urrrrs");
////        return jdbcTemplate.queryForList(SqlProperty.get("getUsers"), String.class);
////    }
//    
//    public String getUserByID(int id) {
//        return jdbcTemplate.queryForObject(SqlProperty.get("getUserByID"), String.class, id);
//    }
//    
//    public void deleteUser(int id) {
//    	jdbcTemplate.update(SqlProperty.get("deleteUser"),id);
//    }
//}
