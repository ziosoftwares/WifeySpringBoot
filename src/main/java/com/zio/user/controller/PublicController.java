package com.zio.user.controller;

import com.zio.user.data.LoginDTO;
import com.zio.user.data.UserInfo;
import com.zio.user.data.entity.User;
import com.zio.user.service.UserService;
import com.zio.common.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health")
    @ResponseStatus(HttpStatus.OK)
    public String getHealth() {
        return "Okay";
    }

    @PostMapping("signup")
    public UserInfo signup(@RequestBody LoginDTO loginDTO) throws ZioException {
        return userService.signup(loginDTO);
    }

    @PostMapping("signin")
    public UserInfo signin(@RequestBody LoginDTO loginDTO) throws ZioException {
        return userService.signin(loginDTO);
    }

}
