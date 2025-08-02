package com.slackow.boundlesswindow;

import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import net.minecraft.client.MinecraftClient;

import java.util.Optional;

@Config(init = Config.InitPoint.PRELAUNCH)
public class BoundlessWindowSRApiConfig implements SpeedrunConfig, BoundlessWindowConfig {

    @Config.Category("main")
    private boolean autoHideDock = true;

    @Config.Category("main")
    private boolean autoHideMenubar = true;

    @Config.Category("main")
    private boolean removeTitlebar = true;

    @Config.Category("main")
    @Config.Access(setter = "setStartupResize")
    private StartupResize startupResize = StartupResize.FILL;

    @Config.Category("startup")
    @Config.Numbers.Whole.Bounds(min = 600, max = 16384)
    @Config.Numbers.TextField
    private int startupWidth = 1512;

    @Config.Category("startup")
    @Config.Numbers.Whole.Bounds(min = 300, max = 16384)
    @Config.Numbers.TextField
    private int startupHeight = 982;

    @Config.Category("startup")
    @Config.Numbers.TextField
    @Config.Numbers.Whole.Bounds(min = -100_000, max = 100_000)
    private int startupX = 0;

    @Config.Category("startup")
    @Config.Numbers.TextField
    @Config.Numbers.Whole.Bounds(min = -100_000, max = 100_000)
    private int startupY = 0;

    {
        BoundlessWindow.config = this;
    }

    private void setStartupResize(StartupResize startupResize) {
        this.startupResize = startupResize;
        Optional.ofNullable(MinecraftClient.getInstance()).ifPresent(client ->
                client.openScreen(client.currentScreen));
    }

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

    @Override
    public String modID() {
        return "boundlesswindow";
    }

    @Override
    public boolean shouldShowCategory(String category) {
        return startupResize == StartupResize.CUSTOM || !"startup".equals(category);
    }

}
