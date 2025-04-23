package com.slackow.boundlesswindow.mixin;

import com.slackow.boundlesswindow.WindowControlServer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Final private Window window;

    @Unique private WindowControlServer windowControlServer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) throws IOException {
        windowControlServer = new WindowControlServer();
        windowControlServer.init();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo info) {
        windowControlServer.tick(window);
    }

}
