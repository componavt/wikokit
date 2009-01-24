/* ExampleAPI.java - example of work with index wiki database through API.
 * 
 * Copyright (c) 2005-2008 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */

package wikidf;

import wikipedia.sql_idf.*;
import wikipedia.sql.Connect;

import java.util.*;

/** Example of work with index wiki database (wikidf) through API.
 * List of typical requests.
 * 
 * 
 * Russian articles:
 * short: Анализ_текста Кластеризация_документов
 * a lot of code: Расстояние_Левенштейна
 * compare languages: Perl and Ruby, C++ and Java
 * Category:Информационные технологии 
 *      Быстрое прототипирование
 *      Критерии оценки безопасности информационных технологий
 *      Копипаст
 *      Форк
 *      Жизненный цикл изделия
 * Категория: Обработка естественного языка
 *      Анализ текста
 *      Вопросно-ответная система
 *      Обработка естественного языка
 *      Машинный перевод
 *      Автоматизированный перевод
 *      Извлечение информации
 *      TF-IDF
 *      TF
 * Википедия:Хорошие статьи 
 *   Бернерс-Ли, Тим
 *   Теория «Смысл ↔ Текст»
 *   Статистика запросов     
 * Википедия:Избранные_статьи
 *      Берестяные грамоты
 *      Казахский алфавит     
 *      Харакат
 * 
 * 
 * Simple Wikipedia
 * Category:Very good articles
 *  Caffeine
 *  Chopstick
 *  Cuban Missile Crisis
 *  Evolution
 *  Fall of Man
 *  Geisha
 *  Hanami
 *  India
 *  Japanese tea ceremony
 *  Kamikaze
 *  Mali
 *  Music
 *  Proxy server
 *  Timpani
 */
public class ExampleAPI {

    public static void main(String args[]) {
        
        // connect to Simple Wikipedia TF-IDF database via wikipedia.sql.Connect
        Connect idfsimplewiki_conn = new Connect();
        idfsimplewiki_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
        java.sql.Connection conn = idfsimplewiki_conn.conn;
        
        String page_title = "Evolution";    // title of article in Simple Wikipedia
        
        // 1a. gets all terms for the page
        List<TermPage> tp_list = WikIDFAPI.getTerms(conn, page_title);
        
        // 1b. gets all terms (for page) sorted by TF*IDF: first are the most rare (in corpus) and frequent (at page) words
        int n = wikipedia.sql_idf.Page.countPages(conn);
        List<TermPage> tp_list_sorted = WikIDFAPI.getTermsSortedByTF_IDF(conn, page_title, n);
        
        // print terms (lemmas) of the article "Evolution"
        System.out.println("\nPage: \"" + page_title + "\"");
        System.out.println("TF*IDF : lemma : term_freq (term frequency in the article) : doc_freq (number of docs with term)");
        System.out.println("Corpus has " + n + " pages.");
        for(TermPage tp:tp_list_sorted) {
            double tf_idf = tp.getTF_IDF();
            System.out.println(
                    tf_idf +
                    " : " + tp.getTerm().getLemma() + 
                    " : "   + tp.getTermFreq() + 
                    " : " + tp.getTerm().getDocFreq());
        }
        
        // 2. searches document by terms GREEN AND TEA, 
        // documents are sorted by TF (term frequency)
        String lemma1 = "GREEN";
        String lemma2 = "TEA";
        List<TermPage> list1 = WikIDFAPI.getPages(conn, lemma1);
        List<TermPage> list2 = WikIDFAPI.getPages(conn, lemma2);
        List<TermPage> intersection = TermPage.intersectPageTitles(list1, list2);
        Collections.sort(intersection, TermPage.TF_ORDER);
        
        System.out.println("\nPages which contain: " + lemma1 + " AND " + lemma2 + ":");
        System.out.println("term_freq (two terms frequency in the page) : page title : number of words (page length)");
        for(TermPage tp:intersection) {
            System.out.println(
                    tp.getTermFreq() + 
                    " : " + tp.getPageTitle() + 
                    " : " + tp.getPageWordCount());
        }
        
    }
}
