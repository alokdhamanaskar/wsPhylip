package phylipWrappers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Alok Dhamanaskar
 */
public class RetrieveResults
{

    public static Object retrieveResult(String dirName)
    {
        String output = "";
        String status = "Success";
        Consense.ConsenseOutput consenseOut = new Consense.ConsenseOutput();
        
        String outcome = CheckJobStatus.CheckStatus(dirName);
        status = outcome;
        if (outcome.equalsIgnoreCase("FINISHED"))
        {
            if (dirName.contains("PhylipConsense"))
            {
                try
                {
                    FileInputStream fstream = new FileInputStream(
                            util.PropertyFileManager.getValueFromProperty("tmpDir")  + dirName + "/outtree");
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    while ((s = br.readLine()) != null)
                    {
                        consenseOut.outTree += s + "\n";
                    }
                    in.close();

                    fstream = new FileInputStream(util.PropertyFileManager.getValueFromProperty("tmpDir") + dirName + "/outfile");
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
                            util.PropertyFileManager.getValueFromProperty("tmpDir") + dirName + "/outfile");
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
        System.out.println(RetrieveResults.retrieveResult("PhylipProtdist:c61e494f-2da2-44e9-9c5b-caf81e7f80d8"));
    }//main ends
}//Class ends

