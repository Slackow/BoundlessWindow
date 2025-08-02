package com.slackow.boundlesswindow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// For versions that don't have speedrunapi
public class BoundlessWindowFileConfig implements BoundlessWindowConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private boolean autoHideDock = true;

    private boolean autoHideMenubar = true;

    private boolean removeTitlebar = true;

    private StartupResize startupResize = StartupResize.FILL;

    private int startupWidth = 1512;

    private int startupHeight = 982;

    private int startupX = 0;

    private int startupY = 0;


    @Override
    public boolean autoHideDock() {
        return autoHideDock;
    }

    @Override
    public boolean autoHideMenubar() {
        return autoHideMenubar;
    }

    @Override
    public boolean removeTitlebar() {
        return removeTitlebar;
    }

    @Override
    public StartupResize startupResize() {
        return startupResize;
    }

    @Override
    public int startupWidth() {
        return startupWidth;
    }

    @Override
    public int startupHeight() {
        return startupHeight;
    }

    @Override
    public int startupX() {
        return startupX;
    }

    @Override
    public int startupY() {
        return startupY;
    }

    public static BoundlessWindowFileConfig load(boolean saveIfNotExist) {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("boundlesswindow.json");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException ignored) {
        }
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                return GSON.fromJson(reader, BoundlessWindowFileConfig.class);
            } catch (IOException ignored) {
            }
        }
        BoundlessWindowFileConfig config = new BoundlessWindowFileConfig();
        if (!saveIfNotExist) return config;
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(config, writer);
        } catch (IOException ignored) {
        }
        return config;
    }
}
