package phylipWrappers;

import java.io.*;
import java.util.UUID;
import util.PhylipOutput;
import static java.lang.System.out;

/**
 *
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 */
public class Consense
{
    public static class ConsenseOutput
    {
        public String consenseTree;
        public String outTree;
        public String status;
    }
        
    public static String errorMsg="Job Submitted successfully";
    public static String partCodeG = "";

    
    public static PhylipOutput consenseRootedTrees(String query, String consensusType)
    {
        Runtime rt = Runtime.getRuntime();
        String output = "";
        String status = "Job Successfully Submitted";
        PhylipOutput out = new PhylipOutput();
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
            String dirNamePath = util.PropertyFileManager.getValueFromProperty("tmpDir") + dirName;
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

    }

    public static PhylipOutput consenseNonRootedTrees(String query, String consensusType, String OutgroupRoot, int nofOutgroup)
    {
        Runtime rt = Runtime.getRuntime();
        String output = "";
        String status = "Job Successfully Submitted";
        PhylipOutput out = new PhylipOutput();

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
            String dirNamePath = util.PropertyFileManager.getValueFromProperty("tmpDir") + dirName;
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


    }

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
                   
//        PhylipOutput out1 = Consense.consenseNonRootedTrees(query, "strict", "yes",1000);
//        out.println(out1.jobId + "-- " + out1.status);

        PhylipOutput out2 = Consense.consenseRootedTrees(query, "mr");
        out.println(out2.jobId + "-- " + out2.status);

    }
}
