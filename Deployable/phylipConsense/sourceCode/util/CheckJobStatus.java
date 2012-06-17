
package util;

import java.io.File;

/**
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 */
public class CheckJobStatus
{

    /**
     * Returns the status of the job, depending upon whether output files are generated
     * @param dirName Job identifier
     * @return Job Status
     */
    public static String checkStatus(String dirName)
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        File directory = new File(absolutePath + dirName);
        File outFile = new File(absolutePath + dirName + "/outfile");
        File outTree = new File(absolutePath + dirName + "/outtree");
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
            }//if
            else if (dirName.contains("PhylipProtdist"))
            {
                if (exists2 == true )
                    status = "FINISHED";
                else
                    status = "RUNNING";
            }//else           
        }//else
        
        return status;
    }//checkStatus

    public static void main(String args[])
    {
        System.out.println(CheckJobStatus.checkStatus("PhylipConsense:a783e97d-a86c-4a39-9895-4ff6f06365b7"));

    }//main
}