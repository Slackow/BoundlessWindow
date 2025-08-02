package com.slackow.boundlesswindow;

import net.fabricmc.loader.api.FabricLoader;

public class BoundlessWindow {

    public static BoundlessWindowConfig config = !FabricLoader.getInstance().isModLoaded("speedrunapi") ?
            BoundlessWindowFileConfig.load(true) : null;

}
