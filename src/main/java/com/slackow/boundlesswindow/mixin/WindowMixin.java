package com.slackow.boundlesswindow.mixin;

import com.mojang.blaze3d.platform.Window;
import com.slackow.boundlesswindow.BoundlessWindow;
import org.lwjgl.sdl.SDLVideo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Window.class)
public class WindowMixin {
    @ModifyArg(method = "createWindow", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/device/GpuBackend;createWindow(Ljava/lang/String;IIJ)J"), index = 3)
    private long addFlags(long flags) {
        if (BoundlessWindow.config.removeTitlebar()) {
            flags |= SDLVideo.SDL_WINDOW_BORDERLESS | SDLVideo.SDL_WINDOW_RESIZABLE;
        }
        return flags;
    }
}
