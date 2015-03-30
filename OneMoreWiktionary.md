# Introduction #

This page will describe Java files to be added to the parser in order to parse one more Wiktionary language edition (WLE).

In order to parse new WLE you have to know the formatting rules of this WLE, because each WLE has different communities and different rules. See interwiki at [enwikt formatting rules](http://en.wiktionary.org/wiki/Wiktionary:ELE).

# 1. Adding new files and classes #
## Example of existing parser module (Russian Wiktionary) ##

There are files and classes in the parser in order to extract information from the Russian Wiktionary.

Russian language code is "ru" (ISO 639) is used as the suffix _Ru_ in names of new Java files and suffix _.ru_ in names of new Java packages (related to Russian language).

There is a special package [wikokit.base.wikt.multi](https://github.com/componavt/wikokit/tree/master/common_wiki/src/wikokit/base/wikt/multi) which contains subpackages for each WLE: en, ru.

Russian Wiktionary module files in the package **[wikokit.base.wikt.multi.ru.name](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/)**:
  * [LanguageTypeRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/LanguageTypeRu.java) - names of languages in Russian and links to the [LanguageType](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikipedia/language/LanguageType.java) codes;
  * [POSRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/POSRu.java) - names of parts of speech in Russian and the links to the [POS](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/constant/POS.java) objects;
  * [RelationRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/RelationRu.java) - names of semantic relations in Russian and the links to the [Relation](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/constant/POS.java) objects.
  * [LabelCategoryRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/LabelCategoryRu.java) - skip now, context labels are under construction this year... (names of categories of context labels in Russian).

Russian Wiktionary module files in the package **[wikokit.base.wikt.multi.ru](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/)**:
  * each file in this package corresponds to the file in the package _[wikokit.base.wikt.word](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/word)_ (see next section), which correspond to one level (header, subsection) of the Wiktionary entry;
  * + additional file [POSTemplateRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/POSTemplateRu.java) - correspondences between part of speech (POS) templates in ruwikt and POS names.

## New parser module (copy and paste) ##

Let's we want to extend our parser by files and classes in order to parse e.g. French Wiktionary (language code is "fr"). The following "сopy and paste" method could simplify work:
  1. copy folder (package) _wikokit.base.wikt.multi.**ru**_ to _wikokit.base.wikt.multi.**fr**_;
  1. copy _wikokit.base.wikt.multi.**ru**.name_ to _wikokit.base.wikt.multi.**fr**.name_;
  1. rename files in this folders (e.g. _LanguageTypeRu.java_ to _LanguageTypeFr.java_ etc.)
  1. change the code of these files in accordance with formatting rules of your (e.g. French) Wiktionary - the most fun part of work :)

# 2. Adding new code to existing files and classes #

Each file in the package [wikokit.base.wikt.word](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/word) contains the call of WLE parser module. E.g. [WLanguage.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/word/WLanguage.java) contains code:
```
LanguageType wikt_lang; // language of Wiktionary

if(l  == LanguageType.ru) {
  lang_sections = WLanguageRu.splitToLanguageSections(page_title, text);
} else if(l == LanguageType.en) {
  lang_sections = WLanguageEn.splitToLanguageSections(page_title, text);
} else {
  throw new NullPointerException("Null LanguageType");
}
```

If you remember we wanted to parse new French Wiktionary. Then this code should be extended:
```
if(l  == LanguageType.ru) {
  lang_sections = WLanguageRu.splitToLanguageSections(page_title, text);

// these two lines were added
} else if(l == LanguageType.fr) {
  lang_sections = WLanguageFr.splitToLanguageSections(page_title, text);

} else if(l == LanguageType.en) {
  lang_sections = WLanguageEn.splitToLanguageSections(page_title, text);
} else {
  throw new NullPointerException("Null LanguageType");
}
```

Now we are calling **WLanguageFr.java** which should be located at the package **wikokit.base.wikt.multi.fr**.

# Comments #
1. Don't forget about [unit test](http://en.wikipedia.org/wiki/Unit_testing). It's a best documentation of our code. Every nontrivial class and function in this project have unit tests, e.g. class [WLanguageRu](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/WLanguageRu.java) has unit tests in the file [WLanguageRuTest.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/test/wikokit/base/wikt/multi/ru/WLanguageRuTest.java)

2. Parsed Wiktionary language edition is defined as input parameter of the file [Main.java](http://code.google.com/p/wikokit/source/browse/trunk/wikt_parser/src/wikt/parser/Main.java) in the [wikt\_parser](https://code.google.com/p/wikokit/source/browse/trunk/wikt_parser#wikt_parser%253Fstate%253Dclosed) project:
```
LanguageType wikt_lang; // language of Wiktionary
```

We should also add code to this file (Main.java) in order to parse French Wiktionary.