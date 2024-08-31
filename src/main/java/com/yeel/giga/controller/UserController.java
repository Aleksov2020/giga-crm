package com.yeel.giga.controller;

import com.yeel.giga.model.UserSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("settings")
    public UserSettings getUserSettings() {
        return null;
    }

    @GetMapping("getToken")
    public String getUserToken() {
        return null;
    }
}
