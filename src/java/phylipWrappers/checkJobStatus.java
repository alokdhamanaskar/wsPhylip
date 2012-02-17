/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phylipWrappers;

import java.io.File;

/**
 *
 * @author Alok Dhamanaskar
 */
public class checkJobStatus
{

    public static String CheckStatus(String dirName)
    {
        File directory = new File("tmp/" + dirName);
        File outFile = new File("tmp/" + dirName + "/outfile");
        File outTree = new File("tmp/" + dirName + "/outtree");
        String status ="";
        
        boolean exists1 = directory.exists();
        boolean exists2 = outFile.exists();
        boolean exists3 = outTree.exists();
        if (exists1 == false)
            status = "Job Not Found";
        else
        {
            if (dirName.contains("PhylipConsense"))
            {   
                if (exists2 == true && exists3 == true)
                    status = "FINISHED";
                else
                    status = "RUNNING";
            }
            else //if (dirName.contains("PhylipConsense"))
            {
                          status = "SUCCESS";
            }           
        }
        return status;
    }

    public static void main(String args[])
    {
        System.out.println(checkJobStatus.CheckStatus("PhylipConsense:54632459-7ab7-4d01-83d0-95ae9c3c2107"));

    }
}