package wikipedia.experiment;

import junit.framework.*;
//import wikipedia.experiment.MetricSpearman;
//import wikipedia.kleinberg.Article;
//import wikipedia.kleinberg.LinksBaseSet;
import wikipedia.kleinberg.SessionHolder;
import wikipedia.kleinberg.DumpToGraphViz;
import wikipedia.kleinberg.Authorities;
//import wikipedia.kleinberg.DCEL;
import wikipedia.sql.Connect;
import wikipedia.language.Encodings;
import wikipedia.util.StringUtil;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Date;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.HashMap;


public class ValuerTest extends TestCase {
    private static final boolean DEBUG = true;
    
    Authorities             auth;
    Connect                 connect, connect_simple, idfsimplewiki_conn, idfenwiki7_conn, idf_conn; //, connect_ru;
    DumpToGraphViz          dump;
    SessionHolder           session;
    
    public ValuerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        connect = new Connect();
        //connect.Open("enwiki?useUnicode=true&characterEncoding=UTF-8", "javawiki", "");
        //connect.Open("localhost", "enwiki", "javawiki", "");
        //connect.Open(Connect.WP_HOST,       Connect.WP_DB,          Connect.WP_USER,    Connect.WP_PASS);
        connect.Open(Connect.WP_HOST,       Connect.WP_DB,          Connect.WP_USER,    Connect.WP_PASS);
        
        connect_simple = new Connect();
        connect_simple.Open(Connect.WP_HOST,Connect.WP_SIMPLE_DB,   Connect.WP_USER,    Connect.WP_PASS);
        /*
        connect_ru = new Connect();
        //connect_ru.Open("localhost", "ruwiki?useUnicode=false&characterEncoding=ISO8859_1", "javawiki", ""); //Java:MySQL ISO8859_1:latin1
        //connect_ru.Open("localhost", "ruwiki?autoReconnect=true&useUnbufferedInput=false&useUnicode=false&characterEncoding=ISO8859_1", "javawiki", "");
        connect_ru.Open(Connect.WP_RU_HOST, Connect.WP_RU_DB, Connect.WP_RU_USER, Connect.WP_RU_PASS);
        */
        
        idfsimplewiki_conn = new Connect();
        idfsimplewiki_conn.Open(Connect.IDF_SIMPLE_HOST, Connect.IDF_SIMPLE_DB, Connect.IDF_SIMPLE_USER, Connect.IDF_SIMPLE_PASS);
        
        idfenwiki7_conn = new Connect();
        idfenwiki7_conn.Open(Connect.IDF_EN_HOST, Connect.IDF_EN_DB, Connect.IDF_EN_USER, Connect.IDF_EN_PASS);
        
        String f = System.getProperty("user.home") + "/.synarcher/test_kleinberg/graphviz/";
        auth            = new Authorities();
        dump            = new DumpToGraphViz();
        dump.file_dot.SetDir(f);
        
        /*
        dump.file_bat.SetDir(f);
        dump.file_bat.SetFilename("bat_ruwiki.bat");
        dump.file_bat.Open(true, "Cp866");
        
        dump.file_sh.SetDir(f);
        dump.file_sh.SetFilename("bat_ruwiki.sh");
        dump.file_sh.Open(true, "Cp1251");
        */
        
        session = new SessionHolder();
        session.initObjects();
        session.dump = dump;
    }

    protected void tearDown() throws Exception {
        connect.Close();
        connect_simple.Close();
        idfsimplewiki_conn.Close();
        idfenwiki7_conn.Close();
    }

    /**
     * Test of compareSynonyms method, of class wikipedia.experiment.Valuer.
     */
    public void testCompareSynonyms() {
        System.out.println("compareSynonyms");
        
        int     root_set_size, increment, n_synonyms, categories_max_steps;
        String  filename;
        
        float   eps_error;      // error to stop the iteration
        String  directory;
        Encodings e = connect.enc;
        
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                   DateFormat.LONG,
                                   new Locale("en","US"));
        String today = formatter.format(new Date());
        
        boolean b_normal_load = false;  // easy or normal (hard) load
        boolean b_simple = false;       // simple wiki or english wiki
        
        if (b_normal_load) {
            // normal test
            categories_max_steps = 4; //10; //99;
            n_synonyms = 1000;
            eps_error  = 0.001f;

            root_set_size = 200;
            increment = 17;
        } else {
            // easy test
            categories_max_steps = 4; //99;
            n_synonyms = 10;
            eps_error  = 0.01f;

            root_set_size = 3;
            increment = 1;
        }
        
        if (b_simple) {
            idf_conn = idfsimplewiki_conn;
            //directory = "data/synonyms_ru/";  //String filename = "ruwiki_synonyms.txt";
            directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/simple/";
            session.Init(connect_simple, session.category_black_list.en, categories_max_steps);
        } else {
            idf_conn = idfenwiki7_conn;
            //all_words = en_words;
            //directory = "data/synonyms_en/";  //String filename = "enwiki_synonyms.txt";
            directory = System.getProperty("user.home") + "/.synarcher/test_kleinberg/en/";
            session.Init(connect, session.category_black_list.en, categories_max_steps);
        }
        session.skipTitlesWithSpaces(false); // 1
        //session.skipTitlesWithSpaces(true); // 2
        
        session.dump = null;
        
        
        dump.enable_file_dot = false;
        dump.file.SetDir(directory);
        
        dump.file.SetFilename( "wordsim353_AHITS_metric_spf.txt");
        dump.file.Open(true, "Cp1251");
        dump.file.Print("\n\ntime start:" + today + " \n");
        if(DEBUG) {
            
            dump.file.Print("\n" +
                "isct_wo - Number of intersection words, they are synonyms of word1 and word2" + "\n" +
                "Spr_ftr - Spearman footrule" + "\n" +
                "G       - compares two list of words. List (w/o duplicates) can have different length. -1 if String[] is null." + "\n" +
                "t sec   - time sec" + "\n" +
                "syn.len - synonyms.size()" + "\n" + 
                "skipSpa - skipTitlesWithSpaces" + "\n" +
                "stepCat - total_steps_while_categories_removing" + "\n" +
                "root    - root_set_size" + "\n" +
                "incrmnt - increment" + "\n" +
                "human   - human_wordsim" + "\n" +
                "cat_max - categories_max_steps" + "\n");
                    
            dump.file.Print("\n" +
                "word1\t" + "word2\t" + "human\t" + 
               // intersect.length + "\t" + dist_f + "\t" + dist_i + "\t" + 
                "Spr_ftr\t" + "isct_wo\t" + "G\t" +
                "t sec\t" + 
                "cat_max" + "\t" +
                "iter\t" +
                //"vert-s\t" + 
                //"edges\t" + 
                "n_synon\t" + "syn.len\t" + 
                "skipSpa\t" +
                "stepCat\t"+
                "root\t" + "incrmnt\t" +
                "synonyms\n");
        }
        dump.file.Flush();
        
        long    t_start, t_end;
        float   t_work;
        t_start = System.currentTimeMillis();
        int i = 0;
        
        System.out.println ("\nThe words are processing:\n");
        WordSim353 wordsim353= new WordSim353();
        Valuer.absent_counter = 0;
        for(WordSim w:wordsim353.data) {
            i++;
            String word1 = StringUtil.UpperFirstLetter(w.word1);
            String word2 = StringUtil.UpperFirstLetter(w.word2);
            //word1 = "Computer";
            //word2 = "Keyboard";
            
            //System.out.println ("The word Latin1ToUTF8 '"+Encodings.Latin1ToUTF8(all_words[i])+"' is processing...");
            System.out.println (i + ": " + word1 + ", " + word2 + ", categories_max_steps: ");
            
            //for(categories_max_steps = 0; categories_max_steps < 20; categories_max_steps+=2) {
            //    session.category_black_list.setMaxSteps(categories_max_steps);
            //    System.out.println ("categories_max_steps " + categories_max_steps);

                //float result = 
                Valuer.compareSynonyms(word1, word2, w.sim, auth, connect, dump, session, 
                        root_set_size, increment, n_synonyms, categories_max_steps, eps_error,
                        idf_conn);
            //}
            
            //if( i > 7) 
              break;
        }
        
        t_end  = System.currentTimeMillis();
        t_work = (t_end - t_start)/1000f; // in sec
        
        dump.file.Print("\n" + "Total time: " + t_work + "sec.\n");
        dump.file.Print("\n" + "Number of absent data items: " + Valuer.absent_counter + ".\n");
        dump.file.Flush();  
    }
    
}
