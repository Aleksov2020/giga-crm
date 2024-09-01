package com.yeel.giga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionService {
    private final PasswordEncoder passwordEncoder;

    public String encryptText(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public boolean checkPassword(String plainText, String encryptedText) {
        return passwordEncoder.matches(plainText, encryptedText);
    }
}
