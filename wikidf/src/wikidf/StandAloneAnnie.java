/*
 * StandAloneAnnie.java
 */

package wikidf;

import java.util.*;
import java.io.*;
import java.net.*;

import gate.*;
import gate.creole.*;
import gate.util.*;
import gate.corpora.RepositioningInfo;


/**
* This class illustrates how to use ANNIE as a sausage machine
* in another application - put ingredients in one end (URLs pointing
* to documents) and get sausages (e.g. Named Entities) out the
* other end.
* <P><B>NOTE:</B><BR>
* For simplicity's sake, we don't do any exception handling.
*/
public class StandAloneAnnie  {

    /** The Corpus Pipeline application to contain ANNIE */
    private SerialAnalyserController annieController;
  
    /**
     * Initialise the ANNIE system. This creates a "corpus pipeline"
     * application that can be used to run sets of documents through
     * the extraction system.
     */
    public void initAnnie() throws GateException {
      Out.prln("Initialising ANNIE...");
  
      // create a serial analyser controller to run ANNIE with
      annieController =
        (SerialAnalyserController) Factory.createResource(
          "gate.creole.SerialAnalyserController", Factory.newFeatureMap(),
          Factory.newFeatureMap(), "ANNIE_" + Gate.genSym()
        );
  
      // load each PR as defined in ANNIEConstants
      for(int i = 0; i < ANNIEConstants.PR_NAMES.length; i++) {
        FeatureMap params = Factory.newFeatureMap(); // use default parameters
        String stmp = ANNIEConstants.PR_NAMES[i];
        ProcessingResource pr = (ProcessingResource)
          Factory.createResource(ANNIEConstants.PR_NAMES[i], params);
  
        // add the PR to the pipeline controller
        annieController.add(pr);
      } // for each ANNIE PR
  
      Out.prln("...ANNIE loaded");
    } // initAnnie()
  
    /** Tell ANNIE's controller about the corpus you want to run on */
    public void setCorpus(Corpus corpus) {
      annieController.setCorpus(corpus);
    } // setCorpus
  
    /** Run ANNIE */
    public void execute() throws GateException {
      Out.prln("Running ANNIE...");
      annieController.execute();
      Out.prln("...ANNIE complete");
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
        texts[0] = "file:/mnt/win_e/projects/java/aot/rupostagger/data/en/signatures_en_short.txt";
        texts[1] = "file:/mnt/win_e/projects/java/aot/rupostagger/data/ru/ABS_zmldks_short.txt";
        args = texts;
        
      // initialise the GATE library
      Out.prln("Initialising GATE...");
      Gate.init();
  
      // Load ANNIE plugin
      File gateHome = Gate.getGateHome();
      File pluginsHome = new File(gateHome, "plugins");
      Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURI().toURL());
      Out.prln("...GATE initialised");  
 
     // initialise ANNIE (this may take several minutes)
     StandAloneAnnie annie = new StandAloneAnnie();
     annie.initAnnie();
 
     // create a GATE corpus and add a document for each command-line
     // argument
     Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
     for(int i = 0; i < args.length; i++) {
       URL u = new URL(args[i]);
       FeatureMap params = Factory.newFeatureMap();
       params.put("sourceUrl", u);
       params.put("preserveOriginalContent", new Boolean(true));
       params.put("collectRepositioningInfo", new Boolean(true));
       Out.prln("Creating doc for " + u);
       Document doc = (Document)
         Factory.createResource("gate.corpora.DocumentImpl", params);
       corpus.add(doc);
     } // for each of args
 
     // tell the pipeline about the corpus and run it
     annie.setCorpus(corpus);
     annie.execute();
 
     // for each document, get an XML document with the
     // person and location names added
     Iterator iter = corpus.iterator();
     int count = 0;
     String startTagPart_1 = "<span GateID=\"";
     String startTagPart_2 = "\" title=\"";
     String startTagPart_3 = "\" style=\"background:Red;\">";
     String endTag = "</span>";
 
     while(iter.hasNext()) {
       Document doc = (Document) iter.next();
       AnnotationSet defaultAnnotSet = doc.getAnnotations();
       Set annotTypesRequired = new HashSet();
       annotTypesRequired.add("Person");
       annotTypesRequired.add("Location");
       AnnotationSet peopleAndPlaces = defaultAnnotSet.get(annotTypesRequired);
 
       FeatureMap features = doc.getFeatures();
       String originalContent = (String)
         features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
       RepositioningInfo info = (RepositioningInfo)
         features.get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);
 
       ++count;
       File file = new File("StANNIE_" + count + ".HTML");
       Out.prln("File name: '"+file.getAbsolutePath()+"'");
       if(originalContent != null && info != null) {
         Out.prln("OrigContent and reposInfo existing. Generate file...");
 
         Iterator it = peopleAndPlaces.iterator();
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
         Out.prln("Unsorted annotations count: "+peopleAndPlaces.size());
         Out.prln("Sorted annotations count: "+sortedAnnotations.size());
         for(int i=sortedAnnotations.size()-1; i>=0; --i) {
           currAnnot = (Annotation) sortedAnnotations.get(i);
           insertPositionStart =
             currAnnot.getStartNode().getOffset().longValue();
           insertPositionStart = info.getOriginalPos(insertPositionStart);
           insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
           insertPositionEnd = info.getOriginalPos(insertPositionEnd, true);
           if(insertPositionEnd != -1 && insertPositionStart != -1) {
             editableContent.insert((int)insertPositionEnd, endTag);
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
       } // if - should generate
       else if (originalContent != null) {
             Out.prln("OrigContent existing. Generate file...");
 
             Iterator it = peopleAndPlaces.iterator();
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
             Out.prln("Unsorted annotations count: "+peopleAndPlaces.size());
             Out.prln("Sorted annotations count: "+sortedAnnotations.size());
             for(int i=sortedAnnotations.size()-1; i>=0; --i) {
               currAnnot = (Annotation) sortedAnnotations.get(i);
               insertPositionStart =
                 currAnnot.getStartNode().getOffset().longValue();
               insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
               if(insertPositionEnd != -1 && insertPositionStart != -1) {
                 editableContent.insert((int)insertPositionEnd, endTag);
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
 
       String xmlDocument = doc.toXml(peopleAndPlaces, false);
       String fileName = new String("StANNIE_toXML_" + count + ".HTML");
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
} // class StandAloneAnnie