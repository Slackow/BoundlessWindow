# BoundlessWindow Mod
macOS only fabric mod for 1.14+,
allows resizing windows past monitor bounds by removing the title bar from the top of the window.

This prevents the window from being resized normally or via regular OS functions like the Accessibility API or AppleScript,
so it exposes a custom API to allow external applications/scripts to resize the window

(e.g. [SlackowWall 1.1+](https://github.com/Slackow/SlackowWall))

This is useful to allow for strategies like tall/boat eye measuring on macOS

Devs: read about the API [here](API.md)

Settings are configurable via Speedrun API, if using the mod on a version that does not support Speedrun API, the mod can also be configured via a file found in the instance under `config/boundlesswindow.json`

Default options are as follows:
```json
{
  "autoHideDock": true,
  "autoHideMenubar": true,
  "removeTitlebar": true,
  "startupResize": "FILL",
  "startupWidth": 1512,
  "startupHeight": 982,
  "startupX": 0,
  "startupY": 0
}
```

For Boateye to work "Remove Titlebar" MUST be on (the titlebar is what would otherwise limit your window's size)

valid values for `startupResize` are `FILL`, `OFF`, or `CUSTOM`, the startup dimensions are only used if this value is set to `CUSTOM`
