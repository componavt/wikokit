List of improvements and modifications to be done.

# GUI #

Todo:
  * Debug checkbox = print ID of all elements from DB (see d2rq);
  * Resize wordcard, reduce height for small cards with one line of information;
  * "Play" button (like Lingvo) to show wordcard for the selected word;
  * Word history list (drop down menu with history);
  * If word is changed then the selected word is selected in the middle of SwingList;

Done:
  * Click on word fills the input text window;

# DB layout changes #

## Language, labels, foreign ##

The current version of our database contains languages (and names of labels, names of semantic relations, etc.) only in English.

The database should contain names of languages (labels, etc.) in Russian (and other languages) also.

The short names of languages (see "Шаблон:lang" in ruwikt) should be stored as a field of the table "lang", not "label".

See database layout in [File\_wikt\_parsed\_empty\_sql](File_wikt_parsed_empty_sql.md).

## Word frequency and importance ##

Start using the field `page.wiki_link_count` (now value is 0).

Add the field `page.in_links` - number of in-bound links from other Wiktionary articles. Fill it by data from Mediawiki dabase, see [Mediawiki database schema](http://www.mediawiki.org/wiki/File:Mediawiki-database-schema.png).
> To find correlation between words' frequencies and number of Wiktionary in-bound links.

## Translation entry DB ##

Goal: storing Translation data from "`*` Arabic: {{t|ar|حاسوب|m|sc=Arab|tr=ħasūb}}".

One record in `translation_entry` contains one translation (word or phrase). Thus one line in Wiktionary translation box corresponds to several records in the table `translation_entry`. See [{{t}}](http://en.wiktionary.org/wiki/Template_talk:t#Documentation)

Todo:
  1. add fields gender, number, sc (script template), tr (transliteration), alt (alternate form of the word) to the table `translation_entry`

## DB todo ##

In future:
  1. + field `relation.page_id`. It will replace (partly) `wiki_text_id` for one-word-relation, e.g. [[aircraft](aircraft.md)], but not "[[aeroplane](aeroplane.md)] (брит.)" for "airplane" entry
  1. rename `lang_pos.etymology_n` to `lang_pos.etymology_id`, create table `etymology`

REDIRECT # (todo and done):
  1. page.is\_redirect (done)
  1. page.redirect\_target (done)
  1. + lang\_pos.redirect\_type (e.g. Wordform, Misspelling)
  1. + i18n: "See нелётный (Redirect)" vs. "См. нелётный (Перенаправление)"

Done (top - new, bottom - old):
  1. + field `wiki_text.wikified_text` - contains definition with wiki markup, but without context labels
  1. generate statistics about number of translations, like [SQLWiktParsedPhantasmagoria](SQLWiktParsedPhantasmagoria.md), done... see [Semantic\_relations](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Semantic_relations), [Translations](http://en.wiktionary.org/wiki/User:AKA_MBG/Statistics:Translations)
  1. To add INDEX keys in SQL (`wiki_text_words.wiki_text_id: INDEX wiki_text_id`)
  1. remove the field `meaning.definition`
  1. field `lang.name` is NOT unique, only `.code` is unique
  1. add default value (0) to the field `meaning.wiki_text_id`
  1. replace `relation.page_id` by `relation.wiki_text_id` and change link from ->page to ->wiki\_text
  1. + table `relation_type` (id,name), + field `relation.relation_type_id`
  1. field `relation_type.name` should be unique
  1. translate word from lang source to lang target

# Parser (wikt\_parser) #

## Meaning ##

Errors in parsing definitions with example, e.g. parser founds 204 (instead of 21) meanings of the English word [catch](http://en.wiktionary.org/wiki/catch#act_of_capturing).

## Synonyms in ruwikt ##

Example: [всегда](https://ru.wiktionary.org/wiki/%D0%B2%D1%81%D0%B5%D0%B3%D0%B4%D0%B0).

Discussion and format: [Викисловарь:Организационные вопросы#Автоупорядочение списков синонимов и т. п.](https://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%9E%D1%80%D0%B3%D0%B0%D0%BD%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B5_%D0%B2%D0%BE%D0%BF%D1%80%D0%BE%D1%81%D1%8B#.D0.90.D0.B2.D1.82.D0.BE.D1.83.D0.BF.D0.BE.D1.80.D1.8F.D0.B4.D0.BE.D1.87.D0.B5.D0.BD.D0.B8.D0.B5_.D1.81.D0.BF.D0.B8.D1.81.D0.BA.D0.BE.D0.B2_.D1.81.D0.B8.D0.BD.D0.BE.D0.BD.D0.B8.D0.BC.D0.BE.D0.B2_.D0.B8_.D1.82._.D0.BF.)

## Translation ##

```
девятиступенчатый | |fr=[[à]] [[neuf]] [[étage]]s  
```

The phrase should be parsed as one translation: _девятиступенчатый_ (fr) = _à neuf étages_

# Functionality #

## Context labels ##
Todo:
  * To build the tree of context labels (dependencies or inclusions). E.g. {{религ.}} label is a hyperonym of  {{ислам.}} label.




# See also #
  * [MRDQuotation](MRDQuotation.md)