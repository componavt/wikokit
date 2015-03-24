(This page is a coordination of work devoted to an extraction of [Context Labels](http://en.wiktionary.org/wiki/Category:Context_labels) from English Wiktionary and Russian Wiktionary.)

# Цель #

Цель работы - добавить в парсер информацию о всех словарных пометах Английского Викисловаря (сокращённо enwikt), Русского Викисловаря (ruwikt) и указать какие пары идентичны в этих двух викисловарях.

# Этапы #

_Первый этап_. Обходим словарные пометы Английского Викисловаря (1001 <s>ночь</s> помета), сверяем с пометами Русского Викисловаря (359 помет) и заносим данные в код парсера.

_Второй этап_. Обходим словарные пометы Русского Викисловаря (359 помет), смотрим - какие из них остались не привязаны к пометам Английского Викисловаря и
  * либо находим к какой помете Английского Викисловаря привязать,
  * либо создаём под неё новую помету в файле ```LabelEn```, к которой и привязываем эту русскую сиротинку.

# Что нужно скачать / установить #
0) [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

1) Редактор [NetBeans](https://netbeans.org/downloads/). Нужна версия "Java SE".

2) SVN-клиент [TortoiseSVN](http://tortoisesvn.net/).

3) Как пишут гугловцы здесь http://code.google.com/p/wikokit/source/checkout - выполните из svn команду, чтобы получить исходный код:
```
svn checkout http://wikokit.googlecode.com/svn/trunk/ wikokit-read-only
```

4) Откройте из NetBeans скачанный проект "common\_wiki" и откройте в нём файлы, с которыми будете работать:
  * [LabelCategory.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/constant/LabelCategory.java)
  * [LabelCategoryRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/LabelCategoryRu.java)
  * [LabelEn.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/en/name/LabelEn.java)
  * [LabelRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/LabelRu.java)

Чтобы открыть нужный файл, нажмите в NetBeans комбинацию клавиш Ctrl+O и напишите название файла, например LabelEn. Редактор NetBeans выполнит поиск по всем файлам открытых проектов.

# Пример конкретной работы (первый этап) #

1) Открываю файл [LabelEn.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/en/name/LabelEn.java), нахожу строку со своей фамилией:
```
// period 15 Krizhanovsky
```
Это значит, мне нужно обработать 15 словарных помет категории "period" (время).

2) Открываю страницу enwikt [Category:Context\_labels](http://en.wiktionary.org/wiki/Category:Context_labels), нахожу подкатегорию словарных помет со словом "period", это [Category:Period context labels](http://en.wiktionary.org/wiki/Category:Period_context_labels)

3) Беру последовательно пометы с этой страницы и в алфавитном порядке добавляю в файл _LabelEn_ после
```
// period 15 Krizhanovsky
```

## Пример 1 (первый этап). "neologism" ##

Итак, взяли из Английского Викисловаря помету [Template:neologism](http://en.wiktionary.org/wiki/Template:neologism) ("неологизм"). Добавляем код в LabelEn:
```
public static final Label neologism = new LabelEn("neologism", "neologism", LabelCategory.period);
```
Первый и второй параметры - короткое и длинное имя пометы (здесь совпадают), третий параметр - категория ("period"), к которой относится помета.

Теперь заглядываем в [Викисловарь:Условные сокращения](http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F) и проверяем - есть ли в Русском Викисловаре аналог?

Да, есть:
  * неол. — неологизм, есть [Шаблон:неол.](http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BD%D0%B5%D0%BE%D0%BB.) (краткое название), полное название "неологизм". Добавляю в [LabelRu.java](http://code.google.com/p/wikokit/source/browse/trunk/common_wiki/src/wikokit/base/wikt/multi/ru/name/LabelRu.java) строку (в алфавитном порядке, в раздел "period"):
```
public static final Label neologism = new LabelRu("неол.", "неологизм", LabelEn.neologism);
```

Имя переменной (neologism) совпадает с имененем соответствующей переменной в LabelEn. Первый и второй параметры - краткое и полное название пометы. Третий параметр - имя переменной в LabelEn, к которой привязывается эта переменная.

## Пример 2 (первый этап). "ru-pre-reform" ##

Берём следующую помету из категории "period". Шаблон [Template:ru-pre-reform](http://en.wiktionary.org/wiki/Template:ru-pre-reform) указывает на устаревшее значение слова. Т.е. это синоним для другой пометы: "obsolete". Поэтому добавляем в LabelEn:
```
public static final Label obsolete = new LabelEn("obsolete", "obsolete", LabelCategory.period);
public static final Label ru_pre_reform = LabelEn.addNonUniqueShortName(obsolete, "ru-pre-reform");
```
Во второй строке помета _ru\_pre\_reform_ как синоним ссылается на помету в первой строке "obsolete". У функции _addNonUniqueShortName_ второй параметр "ru-pre-reform" - это имя шаблона, который будет извлекать парсер.

Для синонимов (т.е. помета _ru\_pre\_reform_, добавленная методом addNonUniqueShortName) не нужно писать перевод в LabelRu.
Для основных помет (основная т.е. нет синонимов, например помета "obsolete") в LabelRu записываем такую строку:
```
public static final Label obsolete = new LabelRu("устар.", "устаревшее", LabelEn.obsolete);
```

## Пример 3 (первый этап). "Ecclesiastical Latin" ##

Нашёл пояснение - что это значит - в Английской Википедии: http://en.wikipedia.org/wiki/Ecclesiastical_Latin

В [условных сокращениях](http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F) Русского Викисловаря ничего похожего не вижу, поэтому в файл LabelRu ничего не пишу, пишу только в LabelEn:
```
public static final Label ecclesiastical_latin = new LabelEn("Ecclesiastical Latin", "Ecclesiastical Latin", LabelCategory.period);
```
Имя помете даю маленькими буквами, слова в имени объединяю подчёркиванием "ecclesiastical\_latin".

## Последовательность работ ##

  1. Крижановские
  1. Румянцев (до 16 мая)
  1. Чирковы (до 1 июня)
  1. Огийко (до 15 июня)
  1. Головин (до 1 июля)

# Подсказки #

## Название шаблона ##

Не обязательно шаблон словарной пометы будет называться также, как его видит пользователь. Например в [условных сокращениях](http://ru.wiktionary.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D1%81%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C:%D0%A3%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5_%D1%81%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D1%8F) указана помета "мн. ч.", но шаблона "мн. ч." в ruwikt нет, зато есть [Шаблон:мн.](http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BC%D0%BD.). Т.е. для большинства помет на странице "Викисловарь:Условные сокращения" есть соответствующие шаблоны, которые и нужно знать парсеру для успешного их распознавания. В случае "мн. ч." нужно в LabelRu использовать функцию для указания синонимов словарных помет:
```
LabelRu.addNonUniqueShortName // см. примеры использования в файле LabelRu
```

Вывод: перед добавлением первого параметра (например "неол." в следующей строке) в файл LabelRu - убедитесь, что в Русском Викисловаре существует шаблон [шаблон:неол.](http://ru.wiktionary.org/wiki/%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD:%D0%BD%D0%B5%D0%BE%D0%BB.). И если такая страница есть, то пишите:
```
public static final Label neologism = new LabelRu("неол.", "неологизм", LabelEn.neologism);
```

## Повтор шаблона ##

Может оказаться, что шаблон, который вы добавляете, уже есть в файле LabelEn, т.е. его добавили раньше и его добавлять ещё раз не нужно.

Это может случиться, если шаблон принадлежит сразу нескольким категориям. Например [шаблон:Late Latin](http://en.wiktionary.org/wiki/Template:Late_Latin) принадлежит двум категориям:
  * Context labels
  * Period context labels

Если встречается шаблон с несколькими категориями, то нужно подумать, какую из двух категорий, т.е. какую из двух строчек, оставить:
```
public static final Label Late_Latin = new LabelEn("Late Latin", "Late Latin", LabelCategory.empty);
public static final Label Late_Latin = new LabelEn("Late Latin", "Late Latin", LabelCategory.period);
```

В данном случае выбрать легко - категория времени (period) предпочтительнее, чем пустая категория (empty), которая ничего не говорит о словарной помете. В других случаях придётся думать и выбирать более подходящую категорию для словарной пометы, т.к. у пометы мы можем указать только одну категорию.

## Разное ##
Не забыть указать синонимы: "мн. ч." и "мн.ч." для "мн.".

Синоним аббр. к сокр.

# Литература #

В наличие есть книга в бумажном и электронном виде, которая поможет в переводе помет:
  * Ахманова О. С. [Словарь лингвистических терминов](https://www.dropbox.com/s/qtaagjxeci7zln4/%D0%90%D1%85%D0%BC%D0%B0%D0%BD%D0%BE%D0%B2%D0%B0%20%D0%9E.%D0%A1.%20-%20%D0%A1%D0%BB%D0%BE%D0%B2%D0%B0%D1%80%D1%8C%20%D0%BB%D0%B8%D0%BD%D0%B3%D0%B2%D0%B8%D1%81%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D1%85%20%D1%82%D0%B5%D1%80%D0%BC%D0%B8%D0%BD%D0%BE%D0%B2.djvu), 1969.