package com.zio.apis;

import com.zio.data.api.Response;
import com.zio.data.dto.PreferencesDTO;
import com.zio.data.entity.Preferences;
import com.zio.data.entity.User;
import com.zio.service.UserService;
import com.zio.util.SessionManager;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
