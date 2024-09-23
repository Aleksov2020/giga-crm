package com.yeel.giga.controller;

import com.yeel.giga.model.UserData;
import com.yeel.giga.model.UserSettings;
import com.yeel.giga.repository.UserSettingsRepository;
import com.yeel.giga.service.EncryptionService;
import com.yeel.giga.service.UserDataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final EncryptionService encryptionService;
    private final UserDataService userDataService;
    private final UserSettingsRepository userSettingsRepository;

    @GetMapping("settings")
    public UserSettings getUserSettings() {
        return userDataService.find(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        ).getUserSettings();
    }

    @PostMapping("settings")
    public UserSettings getUserSettings(@RequestBody UserSettings userSettings) {
        return userDataService.save(
                userDataService.find(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName()
                ).updateSettings(
                        userSettingsRepository.save(
                                userSettingsRepository
                                        .findById(
                                                userSettings.getId()
                                        ).orElseThrow(
                                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                                        ).update(userSettings)
                        )
                )
        ).getUserSettings();
    }

    @GetMapping("get-token")
    public String getUserToken() {
        UserData userData = userDataService.find(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        );

        return encryptionService.encryptText(userData.getUsername() + userData.getId());
    }
}
