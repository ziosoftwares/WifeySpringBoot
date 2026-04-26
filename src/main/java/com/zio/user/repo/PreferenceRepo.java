package com.zio.user.repo;

import com.zio.user.data.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepo extends JpaRepository<Preferences, Long> {
}
