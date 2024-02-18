package com.inn.cafe.com.serviceImpl;

import com.inn.cafe.com.POJO.User;
import com.inn.cafe.com.constents.CafeConstant;
import com.inn.cafe.com.dao.UserDao;
import com.inn.cafe.com.service.UserService;
import com.inn.cafe.com.util.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

// we will write here the actual business logic
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    // DAo is used to interact with the database
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseEntity<String>signUp(Map<String, String> requestMap) {
        log.info("Inside the signUp method of UserServiceImpl {}" ,requestMap);
        try{
            if (validateSignUpMap(requestMap)){
                User user = userDao.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)){
                    userDao.save(getUserFromRequestMap(requestMap));
                    return CafeUtils.getResponseEntity(CafeConstant.USER_SIGNUP_SUCCESS , HttpStatus.OK);
                }
                else {
                    return CafeUtils.getResponseEntity(CafeConstant.USER_ALREADY_EXISTS , HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA , HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.USER_SIGNUP_FAILED , HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validateSignUpMap(Map<String , String> requestMap){
    return (requestMap.containsKey("name") && requestMap.containsKey("email") &&
            requestMap.containsKey("password") && requestMap.containsKey("contact_number"));
    }
    // lets extract the user from the requestMap
    private User getUserFromRequestMap(Map<String,String>requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setContactNumber(requestMap.get("contact_number"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
