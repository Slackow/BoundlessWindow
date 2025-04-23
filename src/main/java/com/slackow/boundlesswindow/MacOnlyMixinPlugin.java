package com.slackow.boundlesswindow;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class MacOnlyMixinPlugin implements IMixinConfigPlugin {

    private static final boolean IS_MAC = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return IS_MAC;
    }

    // Required boilerplate
    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
