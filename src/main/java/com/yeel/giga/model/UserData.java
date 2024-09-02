package com.yeel.giga.model;

import com.yeel.giga.enums.UserRole;
import com.yeel.giga.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserData {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private UserStatus userStatus;
    @OneToOne
    private UserSettings userSettings;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserData updateSettings(UserSettings userSettings) {
        Long id = userSettings.getId();
        this.userSettings = userSettings;
        this.userSettings.setId(id);

        return this;
    }
}
