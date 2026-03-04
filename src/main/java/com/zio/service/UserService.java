package com.zio.service;

import com.zio.data.dto.LoginDTO;
import com.zio.data.dto.PreferencesDTO;
import com.zio.data.entity.Preferences;
import com.zio.data.entity.User;
import com.zio.repo.PreferenceRepo;
import com.zio.repo.UserRepo;
import com.zio.security.JwtUtil;
import com.zio.util.ZioException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PreferenceRepo preferenceRepo;

    @Autowired
    private JwtUtil jwtUtil;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ModelMapper mapper = new ModelMapper();

    public String signin(LoginDTO loginDTO) throws ZioException {
        User user = userRepo.findByEmail(loginDTO.email).orElseThrow(() -> new ZioException("NO_SUCH_USER"));
        return jwtUtil.buildToken(user.getId());
    }

    public String signup(User user) throws ZioException {
        if (userRepo.findByEmail(user.getEmail()).isPresent())
            throw new ZioException("EMAIL_EXISTS");
        if (userRepo.findByUserName(user.getUserName()).isPresent())
            throw new ZioException("USERNAME_EXISTS");
        return jwtUtil.buildToken(userRepo.save(user).getId());
    }

    public void updatePrefs(Preferences preferences) {
        preferenceRepo.save(preferences);
    }

    public PreferencesDTO getPreference(Long userId) throws ZioException {
        Preferences preferences = preferenceRepo.findById(userId).orElseThrow(() -> new ZioException("NO_PREFS"));

        System.out.println("preferences = " + preferences);
        PreferencesDTO dto = new PreferencesDTO();
        dto.setAllergens(preferences.getAllergens().clone());
        dto.setCuisines(preferences.getCuisines().clone());
        dto.setDiets(preferences.getDiets().clone());

        return dto;
    }

    public Boolean verify(Long userId) {
        return userRepo.existsById(userId);
    }
}
