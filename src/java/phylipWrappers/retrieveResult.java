/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phylipWrappers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Alok Dhamanaskar
 */
public class retrieveResult
{

    public static String retrieveResult1(String dirName)
    {
        String output = "error";

        String outcome = checkJobStatus.CheckStatus(dirName);
        if (outcome.equalsIgnoreCase("FINISHED"))
        {
            if (dirName.contains("PhylipConsense"))
            {
                try
                {
                    FileInputStream fstream = new FileInputStream("tmp/" + dirName + "/outtree");
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    output = "<outTree>\n";

                    while ((s = br.readLine()) != null)
                    {
                        output += s + "\n";
                    }
                    output += "</outTree>\n";
                    in.close();

                    fstream = new FileInputStream("tmp/" + dirName + "/outfile");
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));
                    s = "";
                    output += "<outFile>\n";

                    while ((s = br.readLine()) != null)
                    {
                        output += s + "\n";
                    }
                    output += "</outFile>\n";
                    in.close();
                } catch (Exception e)
                {
                    System.out.println("Exception Occurred " + e);
                    output = "There was an error running this job";
                }



            } else if (dirName.contains("PhylipProtdist"))
            {
                try
                {
                    FileInputStream fstream = new FileInputStream("tmp/" + dirName + "/outfile");
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    output = "<outFile>\n";

                    while ((s = br.readLine()) != null)
                    {
                        output += s + "\n";
                    }
                    output += "</outFile>\n";
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
            output = "There was an error running this job";
        } else if (outcome.equalsIgnoreCase("RUNNING"))
        {
            output = "The job is still running";
        } else
        {
            output = outcome;
        }

        return output;
    }//method retrieveResult1 ends

    public static void main(String[] args)
    {
        System.out.println(retrieveResult.retrieveResult1("PhylipProtdist:d920bd86-7742-4e28-a1ef-fd7aa45a7144"));
    }//main ends
}//Class ends
