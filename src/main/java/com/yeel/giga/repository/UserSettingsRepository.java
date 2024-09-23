package com.yeel.giga.repository;

import com.yeel.giga.model.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
}
