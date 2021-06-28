Software Engineering Project 2020-2021
======================================

Authors
-------
- Giulio Occhipinti
- Chiara Falconieri
- Roberto Ferri

Features
--------
- Complete rules
- CLI
- GUI
- Socket
- **Advanced Feature**: Multiple games
- **Advanced Feature**: Local game
- **Advanced Feature**: Persistence

Requirements
------------
OpenJDK 11: download for all platforms available at https://adoptopenjdk.net/

How to run - Client
-------------------

### All platforms

CLI: `$ java -jar Client.jar -cli`

GUI: `$ java -jar Client.jar`

### Windows specific

CLI:

In order to display UTF-8 characters correctly, either run the CLI command in a PowerShell window, or follow these steps:

* steps on how to enable utf on windows *

then run start_cli.bat inside the deliverables folder

GUI: simply double-click on the Client.jar icon

How to run - Server
------------------
On any platform, run the jar with the following command:

`$ java -jar Server.jar [-p port_number]`

If the port number is not specified, it defaults to 2000.
