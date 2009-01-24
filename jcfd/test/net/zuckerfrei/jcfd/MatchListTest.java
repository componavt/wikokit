package net.zuckerfrei.jcfd;

import junit.framework.TestCase;

/**
 * @author Davor Cengija
 * @version $Revision: 1.1.1.1 $
 */
public class MatchListTest extends TestCase {
    
    MatchList list;

    /**
     * Constructor for MatchListTest.
     * @param arg0
     */
    public MatchListTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MatchListTest.class);
    }
    
    public void setUp() {
        list = new MatchList();
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
        addNullMatch();
        assertEquals(1, list.count());
        addNullMatch();
        assertEquals(2, list.count());
    }
    
    public void testPosition() {
        assertEquals(0, list.position());
        addNullMatch(2);
        
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
        addNullMatch();
        assertEquals(true, list.hasNext());
        list.next();
        assertEquals(false, list.hasNext());
        addNullMatch();
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
        addNullMatch(5);
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
        addNullMatch(4);
        Match match = new Match(Database.ANY, "a word");
        list.addMatch(match);
        addNullMatch(5);
        
        assertEquals(10, list.count());
        
        list.removeMatch(match);
        
        assertEquals(9, list.count());
    }
    
    
    private void addNullMatch() {
        list.addMatch(new Match(null, null));
    }
    
    private void addNullMatch(int count) {
        for (int i = 1; i <= count; i++) {
            addNullMatch();
        }
    }

}
