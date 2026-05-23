package com.zio.user.controller;

import com.zio.common.data.api.Response;
import com.zio.user.data.PreferencesDTO;
import com.zio.user.data.entity.Preferences;
import com.zio.user.service.UserService;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("verify")
    public Response<Boolean> verify() throws ZioException {
        return Response.ok(userService.verify(SessionManager.getUserId()));
    }

    @PostMapping("prefs")
    public ResponseEntity<Void> updatePrefs(@RequestBody Preferences preferences) throws ZioException {
        preferences.setUserId(SessionManager.getUserId());
        userService.updatePrefs(preferences);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("prefs")
    public Response<PreferencesDTO> getPrefs() throws ZioException {
        return Response.ok(userService.getPreference(SessionManager.getUserId()));
    }
}
