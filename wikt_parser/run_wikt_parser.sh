#/bin/bash
# %1 language_code - language code of MySQL Wiktionary database to be parsed
# %2 n_start_from - number of records in database to start from
# e.g.:
# en 0
# ru 20000
#
# Shell script (Linux)
java -cp "dist/wikt_parser.jar:dist/lib/*" -Xms1212m -Xmx1212m -Xmn16m -XX:+DisableExplicitGC wikt.parser.Main $1 $2 > ruwikt20160210_parsed_05.log
#
# Script parameters (failed)
# WIKLIBS='"wikt_parser.jar:dist/lib/*"'
# JAVAOPTS="-Xms1212m -Xmx1212m -Xmn16m -XX:+DisableExplicitGC"
# java -cp  "$WIKLIBS" "$JAVAOPTS" wikt.parser.Main $1 $2 > ruwikt20160210_parsed_01.log
#
#
#
# ### .bat files (Windows)
# ### java -cp ./dist/wikt_parser.jar;./dist/lib/mysql-connector-java-5.1.38-bin.jar;./dist/lib/common_wiki.jar;./dist/lib/common_wiki_jdbc.jar -Xms1212m -Xmx1212m -Xmn16m -XX:+DisableExplicitGC wikt.parser.Main %1 %2 > ruwikt20130508_parsed_07_.log
