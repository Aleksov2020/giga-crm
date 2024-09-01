package com.yeel.giga.controller;

import com.yeel.giga.model.UserData;
import com.yeel.giga.model.UserSettings;
import com.yeel.giga.service.EncryptionService;
import com.yeel.giga.service.UserDataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final EncryptionService encryptionService;
    private final UserDataService userDataService;

    @GetMapping("settings")
    public UserSettings getUserSettings() {
        return null;
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
