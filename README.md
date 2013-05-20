# MapTools #

MapTools is a small Bukkit plugin to provide additional informations from a
Minecraft Server to a map rendered with tools such as
[mapcrafter](http://github.com/m0r13/mapcrafter). The plugin writes every few
seconds a JSON-File with informations about the players (for example their position) 
to the filesystem.

MapTools is free software and available under the GPL license.

## Configuration ##

You can specify a few options in the configuration file (`config.yml`):

`markerFile`

This is the output filename of the JSON-File (relative to the server
directory). It defaults to `markers.json`.

`interval`

This is the interval to write the file (in seconds). It defaults to 5 seconds.
