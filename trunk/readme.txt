wikokit (c) 2005-2013, Andrew dot Krizhanovsky at gmail.com
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