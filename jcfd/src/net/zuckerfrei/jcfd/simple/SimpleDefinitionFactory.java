package net.zuckerfrei.jcfd.simple;

import net.zuckerfrei.jcfd.Database;
import net.zuckerfrei.jcfd.Definition;
import net.zuckerfrei.jcfd.DefinitionFactory;

/**
 * Factory for creating simple definition objects.
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 * 
 * @see net.zuckerfrei.jcfd.Definition
 * @see net.zuckerfrei.jcfd.simple.SimpleDefinition
 * @see net.zuckerfrei.jcfd.Configuration
 */
public class SimpleDefinitionFactory extends DefinitionFactory {

    /**
     * @see net.zuckerfrei.jcfd.DefinitionFactory#createDefinition(String,Database,Object)
     */
    public Definition createDefinition(String word, Database database, Object content) {
        return new SimpleDefinition(word, database, (String) content);
    }

}
