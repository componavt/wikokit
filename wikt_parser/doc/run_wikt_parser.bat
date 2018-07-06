:: %1 language_code - language code of MySQL Wiktionary database to be parsed
:: %2 n_start_from - number of records in database to start from
:: e.g.: en 0, or ru 20000

java -cp ./dist/wikt_parser.jar;./dist/lib/mysql-connector-java-8.0.11.jar;./dist/lib/common_wiki.jar -Xms1212m -Xmx1212m -Xmn16m -XX:+DisableExplicitGC wikt.parser.Main %1 %2 > enwikt20100824_parsed_06.log

:: java -cp ./dist/wikt_parser.jar;./dist/lib/mysql-connector-java-8.0.11.jar;./dist/lib/common_wiki.jar -Xms1212m -Xmx1212m -Xmn16m -XX:+DisableExplicitGC -verbose:gc wikt.parser.Main %1 %2 > enwikt20100106_parsed_02.log

::javafx -cp ./dist/wiwordik.jar;./dist/lib/mysql-connector-java-8.0.11.jar;./dist/lib/sqlite-jdbc-3.6.17.1.jar;./dist/lib/common_wiki.jar wiwordik.Main
:: javafx -verbose -cp ./dist/wiwordik.jar;./dist/lib/mysql-connector-java-8.0.11.jar;./dist/lib/sqlite-jdbc-3.6.17.1.jar;./dist/lib/common_wiki.jar wiwordik.Main

:: javafx -cp ./wiwordik/dist/wiwordik.jar;./common_wiki/lib/mysql-connector-java-8.0.11.jar;./common_wiki/lib/sqlite-jdbc-3.6.17.1.jar;./common_wiki/dist/common_wiki.jar wiwordik.Main
