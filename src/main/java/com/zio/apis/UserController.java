package com.zio.apis;

import com.zio.data.entity.Preferences;
import com.zio.data.entity.User;
import com.zio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return ResponseEntity.ok("done" + userService.createUser(user));
    }

    @PostMapping("prefs")
    public ResponseEntity<String> updatePrefs(@RequestBody Preferences preferences) {
        userService.updatePrefs(preferences);
        return ResponseEntity.ok("done");
    }
}
