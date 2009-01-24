package wikipedia.kleinberg;

import junit.framework.*;
import wikipedia.sql.*;
import wikipedia.util.*;
import java.util.*;

public class SessionHolderTest extends TestCase {
    
    public SessionHolderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SessionHolderTest.class);
        
        return suite;
    }

    public void testSkipTitle() {
        System.out.println("skipTitle");
        
        String str = "Words_with_spaces";
        SessionHolder sh = new SessionHolder();
        assertEquals(true, sh.skipTitle(str));
    }
    
}
