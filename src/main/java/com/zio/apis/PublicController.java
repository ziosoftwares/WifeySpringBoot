package com.zio.apis;

import com.zio.data.api.Response;
import com.zio.data.dto.LoginDTO;
import com.zio.data.entity.User;
import com.zio.service.UserService;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public Response<String> signup(@RequestBody User user) throws ZioException {
        return new Response<>(userService.signup(user), HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public Response<String> signin(@RequestBody LoginDTO loginDTO) throws ZioException {
        return Response.ok(userService.signin(loginDTO));
    }

}
