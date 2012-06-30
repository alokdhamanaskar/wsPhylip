package phylipProtPars;

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
 * <br/><br/>Class that wraps the Phylip Protpars program.
 */
public class ProtPars
{   
    
    public static String partCodeG = "";
    

    public static String runProtPars
            (String query, String geneticCode, Double threshold, boolean search4BestTree) 
            throws UnexpectedErrorEx, ImproperInputEx
    {
        //Get ABsolutePath for creating files
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        try
        {
            if (validate( query, search4BestTree, threshold, geneticCode))
            {
                //Create a new Directory for Current request in tmp folder
                String dirName = "PhylipProtpars:" + UUID.randomUUID().toString();
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
                        + "phylip protpars>>op.txt <<EOD\n"
                        + "query.txt\n"
                        + partCode
                        + "EOD";

                f = new FileWriter(dirNamePath + "/protpars.sh");
                BufferedWriter ww = new BufferedWriter(f);
                ww.write(code);
                ww.close();
                Runtime rt = Runtime.getRuntime();
                rt.exec("sh " + dirNamePath + "/protpars.sh");

                return dirName;
            }//end if
            else
                throw new ImproperInputEx();
                //return "Error Occurred: Job not submitted..!!";

        }//try 
        catch (ImproperInputEx e)
        {
            throw new ImproperInputEx(e.getMessage());
        }//catch
        catch (Exception ex)
        {
            out.println("Program failed due to : " + ex);
            throw new UnexpectedErrorEx("Unexpected Exception occurred when invoking phylip Protpars..!");
        }//catch

    }//runNeighbor
  
       
    private static boolean validate
            (String query, boolean search4BestTree, Double threshold, String geneticCode) 
            throws ImproperInputEx 
    {
        if (query == null) query = "";
        query = query.trim();
        if (query.equals("")) 
        {
            throw new ImproperInputEx("Query cannot be null or empty; Protein Distance matrix expected...!");
        }//if
       
        if (geneticCode != null)
        {
            geneticCode = geneticCode.trim();
            if (geneticCode.equals("")) geneticCode = "Universal";

            switch(geneticCode)
            {
                case "Universal": 
                        break;
                case "Mitochondrial" : 
                        partCodeG +="C\nM\n";
                        break;
                case "VertebrateMitochondrial":
                        partCodeG +="C\nV\n";
                        break;
                case "FlyMitochondrial" :
                        partCodeG +="C\nF\n";
                        break;
                case "YeastMitochondrial" :                        
                        partCodeG +="Y\nM\n";
                        break;
                default:    
                    throw new ImproperInputEx("Genetic code accepts only followng values: Universal, "
                            + "Mitochondrial, VertebrateMitochondrial FlyMitochondrial, YeastMitochondrial");
            }//switch
        
        }//if
                    
        if (search4BestTree == false)
            partCodeG += "U\n";
            
        if(threshold != null)
            if (threshold >= 1.0)
                partCodeG += "T\n" + threshold + "\n";
            else
                throw new ImproperInputEx("The threshold should be >= 1");
        
        return true;
        
    }//validate
    

    public static void main(String[] args) throws FileNotFoundException, IOException, UnexpectedErrorEx, ImproperInputEx
    {

        String query = "";
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        FileInputStream fstream = new FileInputStream(absolutePath +"PhylipSampleInputs/protdist.txt");

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            query += strLine + "\n";
        }
        in.close();

        out.println(runProtPars(query, "Mitochondrial", 1.0, true ));
        

        
    }// main ends

}
