/* FileWriter.java
 *
 * Copyright (c) 2005-2011 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
 * Distributed under GNU Public License.
 */

package wikipedia.util;

import java.io.*;

public class FileWriter {
    
    //public static Encodings     encodings;
    private OutputStreamWriter  osw;
    
    private boolean b_append;
    private String  encode;
    
    private String   dir;            //String          dir = new String("data/graphviz/");
    private String   filename;
    private String   path;
    
    public FileWriter() {
        //encodings = new Encodings();
        encode = "UTF8";            // default value
    }
    
    public void SetDir (String new_dir) {
        dir = new_dir;
    }
    
    public void SetFilename (String new_filename) {
        filename = new_filename;
        path = dir + filename;
    }
    /*public void SetFilenameLatinitsa (String new_filename) {
        filename = StringUtilRegular.encodeRussianToLatinitsa(new_filename);
        path = dir + filename;
    }*/
    public String GetFilename   () { return filename; }
    public String GetPath       () { return path;     }
    public String GetDir        () { return dir;      }

    /*
    public void SetFilenameUTF8 (String new_filename) {
        filename = new String(encodings.Latin1ToUTF8(new_filename));
        path = dir + filename;
    }*/
    
    public void SetAppend (boolean b_new_append) {
        b_append = b_new_append;
    }
    
    public void SetEncode (String new_encode) {
        encode = new_encode;
    }
    
    public void Open() {    
        Open(b_append, encode);
    }
    /** Opens file stream. 
     * @param     b_append  if <code>true</code>, then bytes will be written
     *                      to the end of the file rather than the beginning
    */
    public void Open(boolean b_append, String encode) {
        this.b_append = b_append;
        this.encode   = encode;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(path, b_append),encode);
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
    }
    
    public void delete() {
        File f = new File(path);
        f.delete();
    }
    
    public void Print(String text) {
        try {
            if(null != osw) {
            osw.write(text);}
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
    }
    public void PrintNL(String text) {
        Print(text + "\n");
    }
    
    public void Flush() {
        try {
            if(null != osw) {
            osw.flush();    }
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
    }
    
    /** Get filename without the extension
     */
    public String  GetFilenameWoExt() {
        for(int i=filename.length()-1; i>=0; i--) {
            if('.' == filename.charAt(i)) {
                return new String(filename.substring(0, i));
            }
        }
        return filename;
    }
    
    public long GetFileLength() {
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            return raf.length();
        }
        catch (IOException e) {
            System.out.println("Error opening file: " + path);
        }
        return 0;
    }
    
    
    /** Opens file stream in /user home directory/.synarcher/sub_dir/_filename 
     * in encoding enc. 
     * @param     b_append  if <code>true</code>, then bytes will be written
     *                      to the end of the file rather than the beginning
     * Examples
     *  dump.file_dot.setFileInHomeDir("graphviz", "bat_ruwiki.dot", "Cp866",true);
     *  dump.file_bat.setFileInHomeDir("graphviz", "bat_ruwiki.bat", "Cp866",true);
     *   dump.file_sh.setFileInHomeDir("graphviz", "bat_ruwiki.sh", "Cp1251", true);
    */
    public void setFileInHomeDir(String sub_dir, String _filename,String enc,boolean b_append) {
        
        String fs = System.getProperty("file.separator");
        dir = System.getProperty("user.home") + fs + 
                ".synarcher" + fs + sub_dir + fs;
        
        SetFilename(_filename);
        Open(b_append, enc);
    }

    /** Checks file existance. */
    public static boolean existsFile (String path) {

        File _file = new File(path);
        if (_file != null)
            return _file.exists();
        return false;
    }

    
    /** Creates parent directory for the file with the full 'path',
     * if the directory does not exist. */
    public static void createDir(String path) {
        
        File _file = new File(path);
        if (_file != null) {
            if (_file.exists()) {
                // File already exists, check write access
                if (!_file.canWrite()) {
                    System.out.println("File is not writeable: " + _file.getPath());
                }
            }
            else {
                // File does not yet exist, try to create it
                File parentDir = _file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    boolean created = parentDir.mkdirs();
                    if (!created)
                        System.out.println("FileWriter::createDir Unable to create directory for file " + _file.getPath());
                }
            }
        }
    }

    /** Extracts file with name resource_name from the jar,
     * copies it to the target directory target_dir.
     * 
     * @param target_dir target directory, if it is not exists, then mkdir, 
     *                   e.g. creates `.wiwordik` folder for the
     *                   target_dir=".../.wiwordik/enwikt20101030.sqlite"
     *
     * @see http://www.java2s.com/Code/Java/File-Input-Output/RetreiveTextFileFromJar.htm */
    public static boolean retrieveBinaryFileFromJar(String resource_name,
            String target_dir,Object resource) throws Exception
    {
        boolean found=false;
        if(resource_name != null) {

            FileWriter.createDir(target_dir + File.separator + "test file");
            
            ClassLoader cl = resource.getClass().getClassLoader();
            InputStream is = cl.getResourceAsStream(resource_name);
            if(is == null) throw new Exception ("Error in FileWriter::retrieveBinaryFileFromJarResource "+resource_name+" was not found.");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //FileOutputStream fos = new FileOutputStream(target_dir+File.separator +
            //      resource_name.substring(resource_name.lastIndexOf('/'), resource_name.length()));

            FileOutputStream fos = new FileOutputStream(target_dir+File.separator + resource_name);
            byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.flush();
                br.close();
                is.close();
            found=true;
        } else {
            found=false;
        }
        return found;
  }




    
    
    /* OOOOOOOOOOO LLLLLLLLLLLLLL DDDDDDDD
     */
  /*
     public void Write(String filename, String text, boolean b_append, String encode) {
        try {
            osw = new OutputStreamWriter(new FileOutputStream(filename, b_append),encode);
            osw.write(text);
            osw.flush();
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
    }
    
    public void WriteNew(String filename, String text, String encode) {
        Write(filename, text, false, encode);
    }
    
    public void Append(String filename, String text, String encode) {
        Write(filename, text, true, encode);
    }
 */
}
