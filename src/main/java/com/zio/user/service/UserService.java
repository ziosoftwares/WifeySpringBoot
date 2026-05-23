package com.zio.user.service;

import com.zio.common.data.api.Error;
import com.zio.user.data.LoginDTO;
import com.zio.user.data.PreferencesDTO;
import com.zio.user.data.UserInfo;
import com.zio.user.data.entity.Author;
import com.zio.user.data.entity.Preferences;
import com.zio.user.data.entity.User;
import com.zio.user.repo.AuthorRepo;
import com.zio.user.repo.PreferenceRepo;
import com.zio.user.repo.UserRepo;
import com.zio.common.security.JwtUtil;
import com.zio.common.util.ZioException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    PreferenceRepo preferenceRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserInfo signin(LoginDTO loginDTO) throws ZioException {
        User user = userRepo.findByEmail(loginDTO.email).orElseThrow(() -> new ZioException(new Error(403, 1, "WRONG_CREDS")));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new ZioException(new Error(403, 1, "WRONG_CREDS"));
        return new UserInfo(user.getId(), user.getUserName(), jwtUtil.buildToken(user.getId()));
    }

    public UserInfo signup(LoginDTO loginDTO) throws ZioException {
        if (userRepo.findByEmail(loginDTO.getEmail()).isPresent())
            throw new ZioException(new Error(409, 1, "EMAIL_EXISTS"));
        if (userRepo.findByUserName(loginDTO.getUserName()).isPresent())
            throw new ZioException(new Error(409, 2, "USERNAME_EXISTS"));

        loginDTO.setPassword(passwordEncoder.encode(loginDTO.password));
        User savedUser = userRepo.save(new User(loginDTO));
        authorRepo.save(new Author(savedUser.getId(), savedUser.getUserName()));
        preferenceRepo.save(new Preferences(savedUser.getId()));
        return new UserInfo(savedUser.getId(), savedUser.getUserName(), jwtUtil.buildToken(savedUser.getId()));
    }

    public void updatePrefs(Preferences preferences) {
        preferenceRepo.save(preferences);
    }

    public PreferencesDTO getPreference(Long userId) throws ZioException {
        Preferences preferences = preferenceRepo.findById(userId).orElse(new Preferences());
        PreferencesDTO dto = new PreferencesDTO();
        dto.setAllergens(preferences.getAllergens());
        dto.setCuisines(preferences.getCuisines());
        dto.setDiet(preferences.getDiet());

        return dto;
    }

    public Boolean verify(Long userId) {
        return userRepo.existsById(userId);
    }

    public User getUserById(Long id) throws ZioException {
        return userRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, 1, "NO_SUCH_USER")));
    }
}
