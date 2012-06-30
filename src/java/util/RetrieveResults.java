package util;

import phylipConsense.Consense;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import phylipConsense.Consense.ConsenseOutput;
import phylipProtDist.Protdist;

/**
 *
 * @author Alok Dhamanaskar
 */
public class RetrieveResults
{

    public static Object retrieveResult(String dirName)
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        String output = "";
        String status = "Success";
        Consense.ConsenseOutput consenseOut = new Consense.ConsenseOutput();
        
        String outcome = CheckJobStatus.checkStatus(dirName);
        status = outcome;
        if (outcome.equalsIgnoreCase("FINISHED"))
        {
            if (dirName.contains("PhylipConsense") || dirName.contains("PhylipNeighbor") || dirName.contains("PhylipProtpars"))
            {
                try
                {
                    FileInputStream fstream = new FileInputStream(
                            absolutePath  + dirName + "/outtree");
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    while ((s = br.readLine()) != null)
                    {
                        consenseOut.outTree += s + "\n";
                    }
                    in.close();

                    fstream = new FileInputStream(absolutePath + dirName + "/outfile");
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    s = "";

                    while ((s = br.readLine()) != null)
                    {
                        consenseOut.consenseTree += s + "\n";
                    }
                    in.close();
                    consenseOut.status= status;
                } catch (Exception e)
                {
                    System.out.println("Exception Occurred " + e);
                    consenseOut.status = "There was an error running this job : " + e;
                }
                finally
                {
                    return consenseOut;
                }
               
            } else if (dirName.contains("PhylipProtdist"))
            {
                try
                {
                    FileInputStream fstream = new FileInputStream(
                            absolutePath + dirName + "/outfile");
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    while ((s = br.readLine()) != null)
                    {
                        output += s + "\n";
                    }
                    in.close();

                    in.close();
                } catch (Exception e)
                {
                    System.out.println("Exception Occurred " + e);
                    output = "There was an error running this job";
                }

            }


        } else if (outcome.equalsIgnoreCase("ERROR"))
        {
            status = "There was an error running this job";
        } else if (outcome.equalsIgnoreCase("RUNNING"))
        {
            status = "The job is still running";
        } else
        {
            status = outcome;
        }
        Protdist.ProteinDistOutput out = new Protdist.ProteinDistOutput();
        out.proteinDistanceMatrix = output;
        out.status = status;
        
        return out;
    }//method retrieveResult ends

    public static void main(String[] args)
    {
        ConsenseOutput o = (ConsenseOutput) RetrieveResults.retrieveResult("PhylipNeighbor:22f0a6d9-58d0-427c-b3eb-716ea16ed87d");
        System.out.println(o.consenseTree);
        
        System.out.println(o.outTree);
    }//main ends
}//Class ends

