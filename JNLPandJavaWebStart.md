# JNLP and Java Web Start #
## Introduction ##
todo ...

## SQLite JAR file ##

Let's create .jar file from Wiktionary SQLite database, see [Creating a JAR File](http://www.lkn.ei.tum.de/arbeiten/faq/man/JAVA-tutorial/jar/basics/build.html).

```
cd wiwordik/sqlite
jar cf0M enwikt20111008.sqlite.jar enwikt20111008.sqlite
or
"C:\Program Files\Java\jdk1.7.0\bin\jar" cf0M enwikt20111008.sqlite.jar enwikt20111008.sqlite
```

Add result .jar file to the NetBeans project `wiwordik`.

Also add `sqlite-jdbc-X.X.X.jar` to the NetBeans project `wiwordik`.

## Java source code ##

Turn on two variables in the file `wikokit\wiwordik\src\wiwordik\WConstants.java`:
```
public static Boolean IS_RELEASE = true;
public static Boolean IS_SQLITE = true;
```

## Ant (build.xml) ##

Update the version number and the name of the SQLite file in the `build.xml`, e.g.:
```
<property name="version" value="0.05"/>
<property name="sqlite.file" value="ruwikt20101101"/>
```

Run ant target: package-for-store.

## jarsigner ##

See [How to sign JAR files](http://wiki.plexinfo.net/index.php?title=How_to_sign_JAR_files).

Run `wikokit\wiwordik\sqlite\run_jarsigner.bat` to sign .jar file.


## JNLP file ##

```
cp ./wiwordik/store/wiwordik-0.06-ruwikt20110413.jar wikokit/wiwordik/jnlp/
cd wiwordik/jnlp
javaws -import wiwordik-ru_test.jnlp
javaws -viewer # Run Offline: wiwordik-ru_test
```

# English Wiktionary #

Now .jar file with the compressed SQLite database of the English Wiktionary can not be uploaded to the Google Code, since the file has size about 350 MBytes, but Google Code has a constraint - maximum file 200 MByte. You can vote to "Support huge downloads" http://code.google.com/p/support/issues/detail?id=3148&colspec=ID%20Type%20Status%20Milestone%20Priority%20Stars%20Owner%20Summary

So, at this moment You can play via webstart only with the database of Russian Wiktionary (size of compressed SQLite is 75 MBytes only).

# Previous step #
  * [SQLite](SQLite.md) - How to create an SQLite data file in order to compile wiwordik-setup-X.XX.jar and .exe