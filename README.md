# BoundlessWindow Mod
macOS only fabric mod for 1.14+,
allows resizing windows past monitor bounds by removing the title bar from the top of the window.

This prevents the window from being resized normally or via regular OS functions like the Accessibility API or AppleScript,
so it exposes a custom API to allow external applications/scripts to resize the window

(e.g. [SlackowWall 1.1+](https://github.com/Slackow/SlackowWall))

This is useful to allow for strategies like tall/boat eye measuring on macOS

Devs: read about the API [here](API.md)