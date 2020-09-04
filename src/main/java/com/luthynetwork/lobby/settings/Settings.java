package com.luthynetwork.lobby.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(fluent = true)
@Data
public class Settings {

    public boolean alertas, chat;

    public static Settings defaultSettings() {
        return new Settings(true, true);
    }

}
