package com.zio.repo;

import com.zio.data.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepo extends JpaRepository<Preferences, Long> {
}
