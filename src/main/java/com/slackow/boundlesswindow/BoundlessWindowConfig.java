package com.slackow.boundlesswindow;

public interface BoundlessWindowConfig {
    boolean autoHideDock();

    boolean autoHideMenubar();

    boolean removeTitlebar();

    StartupResize startupResize();

    int startupWidth();

    int startupHeight();

    int startupX();

    int startupY();


    enum StartupResize {
        FILL, OFF, CUSTOM
    }
}
