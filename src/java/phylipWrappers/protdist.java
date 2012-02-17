/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phylipWrappers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import util.jobFinished;

/**
 *
 * @author Alok Dhamanaskar
 */
public class protdist
{ 
    
    public static String protdistDefaultParameters(String query)
    {
               Runtime rt = Runtime.getRuntime();
       String output = "";
        try
        {
            
            //Create a new Directory for Current request in tmp folder
            String dirName = "tmp/" + UUID.randomUUID().toString() + "Protdist";            
            boolean success = (new File(dirName)).mkdir();
            if (success) 
                System.out.println("Directory: " + dirName + " created");
                        
            FileWriter f = new FileWriter(dirName + "/query.txt");
            BufferedWriter w = new BufferedWriter(f);
            w.write(query);            
            w.close();
            
            //Create .sh file in the newly created Dir
            String partCode = "Y\n";
           
            String code = "#!/bin/bash\n"
                    + "cd "+ dirName + "\n"
                    + "phylip protdist <<EOD\n"
                    + "query.txt\n"
                    + partCode
                    + "EOD";         

            f = new FileWriter(dirName + "/protdist.sh");
            BufferedWriter ww = new BufferedWriter(f);
            ww.write(code);            
            ww.close();

            Process p = rt.exec("sh "+dirName+"/protdist.sh");

            boolean x = jobFinished.CheckStatusOutfile(dirName);
            System.out.println(x);
            if (!x)
                return "Unable to run Phylip protdist\n Check Your parameters and try again later\n";
            
            FileInputStream fstream = new FileInputStream(dirName + "/outfile");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s="";
            output = "<outFile>\n";
              
            while ((s = br.readLine()) != null)   
            {
               output += s + "\n";
            }
            output += "</outFile>\n";
            in.close();

              
            return output;
            
        } catch (Exception ex)
        {
            return "Program failed due to : " + ex;
        }
        
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        
        String query = "";
        
        FileInputStream fstream = new FileInputStream("/home/alok/Desktop/tmp/PhylipSampleInputs/protdist.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)   
        {
            query += strLine + "\n";
        }
        in.close();
        
        //System.out.println(query);
        
        System.out.println(protdist.protdistDefaultParameters(query));
        

    }
    
}
