package com.zio.service;

import com.zio.data.entity.Preferences;
import com.zio.data.entity.User;
import com.zio.repo.PreferenceRepo;
import com.zio.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PreferenceRepo preferenceRepo;

    public Long createUser(User user) {
        return userRepo.save(user).getId();
    }

    public void updatePrefs(Preferences preferences) {
        preferenceRepo.save(preferences);
    }
}
