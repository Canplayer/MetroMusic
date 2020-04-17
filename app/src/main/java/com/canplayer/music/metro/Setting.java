package com.canplayer.music.metro;

public class Setting {
    public enum GlobalPageTheme {
        Light,
        Black,
        WithAndroid;
    }
    static GlobalPageTheme globalPageTheme = GlobalPageTheme.WithAndroid;

    public static GlobalPageTheme getGlobalPageTheme() {
        return globalPageTheme;
    }

    public static void setGlobalPageTheme(GlobalPageTheme globalPageTheme) {
        Setting.globalPageTheme = globalPageTheme;
    }
}
