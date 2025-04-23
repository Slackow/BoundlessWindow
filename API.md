# API Details

BoundlessWindow opens a socket server bound to a port picked by the OS, this port is logged to `latest.log`, and written to a file under the `minecraft` folder as `boundless_port.txt`

This socket server can be connected to via `localhost:[port]`, and once connected the server expects commands in the form `get` or `set <x> <y> <width> <height>`. All parameters are optional by passing in a dash instead of a number, and the server will reply with the position and bounds of the window after a command.

If the position is unspecified, it will resize to keep the center of the window in the same position.

EX:

```zsh
$ nc localhost 59918
get
200 200 2560 1440
set - - 320 16384
1320 -7272 320 16384
set - - 2560 1440
200 200 2560 1440
set 400 0 2560 1440
400 0 2560 1440
^C
$
```

here's an example of directly reading the file, in this case for an instance named `SeedQueue`
```zsh
$ nc localhost `cat ~/Library/Application\ Support/PrismLauncher/instances/SeedQueue/minecraft/boundless_port.txt`
```

Most languages have APIs for directly talking to sockets, so you should generally prefer those over using the nc command, but this is a good way to test that it's working.