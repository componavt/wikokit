::
SET version=0.09
SET sqlitefile=enwikt20111008
::SET sqlitefile=ruwikt20110521
::
SET jarfile=wiwordik-%version%-%sqlitefile%.jar
cd ..
:: see http://www.lkn.ei.tum.de/arbeiten/faq/man/JAVA-tutorial/jar/basics/update.html
::::jar uf store/%jarfile% %sqlitefile%.sqlite
::jar uf store/%jarfile% sqlite/%sqlitefile%.sqlite
::
:: see http://wiki.plexinfo.net/index.php?title=How_to_sign_JAR_files
"C:\Program Files\Java\jdk1.7.0_02\bin\jarsigner" -keystore D:\all\docs\my_texts\social\root\jarsigner\myKeystore store/%jarfile% andrew_k
