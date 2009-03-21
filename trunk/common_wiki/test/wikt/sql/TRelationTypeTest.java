
package wikt.sql;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wikipedia.sql.Connect;
import wikt.constant.Relation;

public class TRelationTypeTest {

    public Connect   ruwikt_parsed_conn;
    
    public TRelationTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ruwikt_parsed_conn = new Connect();
        ruwikt_parsed_conn.Open(Connect.RUWIKT_HOST,Connect.RUWIKT_PARSED_DB,Connect.RUWIKT_USER,Connect.RUWIKT_PASS);
    }

    @After
    public void tearDown() {
        ruwikt_parsed_conn.Close();
    }

    @Test
    public void testInsert() {
        System.out.println("insert_ru");
        Connect conn = ruwikt_parsed_conn;
        
        TRelationType r = null;
        System.err.println("One warnings may be...");
        r = TRelationType.get(conn, Relation.synonymy);
        if(null == r) {
            TRelationType.insert(conn, Relation.synonymy);
        }

        // blockhead test
        r = TRelationType.get(conn, null);
        assertTrue(null == r);

        r = TRelationType.get(conn, Relation.synonymy);
        if(null != r)
            TRelationType.delete(conn, Relation.synonymy);

        int id = r.getID();
        assertTrue(id > 0);
    }

    @Test
    public void testGetID() {
        System.out.println("getID");
        
        // once upon a time: create Wiktionary parsed db
        TRelationType.recreateTable(ruwikt_parsed_conn);

        // once upon a time: use Wiktionary parsed db
        TRelationType.createFastMaps(ruwikt_parsed_conn);

        // and every usual day
        int synonymy_id = TRelationType.getIDFast(Relation.synonymy);

        TRelationType rel_type = TRelationType.get(ruwikt_parsed_conn, Relation.synonymy);
        assertEquals(rel_type.getID(), synonymy_id);
    }

    @Test
    public void testRecreateTable() {
        System.out.println("recreateTable, fill table `relation_type`");
        TRelationType.recreateTable(ruwikt_parsed_conn);
    }

}