package com.inn.cafe.com.restimpl;


import com.inn.cafe.com.constents.CafeConstant;
import com.inn.cafe.com.rest.UserController;
import com.inn.cafe.com.service.UserService;
import com.inn.cafe.com.util.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {
        @Autowired
        private UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
          return userService.signUp(requestMap);
    }
}
