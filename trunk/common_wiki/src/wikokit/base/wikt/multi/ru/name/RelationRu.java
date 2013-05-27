/* RelationRu.java - names of semantic relations in Russian.
 *
 * Copyright (c) 2012 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under EPL/LGPL/GPL/AL/BSD multi-license.
 */

package wikokit.base.wikt.multi.ru.name;


import wikokit.base.wikt.constant.Relation;
import wikokit.base.wikt.constant.RelationLocal;


/** Names of semantic relations in Russian and the links to the Relation objects.
 * 
 * @attention: initialize class before using, e.g. "RelationLocal _ = RelationRu.synonymy;"
 */
public class RelationRu extends RelationLocal {

    private RelationRu(String _name, String _short_name, Relation _rel) {
        super(_name, _short_name, _rel);
    }
    
    public static final RelationLocal   synonymy,
                                        antonymy,
                                        relational_antonym,
                                        hypernymy,
                                        hyponymy,
                                        holonymy,
                                        meronymy,
                                        troponymy,
                                        coordinate_term,
                                        otherwise_related;
    
    static {
    // public static final RelationLocal unknown = new RelationRu("неизвестные", "неизв.", Relation.uknown); /** The relation is unknown :( */
    
        synonymy = new RelationRu("синонимы", "син.", Relation.synonymy);
        antonymy = new RelationRu("антонимы", "ант.", Relation.antonymy);
        relational_antonym = new RelationRu("конверсивы", "конв.", Relation.relational_antonym);
        
        hypernymy = new RelationRu("гиперонимы", "гиперн.", Relation.hypernymy);
        hyponymy = new RelationRu("гипонимы", "гипон.", Relation.hyponymy);
        
        holonymy = new RelationRu("холонимы", "холон.", Relation.holonymy);    
        meronymy = new RelationRu("меронимы", "мерон.", Relation.meronymy);
        
        troponymy = new RelationRu("тропонимы", "тропон.", Relation.troponymy);
        coordinate_term = new RelationRu("согипонимы", "согип.", Relation.coordinate_term);
        
        otherwise_related = new RelationRu("смотри также", "см.", Relation.otherwise_related);
    }
}
