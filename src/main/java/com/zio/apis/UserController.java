package com.zio.apis;

import com.zio.data.dto.PreferencesDTO;
import com.zio.data.entity.Preferences;
import com.zio.data.entity.User;
import com.zio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    private Long userId = 1L;

    @PostMapping("prefs")
    public ResponseEntity<String> updatePrefs(@RequestBody Preferences preferences) {
        userId = (Long) getRequestingUser();
        preferences.setUserId(userId);
        userService.updatePrefs(preferences);
        return ResponseEntity.ok("done");
    }

    @GetMapping("prefs")
    public ResponseEntity<PreferencesDTO> getPrefs() {
        return ResponseEntity.ok(userService.getPreference(userId));
    }

    private Object getRequestingUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }
}
