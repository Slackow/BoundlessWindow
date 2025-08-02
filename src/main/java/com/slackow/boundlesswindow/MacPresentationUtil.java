package com.slackow.boundlesswindow;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public final class MacPresentationUtil {

    public static final long NSApplicationPresentationAutoHideDock = 0x0001L;
    public static final long NSApplicationPresentationAutoHideMenuBar = 0x0004L;

    private static final boolean IS_MAC = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");

    static {
        if (IS_MAC) loadNative("macutil");
    }

    public static void setPresentationOptions(long options) {
        if (IS_MAC) nativeSetPresentation(options);
    }

    /** {x, y, width, height} from NSScreen.mainScreen().visibleFrame */
    public static int[] visibleFrame() {
        return IS_MAC ? nativeVisibleFrame() : null;
    }
    private static native int[] nativeVisibleFrame();

    private static native void nativeSetPresentation(long options);

    private static void loadNative(String base) {
        String res = "/natives/darwin/lib" + base + ".dylib";
        try (InputStream in = MacPresentationUtil.class.getResourceAsStream(res)) {
            if (in == null) return;
            Path tmp = Files.createTempFile(base, ".dylib");
            Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);
            System.load(tmp.toAbsolutePath().toString());
        } catch (IOException ignored) {}
    }

    private MacPresentationUtil() {}
}