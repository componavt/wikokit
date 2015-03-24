

# Introduction #

Download the parsed Wiktionary database from this site, e.g. `enwikt20XXXX_parsed.zip`. Unzip and upload to your local MySQL database.

Queries below will be trivial, obvious, clear and easily understood :) if you print and meditate on the figure "the structure (tables and relations) of the Wiktionary parsed database" (see [File\_wikt\_parsed\_empty\_sql](File_wikt_parsed_empty_sql.md)).

# SQL query examples #
## From page to definitions ##

It's given an english word, e.g. [transient](http://en.wiktionary.org/wiki/transient). The aim is to get all the definitions.

It could be concluded (from the figure with tables and relations of the Wiktionary parsed database, see [File\_wikt\_parsed\_empty\_sql](File_wikt_parsed_empty_sql.md)) that the following sequence of tables should be used: `page` -> `lang_pos` -> `meaning` -> `wiki_text`.

1. Table `page`.

```
mysql> SELECT id FROM page WHERE page_title="transient";
```

| **id** |
|:-------|
| 33823 |

2. The code of English language is **en**. Let's select identifier (`lang_id`) from the table `lang`.

```
mysql> SELECT * FROM lang WHERE code="en";
```

| **id** | **name** | **code** | **n\_foreign\_POS** | **n\_translations** |
|:-------|:---------|:---------|:--------------------|:--------------------|
| 245 | English | en | 265060 | 0 |

3. Table `page` -> `lang_pos`.

```
mysql> SELECT * FROM lang_pos WHERE page_id=33823 AND lang_id=245;
```

| id      | page\_id | pos\_id | lang\_id | etymology\_n | lemma | redirect\_type |
|:--------|:---------|:--------|:---------|:-------------|:------|:---------------|
| 1110123 |   33823 |      3 |     245 |           0 |       |          NULL |
| 1110124 |   33823 |     11 |     245 |           1 |       |          NULL |

There are two records in the table `lang_pos` with different part of speech identifier (field `pos_id`, see the table `part_of_speech`). Let's work with adjective (first record, pos\_id=3), then lang\_pos.id=1110123

4. Table `lang_pos` -> `meaning`.

```
mysql> SELECT * FROM meaning WHERE lang_pos_id=1110123;
```

| id      | lang\_pos\_id | meaning\_n | wiki\_text\_id |
|:--------|:--------------|:-----------|:---------------|
| 1431934 |     1110123 |         0 |      1589097 |
| 1431935 |     1110123 |         1 |      1589106 |
| 1431936 |     1110123 |         2 |      1589107 |
| 1431937 |     1110123 |         3 |      1589109 |
| 1431938 |     1110123 |         4 |      1589110 |
| 1431939 |     1110123 |         5 |      1589111 |
| 1431940 |     1110123 |         6 |      1589112 |

OK. There are 7 definitions for the adjective [transient](http://en.wiktionary.org/wiki/transient).

5. Table `meaning` -> `wiki_text`.

Let's extract the text of the first meaning from the table `wiki_text`.

```
mysql> SELECT * FROM wiki_text WHERE id=1589097;
```

| id      | text                                           |
|:--------|:-----------------------------------------------|
| 1589097 | Passing or disappearing with time; transitory. |


## Synonyms ##

Let's get list of synonyms for the second meaning of English noun "[iron](http://en.wiktionary.org/wiki/iron)".

Sequence of tables:
  1. page & lang & part\_of\_speech -> lang\_pos -> meaning
  1. relation\_type & meaning -> relation -> wiki\_text

### 1) page -> meaning ###

```
mysql> SELECT id FROM page WHERE page_title="iron";
```

The entry "iron" has page\_id = 41364.

```
mysql> SELECT * FROM lang WHERE code="en";
```

The language code "en" has lang\_id = 285.

```
mysql> SELECT * FROM part_of_speech WHERE name="noun";
```

The part of speech "noun" has pos\_id = 38.

```
mysql> SELECT * FROM lang_pos WHERE page_id=41364 AND lang_id=285 AND pos_id=38;
```

The English noun "iron" has lang\_pos\_id=460576.

```
mysql> SELECT * FROM meaning WHERE lang_pos_id=460576 AND meaning_n=1;
```

The second meaning (n=1, start from zero) of English noun "iron" has meaning\_id=608448, wiki\_text\_id=796355.

```
mysql> SELECT * FROM wiki_text WHERE id=796355;
```

The second meaning of "iron" has definition:

_{{countable}} A tool or appliance made of metal, which is heated and then used to transfer heat to something else; most often a thick piece of metal fitted with a handle and having a flat, roughly triangular bottom, which is heated and used to press wrinkles from clothing, and now usually containing an electrical heating apparatus._

### 2) relation\_type & meaning -> relation -> wiki\_text ###

```
mysql> SELECT id FROM relation_type WHERE name="synonyms";
```

The Synonymy relation has relation\_type\_id=8.

```
mysql> SELECT * FROM relation WHERE meaning_id=608448 AND relation_type_id=8;
```

The second meaning of "iron" has two synonyms with wiki\_text\_id 796356 and 796357.

```
mysql> SELECT * FROM wiki_text WHERE id=796356;
mysql> SELECT * FROM wiki_text WHERE id=796357;
```

The second meaning of "iron" has two synonyms:
  * _flatiron {{qualifier|old-fashioned}}_;
  * _smoothing iron {{qualifier|old-fashioned}}_.

## Translate word from one language to another ##

We consider two ways to get translations by using English Wiktionary:
  * _direct translation_ - from English word to words in other languages, which are listed in the section _Translation_ of the entry;
  * _reverse translation_ - from the non-English word in the section _Translation_ to the English title (header) of the entry;

### 1) Translate from English ###

Let's find translation of the English (**en** code) word [anecdote](http://en.wiktionary.org/wiki/anecdote) into Portuguese language by the first way (_direct translation_).

1. The code of English language is **en**. Let's select identifier (`lang_id`) from the table `lang`.

```
mysql> SELECT * FROM lang WHERE code="en";
```

| **id** | **name** | **code** | **n\_foreign\_POS** | **n\_translations** |
|:-------|:---------|:---------|:--------------------|:--------------------|
| 285 | English | en | 255327 | 0 |

2. Table `page`.

```
mysql> SELECT id FROM page WHERE page_title="anecdote";
```

| **id** |
|:-------|
| 245963 |

3. Table `lang` and `page` -> `lang_pos`.

We have found ID of records in the tables `lang` and `page`, so we can find ID of records in the table `lang_pos`.

```
mysql> SELECT * FROM lang_pos WHERE page_id=245963 AND lang_id=285;
```

| id     | page\_id | pos\_id | lang\_id |
|:-------|:---------|:--------|:---------|
| 138883 | 245963 |     38 |     285 |

4. Table `lang_pos` -> `meaning`

Let's get ID of meaning (definition) by identifier from the table `lang_pos` (lang\_pos\_id).

```
mysql> SELECT * FROM meaning WHERE lang_pos_id=138883;
```

| id     | lang\_pos\_id | meaning\_n | wiki\_text\_id |
|:-------|:--------------|:-----------|:---------------|
| 169914 |      138883 |         0 |       244073 |
| 169915 |      138883 |         1 |       244098 |
| 169916 |      138883 |         2 |       244101 |

So, there are three meaning for this word. Let's find translation for the first meaning with meaning\_id = 169914.

5. Table `meaning` -> `translation`

```
mysql> SELECT * FROM translation WHERE meaning_id=169914;
```

| id    | lang\_pos\_id | meaning\_summary              | meaning\_id |
|:------|:--------------|:------------------------------|:------------|
| 13063 |      138883 | short account of an incident |     169914 |

OK. translation\_id = 13063

6. Language code of the target language (Portuguese) is "pt". We need to find lang\_id.

```
mysql> SELECT * FROM lang WHERE code="pt";
```

| **id** | **name**     | **code**| **n\_foreign\_POS** | **n\_translations** |
|:-------|:-------------|:--------|:--------------------|:--------------------|
| 756  | Portuguese | pt    | 8311            | 0                |

lang\_id = 756

7. Table `translation_entry` -> `wiki_text`

The list of records in the table `translation_entry` which corresponds to this (first) meaning:

```
mysql> SELECT * FROM translation_entry WHERE translation_id=13063 AND lang_id=756;
```

| id     | translation\_id | lang\_id | wiki\_text\_id |
|:-------|:----------------|:---------|:---------------|
| 134181 |          13063 |     756 |       244093 |

`wiki_text_id=244093` will helps to find the translation word itself.

8. Result translation.

```
mysql> SELECT * FROM wiki_text WHERE id=244093;
```

| id     | text    |
|:-------|:--------|
| 244093 | anedota |

Good job.

### 2) Translate to English ###

Let's go back and will find reverse translation: from **anedota** in Portuguese to English. It should be **[anecdote](http://en.wiktionary.org/wiki/anecdote)**.

1. We know (see steps above) the IDs of English and Portuguese languages (but be carefull, since language ID is not constant, it could be different in different databases):

**English lang\_id = 285** Portuguese lang\_id = 756

2. wiki\_text -> wiki\_text\_id

We know the word in the Translation section in the Wiktionary entry to be found: **anedota**.

```
mysql> SELECT * FROM wiki_text WHERE text="anedota";
```

| id     | text    |
|:-------|:--------|
| 244093 | anedota |

3. Table `wiki_text_id` -> `translation_entry` -> `translation_id`

Portuguese lang\_id = 756

```
mysql> SELECT * FROM translation_entry WHERE wiki_text_id=13063 AND lang_id=756;
```

| id     | translation\_id | lang\_id | wiki\_text\_id |
|:-------|:----------------|:---------|:---------------|
| 134181 |          13063 |     756 |       244093 |

translation\_id = 13063;

4.  Table `translation_id` -> `meaning` -> `meaning_id`

```
mysql> SELECT * FROM translation WHERE id=13063;
```

| id    | lang\_pos\_id | meaning\_summary              | meaning\_id |
|:------|:--------------|:------------------------------|:------------|
| 13063 |      138883 | short account of an incident |     169914 |

meaning\_id = 169914;

5. Table `meaning_id` -> `lang_pos` -> `lang_pos_id`

```
mysql> SELECT * FROM meaning WHERE id = 169914;
```

| id     | lang\_pos\_id | meaning\_n | wiki\_text\_id |
|:-------|:--------------|:-----------|:---------------|
| 169914 |      138883 |         0 |       244073 |

lang\_pos\_id = 138883;

6. Table `lang` and `lang_pos_id` -> `page_id`.

English lang\_id = 285

```
mysql> SELECT * FROM lang_pos WHERE lang_id=285 AND id=138883;
```

| id     | page\_id | pos\_id | lang\_id |
|:-------|:---------|:--------|:---------|
| 138883 | 245963 |     38 |     285 |

page\_id = 245963;

7. Table `page`. Gets word (headword, i.e. Wiktionary entry) by ID.

```
mysql> SELECT page_title FROM page WHERE id=245963;
```

| **page\_title** |
|:----------------|
| anecdote |



<a href='Hidden comment: 
== Warning ==

=== Empty definitions ===

If you run this query:

```
mysql> select * from page, lang_pos, meaning where wiki_text_id is NULL AND lang_pos_id=meaning.lang_pos_id AND page.id=lang_pos.page_id AND page.id < 100 GROUP BY page.id LIMIT 10;
```

then you will see the list of first N words (of 100 words) which has empty definitions (meanings.wiki_text_id is NULL), because... Because (1) the Wiktionary parser is not finished yet and (2) the Wiktionary has a lot of interesting templates which should be parsed correctly.

E.g. The result of this query contains the symbol [http://en.wiktionary.org/wiki/!%3D !=]. It has null `meanings.wiki_text_id`, because (IMHO) the entry contains the template _{{head|_ which is not parsed in this version of the parser. So the parser can not find the text of the definition due to this template. :(
'></a>


# See also #
  * [GettingStartedWiktionaryParser](GettingStartedWiktionaryParser.md)
  * [d2rqMappingSPARQL](d2rqMappingSPARQL.md)
  * [MRDQuote](MRDQuote#SQL_queries.md)