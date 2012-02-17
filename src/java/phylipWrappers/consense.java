
package phylipWrappers;

import java.io.*;
import java.util.UUID;
import util.jobFinished;

/**
 *
 * @author Alok Dhamanaskar
 */
public class consense
{

    public static String consenseRootedTrees(String query, String consensusType)
    {
       Runtime rt = Runtime.getRuntime();
       String output = "";
        try
        {
            
            //Create a new Directory for Current request in tmp folder
            String dirName = "/home/alok/NetBeansProjects/PhylipWS/tmp/" + UUID.randomUUID().toString() + "Consense";            
            boolean success = (new File(dirName)).mkdir();
            if (success) 
                System.out.println("Directory: " + dirName + " created");
                        
            FileWriter f = new FileWriter(dirName + "/query.txt");
            BufferedWriter w = new BufferedWriter(f);
            w.write(query);            
            w.close();
            
            //Create .sh file in the newly created Dir
            String partCode = "";
            if (consensusType.equalsIgnoreCase("MRe"))
                partCode = "R\nY\n";
            else if (consensusType.equalsIgnoreCase("Strict"))
                partCode = "C\nR\nY\n";
            else if (consensusType.equalsIgnoreCase("MR"))
                partCode = "C\nC\nR\nY\n";
            else if (consensusType.equalsIgnoreCase("Ml"))
                partCode = "C\nC\nC\nR\nY\n0.5\n";                
            else
                return "consensusType takes only following values:\n"
                        + "MRe : Majority rule (extended)\n, strict,\n MR : Majority rule,\n Ml";
            
            String code = "#!/bin/bash\n"
                    + "cd "+ dirName + "\n"
                    + "phylip consense <<EOD\n"
                    + "query.txt\n"
                    + partCode
                    + "EOD";         

            f = new FileWriter(dirName + "/consense.sh");
            BufferedWriter ww = new BufferedWriter(f);
            ww.write(code);            
            ww.close();

            Process p = rt.exec("sh "+dirName+"/consense.sh");

            if (!jobFinished.CheckStatus(dirName))
                return "Unable to run Phylip consense\n Check Your parameters and try again later\n";
            
            FileInputStream fstream = new FileInputStream(dirName + "/outtree");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s="";
            output = "<outTree>\n";
              
            while ((s = br.readLine()) != null)   
            {
               output += s + "\n";
            }
                    output += "</outTree>\n";
                    in.close();
                    
                    fstream = new FileInputStream(dirName + "/outfile");
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    s="";
                    output += "<outFile>\n";
                    
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

    
    public static String consenseNonRootedTrees
            (String query, String consensusType, String OutgroupRoot, int nofOutgroup)
    {
       
        
       Runtime rt = Runtime.getRuntime();
       String output = "";
        try
        {
            
            //Create a new Directory for Current request in tmp folder
            String dirName = "/home/alok/NetBeansProjects/PhylipWS/tmp/" + UUID.randomUUID().toString() + "Consense";            
            boolean success = (new File(dirName)).mkdir();
            if (success) 
                System.out.println("Directory: " + dirName + " created");
                        
            FileWriter f = new FileWriter(dirName + "/query.txt");
            BufferedWriter w = new BufferedWriter(f);
            w.write(query);            
            w.close();
            
            //Create .sh file in the newly created Dir
            String partCode = "";

            
            partCode = consense.getCodeNonRootedTrees(consensusType, OutgroupRoot, nofOutgroup);
            if (partCode.contains("Error :"))
               return partCode;     
            
            String code = "#!/bin/bash\n"
                    + "cd "+ dirName + "\n"
                    + "phylip consense <<EOD\n"
                    + "query.txt\n"
                    + partCode
                    + "EOD";         

            f = new FileWriter(dirName + "/consense.sh");
            BufferedWriter ww = new BufferedWriter(f);
            ww.write(code);            
            ww.close();

            Process p = rt.exec("sh "+dirName+"/consense.sh");

            if (!jobFinished.CheckStatus(dirName))
                return "Unable to run Phylip consense \n Check Your parameters and try again later\n";

            FileInputStream fstream = new FileInputStream(dirName + "/outtree");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s="";
            output = "<outTree>\n";
              
            while ((s = br.readLine()) != null)   
            {
               output += s + "\n";
            }
                    output += "</outTree>\n";
                    in.close();
                    
                    fstream = new FileInputStream(dirName + "/outfile");
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    s="";
                    output += "<outFile>\n";
                    
                    while ((s = br.readLine()) != null)   
                    {
                        output += s + "\n";
                    }
                    output += "</outFree>\n";
                    in.close();
              
            return output;
            
        } catch (Exception ex)
        {
            return "Program failed due to : " + ex;
        }
        
        
    }

    
        private static String getCodeNonRootedTrees(String consensusType, String OutgroupRoot, int nofOutgroup)
    {
        String partCode = "";
        if (consensusType.equalsIgnoreCase("MRe"))
            partCode = "";
        else if (consensusType.equalsIgnoreCase("Strict"))
            partCode = "C\n";
        else if (consensusType.equalsIgnoreCase("MR"))
            partCode = "C\nC\n";
        else if (consensusType.equalsIgnoreCase("Ml"))
                partCode = "C\nC\nC\n";                
        else
                return "Error : consensusType takes only following values:\n"
                        + "MRe : Majority rule (extended), strict, MR : Majority rule and Ml";

        if (OutgroupRoot.equalsIgnoreCase("no"))
            partCode += "Y\n";
        else if (OutgroupRoot.equalsIgnoreCase("yes"))
        {
            if (nofOutgroup > 0)
                partCode += "O\n" + nofOutgroup + "\nY\n";
            else 
                return "Error : The value for nofOutgroup should be greater than 0";
        }
        else
            return "Error : OutgroupRoot, takes only following values: yes, no ";
        
        if(consensusType.equalsIgnoreCase("Ml"))
        {
               partCode +="0.5\n";
        }
        
        return partCode;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        
        String query = "";
        
        FileInputStream fstream = new FileInputStream("/home/alok/Desktop/PhylipSampleInputs/consense.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)   
        {
            query += strLine + "\n";
        }
        in.close();
        
        //System.out.println(query);
        
        //System.out.println(consense.consenseRootedTrees(query, "Strict"));
        //System.out.println(consense.consenseRootedTrees(query, "mr"));
        System.out.println(consense.consenseNonRootedTrees(query, "strict", "yes",1));
        

    }
}
