package phylipConsense;

import java.io.*;
import java.util.UUID;
import util.RunPhylipOutput;
import static java.lang.System.out;
import util.GetAbsolutePath;

/**
 *
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 * <br/><br/> Creates the parameters required for running the Phylip Consense program, depending upon User input
 * 
 */
public class Consense
{

    public static String errorMsg="Job Submitted successfully";
    public static String partCodeG = "";
      
    /**
     * Invokes Phylip Consense program for Rooted Trees
     * @param query
     * @param consensusType The consensus type (MRe, strict, MR, Ml)
     * @return A Job identifier for the web service job submitted
     */
    public static RunPhylipOutput consenseRootedTrees(String query, String consensusType)
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        Runtime rt = Runtime.getRuntime();
        String output = "";
        String status = "Job Successfully Submitted";
        RunPhylipOutput out = new RunPhylipOutput();
        if (query.equals(""))
        {
            out.jobId = output;
            status = "Job not Submitted: Query Cannot be Null";
            out.status = status;
            return out;
        }

        try
        {
            //Create a new Directory for Current request in tmp folder
            String dirName = "PhylipConsense:" + UUID.randomUUID().toString();
            String dirNamePath = absolutePath + dirName;
            boolean success = (new File(dirNamePath)).mkdir();
            if (success)
            {
                System.out.println("Directory: " + dirNamePath + " created");
            }

            FileWriter f = new FileWriter(dirNamePath + "/query.txt");
            BufferedWriter w = new BufferedWriter(f);
            w.write(query);
            w.close();

            //Create .sh file in the newly created Dir
            String partCode = "";
            if (consensusType.equalsIgnoreCase("MRe"))
            {
                partCode = "R\nY\n";
            } else if (consensusType.equalsIgnoreCase("Strict"))
            {
                partCode = "C\nR\nY\n";
            } else if (consensusType.equalsIgnoreCase("MR"))
            {
                partCode = "C\nC\nR\nY\n";
            } else if (consensusType.equalsIgnoreCase("Ml"))
            {
                partCode = "C\nC\nC\nR\nY\n0.5\n";
            } else
            {
                status = "consensusType takes only following values:\n"
                        + "MRe : Majority rule (extended)\n, strict,\n MR : Majority rule,\n Ml";
            }

            String code = "#!/bin/bash\n"
                    + "cd " + dirNamePath + "\n"
                    + "phylip consense <<EOD\n"
                    + "query.txt\n"
                    + partCode
                    + "EOD";

            f = new FileWriter(dirNamePath + "/consense.sh");
            BufferedWriter ww = new BufferedWriter(f);
            ww.write(code);
            ww.close();

            Process p = rt.exec("sh " + dirNamePath + "/consense.sh");
            output = dirName;

        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            status = "Unexpected Error occured at server : " + ex;
        } finally
        {
            out.jobId = output;
            out.status = status;
            return out;
        }

    }//consenseRootedTrees

       
    /**
     * Invokes Phylip Consense program for Non-Rooted Trees
     * @param query
     * @param consensusType The consensus type (MRe, strict, MR, Ml)
     * @param OutgroupRoot The Outgroup root
     * @param nofOutgroup Number of OutGroups
     * @return A Job identifier for the web service job submitted
     */
    public static RunPhylipOutput consenseNonRootedTrees(String query, String consensusType, String OutgroupRoot, int nofOutgroup)
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        Runtime rt = Runtime.getRuntime();
        String output = "";
        String status = "Job Successfully Submitted";
        RunPhylipOutput out = new RunPhylipOutput();

        if (query.equals(""))
        {
            out.jobId = output;
            status = "Job not Submitted: Query Cannot be Null";
            out.status = status;
            return out;
        }

        try
        {
            //Create a new Directory for Current request in tmp folder
            String dirName = "PhylipConsense:" + UUID.randomUUID().toString();
            String dirNamePath = absolutePath + dirName;
            boolean success = (new File(dirNamePath)).mkdir();
            if (success)
            {
                System.out.println("Directory: " + dirNamePath + " created");
            }

            FileWriter f = new FileWriter(dirNamePath + "/query.txt");
            BufferedWriter w = new BufferedWriter(f);
            w.write(query);
            w.close();

            //Create .sh file in the newly created Dir
            String partCode = "";

            if (Consense.getCodeNonRootedTrees(consensusType, OutgroupRoot, nofOutgroup))
            {
                partCode = partCodeG;

                String code = "#!/bin/bash\n"
                        + "cd " + dirNamePath + "\n"
                        + "phylip consense <<EOD\n"
                        + "query.txt\n"
                        + partCode
                        + "EOD";

                f = new FileWriter(dirNamePath + "/consense.sh");
                BufferedWriter ww = new BufferedWriter(f);
                ww.write(code);
                ww.close();

                Process p = rt.exec("sh " + dirNamePath + "/consense.sh");
                output = dirName;
            }
            else
                status = errorMsg;

        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            status = "Unexpected Error occured at server: " + ex;
        } finally
        {
            out.jobId = output;
            out.status = status;
            return out;
        }

    }//consenseNonRootedTrees

    /**
     * Generates the code to execute Phylip Consense program for NonRootedTrees.
     * 
     * @param consensusType The consensus type (MRe, strict, MR, Ml)
     * @param OutgroupRoot The Outgroup root
     * @param nofOutgroup Number of OutGroups
     * @return 
     */
    private static boolean getCodeNonRootedTrees(String consensusType, String OutgroupRoot, int nofOutgroup)
    {
        if (consensusType.equalsIgnoreCase("MRe"))
        {
            partCodeG = "";
        } else if (consensusType.equalsIgnoreCase("Strict"))
        {
            partCodeG = "C\n";
        } else if (consensusType.equalsIgnoreCase("MR"))
        {
            partCodeG = "C\nC\n";
        } else if (consensusType.equalsIgnoreCase("Ml"))
        {
            partCodeG = "C\nC\nC\n";
        } else
        {
            errorMsg = "Error : consensusType takes only following values:\n"
                    + "MRe : Majority rule (extended), strict, MR : Majority rule and Ml";
            return false;
        }

        if (OutgroupRoot.equalsIgnoreCase("no"))
        {
            partCodeG += "Y\n";
        } else if (OutgroupRoot.equalsIgnoreCase("yes"))
        {
            if (nofOutgroup > 0)
                partCodeG += "O\n" + nofOutgroup + "\nY\n";
            else
            {
                errorMsg = "Error : The value for nofOutgroup should be greater than 0";                
                return false;
            }

        } else
        {
            errorMsg = "Error : OutgroupRoot, takes only following values: yes, no ";
            return false;
        }

        if (consensusType.equalsIgnoreCase("Ml"))
        {
            partCodeG += "0.5\n";
        }

        return true;
    }//getCodeNonRootedTrees

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //Test code
        String query = "";
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        FileInputStream fstream = new FileInputStream(absolutePath +"PhylipSampleInputs/consense.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            query += strLine + "\n";
        }
        in.close();
                   
        RunPhylipOutput out1 = Consense.consenseNonRootedTrees(query, "strict", "yes",3);
        out.println(out1.jobId + "-- " + out1.status);

//        RunPhylipOutput out2 = Consense.consenseRootedTrees(query, "mr");
//        out.println(out2.jobId + "-- " + out2.status);

    }//main
    
}//Consense
