
package phylipWrappers;

import java.io.*;
import java.util.UUID;

/**
 *
 * @author Alok Dhamanaskar
 */
public class Consense
{

    public static String consenseRootedTrees(String query, String consensusType)
    {
       Runtime rt = Runtime.getRuntime();
       String output = "";
        try
        {
            
            //Create a new Directory for Current request in tmp folder
            String dirName = "PhylipConsense:" + UUID.randomUUID().toString();            
            String dirNamePath = "tmp/" + dirName;  
            boolean success = (new File(dirNamePath)).mkdir();
            if (success) 
                System.out.println("Directory: " + dirNamePath + " created");
                        
            FileWriter f = new FileWriter(dirNamePath + "/query.txt");
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
                    + "cd "+ dirNamePath + "\n"
                    + "phylip consense <<EOD\n"
                    + "query.txt\n"
                    + partCode
                    + "EOD";         

            f = new FileWriter(dirNamePath + "/consense.sh");
            BufferedWriter ww = new BufferedWriter(f);
            ww.write(code);            
            ww.close();

            Process p = rt.exec("sh "+dirNamePath+"/consense.sh");
         
            return dirName;
            
        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            return "Unexpected Error occured at server.";
        }
        
    }


    
    public static String consenseNonRootedTrees
            (String query, String consensusType, String OutgroupRoot, int nofOutgroup)
    {
       Runtime rt = Runtime.getRuntime();
        try
        {
            //Create a new Directory for Current request in tmp folder
            String dirName = "PhylipConsense:" + UUID.randomUUID().toString();            
            String dirNamePath = "tmp/" + dirName;            
            boolean success = (new File(dirNamePath)).mkdir();
            if (success) 
                System.out.println("Directory: " + dirNamePath + " created");
            else
            {
                System.out.println("Directory: " + dirNamePath + " Could not be created");
                return "Unexpected Error occured at server.";
            }
                        
            FileWriter f = new FileWriter(dirNamePath + "/query.txt");
            BufferedWriter w = new BufferedWriter(f);
            w.write(query);            
            w.close();
            
            //Create .sh file in the newly created Dir
            String partCode = "";
            
            partCode = Consense.getCodeNonRootedTrees(consensusType, OutgroupRoot, nofOutgroup);
            if (partCode.contains("Error :"))
               return partCode;     
            
            String code = "#!/bin/bash\n"
                    + "cd "+ dirNamePath + "\n"
                    + "phylip consense <<EOD\n"
                    + "query.txt\n"
                    + partCode
                    + "EOD";         

            f = new FileWriter(dirNamePath + "/consense.sh");
            BufferedWriter ww = new BufferedWriter(f);
            ww.write(code);            
            ww.close();

            Process p = rt.exec("sh "+dirNamePath+"/consense.sh");
              
            return dirName;
            
        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            return "Unexpected Error occured at server.";
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
        
        FileInputStream fstream = new FileInputStream("/home/alok/Desktop/tmp/PhylipSampleInputs/consense.txt");
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
        System.out.println(Consense.consenseRootedTrees(query, "mr"));
        //System.out.println(consense.consenseNonRootedTrees(query, "strict", "yes",1));
        

    }
}
