package net.zuckerfrei.jcfd;

import junit.framework.TestCase;

/**
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class DefinitionListTest extends TestCase {

    DefinitionList list;

    /**
     * Constructor for DefinitionListTest.
     * @param arg0
     */
    public DefinitionListTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DefinitionListTest.class);
    }
    
    public void setUp() {
        list = new DefinitionList();
    }
    
    public void testInit() throws Exception {
        assertEquals(0, list.count());
        assertEquals(false, list.hasNext());
        try {
            list.next();
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            ;
        }
        
        try {
            list.prev();
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            ;
        }
    }
    
    public void testCount() {
        addNullDefinition();
        assertEquals(1, list.count());
        addNullDefinition();
        assertEquals(2, list.count());
    }
    
    public void testPosition() {
        assertEquals(0, list.position());
        addNullDefinition(2);
        
        list.next();
        assertEquals(1, list.position());
        
        list.next();
        assertEquals(2, list.position());
        
        list.prev();
        assertEquals(1, list.position());
        
        list.prev();
        assertEquals(0, list.position());
    }
    
    public void testHasNext() {
        assertEquals(false, list.hasNext());
        addNullDefinition();
        assertEquals(true, list.hasNext());
        list.next();
        assertEquals(false, list.hasNext());
        addNullDefinition();
        assertEquals(true, list.hasNext());
        list.next();
        assertEquals(false, list.hasNext());
        list.prev();
        assertEquals(true, list.hasNext());
        list.next();
        assertEquals(false, list.hasNext());
        list.prev();
        assertEquals(true, list.hasNext());
        list.prev();
        assertEquals(true, list.hasNext());
    }
    
    public void testGo() {
        addNullDefinition(5);
        assertEquals(0, list.position());
        list.next();
        list.next();
        assertEquals(2, list.position());
        list.goBeforeFirst();
        assertEquals(0, list.position());
        list.goAfterLast();
        assertEquals(5, list.position());
        assertEquals(false, list.hasNext());
    }
    
    public void testRemove() {
        addNullDefinition(4);
        Definition definition = new MockDefinition();
        list.addDefinition(definition);
        addNullDefinition(5);
        
        assertEquals(10, list.count());
        
        list.removeDefinition(definition);
        
        assertEquals(9, list.count());
    }
    
    
    private void addNullDefinition() {
        list.addDefinition(new MockDefinition());
    }
    
    private void addNullDefinition(int count) {
        for (int i = 1; i <= count; i++) {
            addNullDefinition();
        }
    }

}
