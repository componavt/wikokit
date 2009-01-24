#!/bin/sh
#
echo 'Converting MessagesBundle_ru to UTF-8';
native2ascii -encoding UTF-8 MessagesBundle_ru_edit.properties MessagesBundle_ru.properties
