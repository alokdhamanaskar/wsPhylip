package phylipNeighbor;

import java.io.*;
import static java.lang.System.out;
import java.util.UUID;
import util.GetAbsolutePath;
import util.ImproperInputEx;
import util.UnexpectedErrorEx;

/**
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 * <br/><br/>Class that wraps the Phylip Neighbor program.
 */
public class Neighbor
{

    public static class NeighborOutput
    {
        public String outTree;
        public String outFile;
    }//NeighborOutput
    
    public static String partCodeG = "";
    
    /**
     * Invokes Phylip Neighbor program and returns Job identifier, which is the 
     * location of the directory that has the result for Phylip Neighbor program.
     * 
     * @param query Protein distance Matrix calculated using Phylip Protdist
     * @param clusteringMethod Clustering Method Used
     * @param lowerTriangular Boolean value for using / not using lower triangular matrix
     * @param upperTrangular Boolean value for using / not using upper triangular matrix
     * @param subReplicates Boolean value for using / not using subReplicates
     * @return Returns JobID
     * @throws UnexpectedErrorEx
     * @throws ImproperInputEx Unexpected Inputs
     */
    public static String runNeighbor
            (String query, String clusteringMethod, boolean lowerTriangular, boolean upperTrangular, boolean subReplicates) 
            throws UnexpectedErrorEx, ImproperInputEx
    {
        //Get ABsolutePath for creating files
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        try
        {

            if (validate(query, clusteringMethod, lowerTriangular, upperTrangular, subReplicates))
            {
                //Create a new Directory for Current request in tmp folder
                String dirName = "PhylipNeighbor:" + UUID.randomUUID().toString();
                String dirNamePath = absolutePath + dirName;
                boolean success = (new File(dirNamePath)).mkdir();
                if (success)
                {
                    System.out.println("Directory: " + dirNamePath + " created");
                } else
                {
                    System.out.println("Directory: " + dirNamePath + " Could not be created");
                    throw new UnexpectedErrorEx("Unexpected Error cooure when invoking phylip Neighbor..!");
                }//else

                FileWriter f = new FileWriter(dirNamePath + "/query.txt");
                BufferedWriter w = new BufferedWriter(f);
                w.write(query);
                w.close();

                //Create .sh file in the newly created Dir
                String partCode = partCodeG;
                partCode += "Y\n";

                String code = "#!/bin/bash\n"
                        + "cd " + dirNamePath + "\n"
                        + "phylip neighbor <<EOD\n"
                        + "query.txt\n"
                        + partCode
                        + "EOD";

                f = new FileWriter(dirNamePath + "/neighbor.sh");
                BufferedWriter ww = new BufferedWriter(f);
                ww.write(code);
                ww.close();
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec("sh " + dirNamePath + "/neighbor.sh");

                return dirName;
            }//end if
            else
                return "Error Occurred: Job not submitted..!!";

        }//try 
        catch (ImproperInputEx e)
        {
            throw new ImproperInputEx(e.getMessage());
        }//catch
        catch (Exception ex)
        {
            out.println("Program failed due to : " + ex);
            throw new UnexpectedErrorEx("Unexpected Exception occurred when invoking phylip Neighbor..!");
        }//catch

    }//runNeighbor
  
    /**
     * 
     * Invokes Phylip Neighbor program and returns Job identifier, which is the 
     * location of the directory that has the result for Phylip Neighbor program.
     * 
     * @param query Protein distance Matrix calculated using Phylip Protdist
     * @param clusteringMethod Clustering Method Used
     * @return Returns JobID
     * @throws UnexpectedErrorEx
     * @throws ImproperInputEx Unexpected Inputs
     */
    public static String runNeighborDefaultParam(String query, String clusteringMethod) 
            throws UnexpectedErrorEx, ImproperInputEx
    {
        return runNeighbor(query, clusteringMethod, false, false, false);
        
    }//neighborDefaultParameters
    
    /**
     * Validates the inputs for Phylip Neighbor program
     * @param query Protein distance Matrix calculated using Phylip Protdist
     * @param clusteringMethod Clustering Method Used
     * @param lowerTriangular Boolean value for using / not using lower triangular matrix
     * @param upperTrangular Boolean value for using / not using upper triangular matrix
     * @param subReplicates Boolean value for using / not using subReplicates
     * @return If the inputs are valid or not
     * @throws ImproperInputEx 
     */        
    private static boolean validate
            (String query, String clusteringMethod, boolean lowerTriangular, boolean upperTrangular, boolean subReplicates) 
            throws ImproperInputEx 
    {
        if (query == null) query = "";
        query = query.trim();
        if (query.equals("")) 
        {
            throw new ImproperInputEx("Query cannot be null or empty; Protein Distance matrix expected...!");
        }//if
        
        if(clusteringMethod == null) clusteringMethod = "";
        clusteringMethod = clusteringMethod.trim();

        if (clusteringMethod.equalsIgnoreCase("UPGMA"))
        {
            partCodeG += "N\n";
        }
        else if ( (!clusteringMethod.equalsIgnoreCase("Neighbor-joining")) && (!clusteringMethod.equals("")) )
        {
            throw new ImproperInputEx("Phylip Neighbor supprots only two clustering methods: Neighbor-joining, UPGMA");
        }
        
        if (lowerTriangular == true)
            partCodeG += "L\n";

        if (upperTrangular == true)
            partCodeG += "R\n";
        
        if (subReplicates == true)
            partCodeG += "S\n";
        
        return true;
        
    }//validate
    

    public static void main(String[] args) throws FileNotFoundException, IOException, UnexpectedErrorEx, ImproperInputEx
    {

        String query = "";
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        FileInputStream fstream = new FileInputStream(absolutePath +"PhylipSampleInputs/neighbor.txt");

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            query += strLine + "\n";
        }
        in.close();

        //out.println(runNeighbor(query, "Neighbor-joining ", true, false, false));
        
        out.println(runNeighborDefaultParam(query, "neighbor-joining "));

        
    }// main ends

}
