## Intro ##

The goal is to create an empty SQL file for the Wiktionary parsed database, i.e. create `wikt_parsed_empty.sql` from `wikt_parsed_empty.mwb` via MySQL Workbench.

See the files in the folder wikokit/wikt\_parser/doc/

# Details #

Download [MySQL Workbench](http://wb.mysql.com/).

MySQL Workbench tips:
  * Export forward engineer
  * V Generate Drop Statements Before Each CREATE statement
  * V Omit Scheme Qualifier
  * V Generate INSERTS Statements for Tables

Vim hacks:
```
%s/`mydb`.//g 
```

# See also #
  * [File\_wikt\_parsed\_empty\_sql](File_wikt_parsed_empty_sql.md)