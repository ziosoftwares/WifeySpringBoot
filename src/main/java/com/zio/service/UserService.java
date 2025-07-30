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

    public Long createUser(User user) throws ZioException {
        if (userRepo.findByName(user.getName()).isPresent())
            throw new ZioException("USERNAME_EXISTS");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user).getId();
    }

    public void updatePrefs(Preferences preferences) {
        preferenceRepo.save(preferences);
    }

    public PreferencesDTO getPreference(Long userId) {
        Preferences preferences = preferenceRepo.getReferenceById(userId);
        PreferencesDTO dto = new PreferencesDTO();
        dto.setAllergens(preferences.getAllergens().clone());
        dto.setCuisines(preferences.getCuisines().clone());
        dto.setDiets(preferences.getDiets().clone());
        return dto;
    }

    public String login(LoginDTO loginDTO) throws ZioException {
        User user = userRepo.findByName(loginDTO.name).orElseThrow(() -> new ZioException("NO_SUCH_USER"));

        if (!passwordEncoder.matches(loginDTO.password, user.getPassword()))
            throw new ZioException("WRONG_CREDS");

        return jwtUtil.buildToken(user.getId());
    }
}
