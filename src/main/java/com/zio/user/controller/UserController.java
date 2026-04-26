package com.zio.user.controller;

import com.zio.common.data.api.Response;
import com.zio.user.data.PreferencesDTO;
import com.zio.user.data.entity.Preferences;
import com.zio.user.service.UserService;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    private Long userId = -1L;

    @GetMapping("verify")
    public Response<Boolean> verify() throws ZioException {
        userId = SessionManager.getUserId();
        return Response.ok(userService.verify(userId));
    }

    @PostMapping("prefs")
    public ResponseEntity<String> updatePrefs(@RequestBody Preferences preferences) throws ZioException {
        userId = SessionManager.getUserId();
        preferences.setUserId(userId);
        userService.updatePrefs(preferences);
        return ResponseEntity.ok("done");
    }

    @GetMapping("prefs")
    public Response<PreferencesDTO> getPrefs() throws ZioException {
        userId = SessionManager.getUserId();
        return Response.ok(userService.getPreference(userId));
    }
}
