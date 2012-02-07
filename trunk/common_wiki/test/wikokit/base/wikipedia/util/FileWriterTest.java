package wikokit.base.wikipedia.util;

import wikokit.base.wikipedia.util.FileWriter;
import junit.framework.*;
import java.io.*;

public class FileWriterTest extends TestCase {
    
    FileWriter  file;
    public FileWriterTest(String testName) {
        super(testName);
    }

    protected void setUp() throws java.lang.Exception {
        file = new FileWriter();
    }

    protected void tearDown() throws java.lang.Exception {
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(FileWriterTest.class);
        
        return suite;
    }
    
    public void testCreateDir() {
        System.out.println("testCreateDir");
        String fs, dir, path_synarcher, path, path_test_kleinberg;
        
        fs = System.getProperty("file.separator");
        dir = System.getProperty("user.home") + fs;
        
        // creates ~/.synarcher/
        path_synarcher = dir + ".synarcher" + fs;
        path = path_synarcher + "test.txt";
        FileWriter.createDir(path);
        
        // creates ~/.synarcher/graphviz/
        path = path_synarcher + "graphviz" + fs + "test.txt";
        FileWriter.createDir(path);
        
        // creates ~/.synarcher/test_kleinberg/
        path_test_kleinberg = path_synarcher + "test_kleinberg" + fs;
        path =  path_test_kleinberg + "test.txt";
        FileWriter.createDir(path);
        
        // creates ~/.synarcher/test_kleinberg/en/
        path_test_kleinberg = path_synarcher + "test_kleinberg" + fs;
        path =  path_test_kleinberg + "en" + fs + "test.txt";
        FileWriter.createDir(path);
        
        // creates ~/.synarcher/test_kleinberg/ru/
        path_test_kleinberg = path_synarcher + "test_kleinberg" + fs;
        path =  path_test_kleinberg + "ru" + fs + "test.txt";
        FileWriter.createDir(path);
    }
    
    
    /**
     * Test of SetDir method, of class wikipedia.FileWriter.
     */
    public void testSetDir() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of SetFilename method, of class wikipedia.FileWriter.
     */
    public void testSetFilename() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of GetFilename method, of class wikipedia.FileWriter.
     */
    public void testGetFilename() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of GetPath method, of class wikipedia.FileWriter.
     */
    public void testGetPath() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of GetDir method, of class wikipedia.FileWriter.
     */
    public void testGetDir() {

        // TODO add your test code below by replacing the default call to fail.
    }


    /**
     * Test of SetAppend method, of class wikipedia.FileWriter.
     */
    public void testSetAppend() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of SetEncode method, of class wikipedia.FileWriter.
     */
    public void testSetEncode() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of Open method, of class wikipedia.FileWriter.
     */
    public void testOpen() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of Print method, of class wikipedia.FileWriter.
     */
    public void testPrint() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of PrintNL method, of class wikipedia.FileWriter.
     */
    public void testPrintNL() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of Flush method, of class wikipedia.FileWriter.
     */
    public void testFlush() {

        // TODO add your test code below by replacing the default call to fail.
    }

    /**
     * Test of GetFilenameWoExt method, of class wikipedia.FileWriter.
     */
    public void testGetFilenameWoExt() {
        file.SetFilename("The_sample");
        assertEquals(    "The_sample", file.GetFilenameWoExt());
        
        file.SetFilename("The_sample.dot");
        assertEquals(    "The_sample", file.GetFilenameWoExt());
        
        file.SetFilename("The_sample.dot.funny.dot");
        assertEquals(    "The_sample.dot.funny", file.GetFilenameWoExt());
        
        file.SetFilename("Яблоко.dot");
        assertEquals("Яблоко", file.GetFilenameWoExt());
    }
     
    
}
