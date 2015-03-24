# Warning! #

This page is deprecated.

Now the result file is presented to the user via Java Web Start, see [JNLPandJavaWebStart](JNLPandJavaWebStart.md).

# Compilation by IzPack #
## Introduction ##

[IzPack](http://en.wikipedia.org/wiki/IzPack) is an open source software installer generator that targets the Java platform.

## Configuration ##

Edit the following files in the folder `./wikokit/wiwordik/`:
  * `compile-wiwordik-izpack.bat`
  * `install.xml`
  * `shortcutSpec.xml`

The SQLite database file should exist:
  * `./wikokit/wiwordik/ruwikt20YYMMDD.sqlite`

## Software requirements ##
Install [JavaFX SDK](http://javafx.com/downloads).

## Compilation ##

Compile wiwordik files into one file (e.g. `wiwordik-setup-0.04.jar`):
```
cd wikokit/wiwordik/
compile-wiwordik-izpack.bat
cd ../releases/
java -jar wiwordik-setup-0.04.jar
```

Convert into EXE file (e.g. `wiwordik-setup-0.04.exe`):
  * Add `izpack2exe.exe` into your %PATH%

```
cd wikokit/releases/
izpack2exe --file=wiwordik.bat --file=wiwordik-setup-0.04.jar --output=wiwordik-setup-0.04.exe --launch-file=wiwordik.bat
```

# Previous step #
  * [SQLite](SQLite.md) - How to create an SQLite data file in order to compile wiwordik-setup-X.XX.jar and .exe