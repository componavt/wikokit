wikokit (c) 2005-2010, Andrew.Krizhanovsky at gmail.com
http://code.google.com/p/wikokit/
                                                  
Previous project name Synarcher.
Previous site: https://sourceforge.net/projects/synarcher

Wiki tool kit (wikokit) contains several projects related to wiki
(you can open these projects in NetBeans IDE): 

./common_wiki   - common (low-level) functions for access to Wikipedia and 
                Wiktionary in MySQL database,

./hits_wiki     (old title kleinberg)
                - API for access to Wikipedia in MySQL database,
                algorithms to search synonyms in Wikipedia
                (depends on jcfd.jar, common_wiki.jar).

./TGWikiBrowser - visual browser to search for synonyms in local or
                remote Wikipedia (depends on hits_wiki.jar and common_wiki.jar)

./wikidf        - Wiki Index Database (list of lemmas and links to wiki pages,
                which contain these lemmas). See ./wikidf/readme.txt

./wikt_parser   - Wiktionary parser creates a MySQL database (like WordNet) 
                from an Wiktionary MySQL dump file. The project goal is to 
                convert Wiktionary articles to machine readable format.
                (It depends on common_wiki.)

./jcfd          - Java client for Dict is written by Davor Cengija, 
                Apache License, Version 1.1.
                (jcfd.jar is used in hits_wiki.jar in order to compare search 
                results with thesauri WordNet and Moby. It is not yet 
                incorporated into binary version of the program Synarcher).

See gpl.txt and "notice" for license details.

wikokit is provided as binary (wikokit-X.X.X.tar.gz) and full source code 
(wikokit-X.X.X.src.tar.gz).

--- rewrite below.

Synarcher searches synonyms (and related words) in a set of texts of special 
structure (like Wikipedia and other wiki-resources).

See documentation in docs/index.html 
or in ./hits_wiki/docs/docbook/html/index.html

Run Synarcher in binary package by:
  sh synarcher.sh       - Linux, English interface
  sh synarcher_ru.sh    - Linux, Russian interface
  synarcher.bat         - Windows, English interface
  synarcher_ru.bat      - Windows, Russian interface

In order to run Synarcher in source package compile it, then run:
  sh work_synarcher.sh  - Linux, English interface
     work_synarcher.bat - Windows, English interface

In order to add translation of the program interface (e.g. to Esperanto) see 
folder 'TGWikiBrowser/src/translation'.

You can automatically create synarcher-X.X.X.tar.gz and 
synarcher-X.X.X.src.tar.gz:
0. rename synarcher-X.X.X.src to something else or change variable ver_id 
(see step 2.), else the folder synarcher-X.X.X.src will be removed at step 3.
1. cd /releases
2. edit "input variables" in create_tar_gz.sh
3. sh create_tar_gz.sh
