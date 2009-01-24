package wikidf;

import java.util.*;
import java.io.*;
import java.net.*;

import gate.*;
import gate.creole.*;
import gate.util.*;
import gate.corpora.RepositioningInfo;


/**
* This class illustrates how to use RussianPOSTagger 
* in another application - put ingredients in one end (URLs pointing
* to documents) and get normalized words (for Russian, Enlish, or Deutch) out the
* other end.
* <P><B>NOTE:</B><BR>
* For simplicity's sake, we don't do any exception handling.
*/
public class StandAloneRussianPOSTagger  {
    private static final boolean DEBUG = false;
    
    /** The Corpus Pipeline application to contain RussianPOSTagger */
    private SerialAnalyserController annieController;
    
    final String PR_NAMES_RPOST [] = {
          "gate.creole.annotdelete.AnnotationDeletePR",
          "gate.creole.tokeniser.DefaultTokeniser",
          //"gate.creole.gazetteer.DefaultGazetteer",
          "gate.creole.splitter.SentenceSplitter",
          //"russian.RuPOSTagger",   //"gate.creole.RussianPOSTagger",
          //"gate.creole.POSTagger",
          //"gate.creole.ANNIETransducer",
          //"gate.creole.orthomatcher.OrthoMatcher",
      };
  
    /**
     * Initialise the ANNIE system. This creates a "corpus pipeline"
     * application that can be used to run sets of documents through
     * the extraction system.
     *
     * @param dict_lang  language of the plugged dictionary (at LemServer)
     */
    public void initPRs(DictLanguage dict_lang) throws GateException {
      Out.prln("Initialising PRs...");
      
      // create a serial analyser controller to run ANNIE with
      annieController =
        (SerialAnalyserController) Factory.createResource(
          "gate.creole.SerialAnalyserController", Factory.newFeatureMap(),
          Factory.newFeatureMap(), "ANNIE_" + Gate.genSym()
        );
  
      // load each PR
      //for(int i = 0; i < ANNIEConstants.PR_NAMES_RPOST.length; i++) {
      for(int i = 0; i < PR_NAMES_RPOST.length; i++) {
        FeatureMap params = Factory.newFeatureMap(); // use default parameters
        ProcessingResource pr = (ProcessingResource)
          Factory.createResource(PR_NAMES_RPOST[i], params);
  
        // add the PR to the pipeline controller
        annieController.add(pr);
      } // for each PR
      
      {
          FeatureMap params = Factory.newFeatureMap();
          params.put("portLemServer", 8000);
          params.put("hostLemServer", "localhost");
          //params.put("hostLemServer", "student.lisa.iias.spb.su");
          params.put("dictLemServer", dict_lang.toString());
      
          ProcessingResource pr = (ProcessingResource)
            Factory.createResource("russian.RuPOSTagger", params);
  
          // add the PR to the pipeline controller
          annieController.add(pr);
      }
  
      Out.prln("...PRs loaded");
    }
    
    public void deletePRs() throws GateException
    {
        int c = PR_NAMES_RPOST.length + 1;
        while(c-- > 0) {
            Collection<gate.Resource> c_prs = annieController.getPRs();
            if(null != c_prs) {
                for(gate.Resource p: c_prs) {
                    Factory.deleteResource(p);
                    break;
                }
            }
        }
        annieController.cleanup();
        Factory.deleteResource(annieController);
    }
            
  
    /** Tell ANNIE's controller about the corpus you want to run on */
    public void setCorpus(Corpus corpus) {
      annieController.setCorpus(corpus);
    } // setCorpus
  
    /** Run ANNIE */
    public void execute() throws GateException {
      if(DEBUG) { Out.prln("Running ANNIE..."); }
      annieController.execute();
      if(DEBUG) { Out.prln("...ANNIE complete"); }
    } // execute()
  
    /**
     * Run from the command-line, with a list of URLs as argument.
     * <P><B>NOTE:</B><BR>
     * This code will run with all the documents in memory - if you
     * want to unload each from memory after use, add code to store
     * the corpus in a DataStore.
     */
    public static void main(String args[])
    throws GateException, IOException {
        
        String[] texts = new String[2];
        // long
        //texts[0] = "file:/mnt/win_e/all/projects/java/aot/gate/russian/embedRPOST/data/en/signatures_en.txt";
        //texts[1] = "file:/mnt/win_e/all/projects/java/aot/gate/russian/embedRPOST/data/en/Common_Sense_Problem_Page.txt";
        // short
        //texts[1] = "file:/mnt/win_e/all/projects/java/aot/gate/russian/embedRPOST/data/en/signatures_en_short.txt";
        //texts[0] = "file:/mnt/win_e/all/projects/java/aot/gate/russian/embedRPOST/data/ru/ABS_zmldks_short.txt";
        texts[0] = "file:../data/ru/ABS_zmldks_short.txt";
        texts[1] = "file:../data/en/signatures_en_short.txt";
        args = texts;
        
      // initialise the GATE library
      Out.prln("Initialising GATE...");
      
      /*
      // Initialize GATE_HOME
      // variant 1) clever way: use of system variable GATE_HOME
      String gateHomeDirPath = System.getProperty("GATE_HOME");     // CONFIG_DIR_PROPERTY);
      if(gateHomeDirPath == null) {
          // variant 2) stupid way: hardcoded setting of the GATE path
          // linux
          gateHomeDirPath = "/opt/GATE-3.1";
          // windows
          //gateHomeDirPath = "C:/Program_Files/tratata.../Your_GATE";
      }
      File gateHomeDir = new File(gateHomeDirPath);
      Gate.setGateHome(gateHomeDir);
       */
      
      DictLanguage dict_lang = DictLanguage.get("ENGLISH"); // RUSSIAN
      Gate.init();
  
      // Load ANNIE and RussianPOSTagger plugins
      File gateHome = Gate.getGateHome();
      File pluginsHome = new File(gateHome, "plugins");
      Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURI().toURL());
      Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "RussianPOSTagger").toURI().toURL());
      Out.prln("...GATE initialised");
 
    // initialise ANNIE (this may take several minutes)
     StandAloneRussianPOSTagger prs = new StandAloneRussianPOSTagger();
     prs.initPRs(dict_lang);
 
     // create a GATE corpus and add a document for each command-line
     // argument
     Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
     for(int i = 0; i < args.length; i++) {
       URL u = new URL(args[i]);
       FeatureMap params = Factory.newFeatureMap();
       params.put("sourceUrl", u);
       params.put("preserveOriginalContent", new Boolean(true));
       params.put("collectRepositioningInfo", new Boolean(true));
       params.put("encoding", "Cp1251");    // widows-1251
       Out.prln("Creating doc for " + u);
       Document doc = (Document)
         Factory.createResource("gate.corpora.DocumentImpl", params);
       corpus.add(doc);
     } // for each of args
 
     // tell the pipeline about the corpus and run it
     prs.setCorpus(corpus);
     prs.execute();
 
     // for each document, get an XML document with the
     // person and location names added
     Iterator iter = corpus.iterator();
     int count = 0;
     String startTagPart_1 = "<span GateID=\"";
     String startTagPart_2 = "\" title=\"";
     String startTagPart_3 = "\" lemma=\"";
     String startTagPart_4 = "\" style=\"background:Red;\">";
     String endTag = "</span>";
 
     while(iter.hasNext()) {
       Document doc = (Document) iter.next();
       AnnotationSet defaultAnnotSet = doc.getAnnotations();
       Set annotTypesRequired = new HashSet();
       annotTypesRequired.add("Paradigm");  // old: Person
       //annotTypesRequired.add("Wordform");  // old: Location
       
       // pw annotation set: Paradigm and Wordform
       AnnotationSet pw = defaultAnnotSet.get(annotTypesRequired);
 
       FeatureMap features = doc.getFeatures();
       String originalContent = (String)
         features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
       RepositioningInfo info = (RepositioningInfo)
         features.get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);
 
       ++count;
       File file = new File("RussianPOSTagger_" + count + ".html");
       Out.prln("File name: '"+file.getAbsolutePath()+"'");
       if(originalContent != null && info != null) {
         Out.prln("OrigContent and reposInfo existing. Generate file...");
 
         Iterator it = pw.iterator();
         Annotation currAnnot;
         SortedAnnotationList sortedAnnotations = new SortedAnnotationList();
 
         while(it.hasNext()) {
           currAnnot = (Annotation) it.next();
           sortedAnnotations.addSortedExclusive(currAnnot);
         } // while
 
         StringBuffer editableContent = new StringBuffer(originalContent);
         long insertPositionEnd;
         long insertPositionStart;
         // insert anotation tags backward
         Out.prln("Unsorted annotations count: "+pw.size());
         Out.prln("Sorted annotations count: "+sortedAnnotations.size());
         for(int i=sortedAnnotations.size()-1; i>=0; --i) {
           //cur_token.getFeatures()
           //String str = "mylemma";
             
           currAnnot = (Annotation) sortedAnnotations.get(i);
           insertPositionStart =
             currAnnot.getStartNode().getOffset().longValue();
           insertPositionStart = info.getOriginalPos(insertPositionStart);
           insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
           insertPositionEnd = info.getOriginalPos(insertPositionEnd, true);
           if(insertPositionEnd != -1 && insertPositionStart != -1) {
             editableContent.insert((int)insertPositionEnd, endTag);
             editableContent.insert((int)insertPositionStart, startTagPart_4);
             //editableContent.insert((int)insertPositionStart,
             //                                              str);
             //editableContent.insert((int)insertPositionStart, startTagPart_3);
             editableContent.insert((int)insertPositionStart,
                                                           currAnnot.getType());
             editableContent.insert((int)insertPositionStart, startTagPart_2);
             editableContent.insert((int)insertPositionStart,
                                                   currAnnot.getId().toString());
             editableContent.insert((int)insertPositionStart, startTagPart_1);
           } // if
         } // for
 
         FileWriter writer = new FileWriter(file);
         writer.write(editableContent.toString());
         writer.close();
       } // if - should generate
       else if (originalContent != null && null != pw) {
             Out.prln("OrigContent existing. Generate file...");
 
             Iterator it = pw.iterator();
             Annotation currAnnot;
             SortedAnnotationList sortedAnnotations = new SortedAnnotationList();
 
             while(it.hasNext()) {
               currAnnot = (Annotation) it.next();
               sortedAnnotations.addSortedExclusive(currAnnot);
             } // while
 
             StringBuffer editableContent = new StringBuffer(originalContent);
             long insertPositionEnd;
             long insertPositionStart;
             // insert anotation tags backward
             Out.prln("Unsorted annotations count: "+pw.size());
             Out.prln("Sorted annotations count: "+sortedAnnotations.size());
             for(int i=sortedAnnotations.size()-1; i>=0; --i) {
               currAnnot = (Annotation) sortedAnnotations.get(i);
               
               FeatureMap f = currAnnot.getFeatures();
               
               //String gc = (String)f.get("gram_codes");
               //String pid = (String)f.get("paradigm_id");
               
               String word = "";
               if(f.containsKey("word")) {
                   word = (String)f.get("word");
                   Out.prln("word : "+word);
               }
               
               String lemma = "";
               if(f.containsKey("lemma")) {
                   lemma = (String)f.get("lemma");
                   Out.prln("lemma : "+lemma);
               }
               
               insertPositionStart =
                 currAnnot.getStartNode().getOffset().longValue();
               insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
               if(insertPositionEnd != -1 && insertPositionStart != -1) {
                 editableContent.insert((int)insertPositionEnd, endTag);
                 
                 editableContent.insert((int)insertPositionStart, startTagPart_4);
                 editableContent.insert((int)insertPositionStart,
                                                               lemma);
                 
                 editableContent.insert((int)insertPositionStart, startTagPart_3);
                 editableContent.insert((int)insertPositionStart,
                                                               currAnnot.getType());
                 editableContent.insert((int)insertPositionStart, startTagPart_2);
                 editableContent.insert((int)insertPositionStart,
                                                       currAnnot.getId().toString());
                 editableContent.insert((int)insertPositionStart, startTagPart_1);
               } // if
             } // for
 
             FileWriter writer = new FileWriter(file);
             writer.write(editableContent.toString());
             writer.close();
       }
       else {
         Out.prln("Content : "+originalContent);
         Out.prln("Repositioning: "+info);
       }
 
       String xmlDocument = doc.toXml(pw, false);
       String fileName = new String("RussianPOSTagger_toXML_" + count + ".xml");
       FileWriter writer = new FileWriter(fileName);
       writer.write(xmlDocument);
       writer.close();
 
       // do something usefull with the XML here!
       Out.prln("'"+xmlDocument+"'");
     } // for each doc
   } // main
 

   public static class SortedAnnotationList extends Vector {
     public SortedAnnotationList() {
       super();
     } // SortedAnnotationList
 
     public boolean addSortedExclusive(Annotation annot) {
       Annotation currAnot = null;
 
       // overlapping check
       for (int i=0; i<size(); ++i) {
         currAnot = (Annotation) get(i);
         if(annot.overlaps(currAnot)) {
           return false;
         } // if
       } // for
 
       long annotStart = annot.getStartNode().getOffset().longValue();
       long currStart;
       // insert
       for (int i=0; i < size(); ++i) {
         currAnot = (Annotation) get(i);
         currStart = currAnot.getStartNode().getOffset().longValue();
         if(annotStart < currStart) {
           insertElementAt(annot, i);
           /*
            Out.prln("Insert start: "+annotStart+" at position: "+i+" size="+size());
            Out.prln("Current start: "+currStart);
            */
           return true;
         } // if
       } // for
 
       int size = size();
       insertElementAt(annot, size);
 //Out.prln("Insert start: "+annotStart+" at size position: "+size);
       return true;
     } // addSorted
   } // SortedAnnotationList
} // class StandAloneRussianPOSTagger