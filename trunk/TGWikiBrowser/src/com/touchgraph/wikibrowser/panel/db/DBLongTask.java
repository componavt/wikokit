/** DBLongTask.java
 * 
 * Copyright (c) 2005-2007 Andrew Krizhanovsky /aka at mail.iias.spb.su/
 * Distributed under GNU Public License.
 */


package com.touchgraph.wikibrowser.panel.db;

import com.touchgraph.wikibrowser.*;
import wikipedia.sql.*;

/** Calculates statistics about the MySQL wikipedia database
 * Uses a SwingWorker to perform a time-consuming task. */
public class DBLongTask {
    private int lengthOfTask;
    private int current = 0;
    private boolean done = false;
    private boolean canceled = false;
    private String statMessage;
    
    private String          db_name;
    private TGWikiBrowser   wb;

    public DBLongTask() {
        // number of request to db
        lengthOfTask = 5;
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go(String db_host,String db_name,TGWikiBrowser wb) {
        this.db_name = db_name;
        this.wb      = wb;
        
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                canceled = false;
                statMessage = null;
                return new ActualTask();
            }
        };
        worker.start();
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    public int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    public int getCurrent() {
        return current;
    }

    public void stop() {
        canceled = true;
        statMessage = null;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the most recent status message, or null
     * if there is no current status message.
     */
    public String getMessage() {
        return statMessage;
    }

    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            //Starts long task
            if (!canceled && !done) {
                //try {
                    //Thread.sleep(1000); //sleep for a second
                    
                    Connect c = wb.syn_searcher.session.connect;
                                //statMessage  = "Please, wait...";
                                statMessage  = "Wikipedia ('"+ db_name +"') currently has\n  ";
                    
                                statMessage += Statistics.CountArticles(c) + " articles,\n  ";
                    current ++; //make some progress
                    
                                statMessage += Statistics.Count(c, "pagelinks")    + " links,\n  ";
                    current ++; statMessage += Statistics.CountCategories(c)       + " categories,\n  ";
                    current ++; statMessage += Statistics.Count(c, "categorylinks")+ " categorylinks,\n  ";
                    current ++; statMessage += Statistics.Count(c, "image")        + " images,\n  ";
                    current ++; statMessage += Statistics.Count(c, "imagelinks")   + " imagelinks.";
                    
                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }
                    //statMessage = "Completed " + current +
                    //              " out of " + lengthOfTask + ".";
                //} catch (InterruptedException e) {
                //    System.out.println("Task interrupted");
                //}
            }
        }
    }
}
