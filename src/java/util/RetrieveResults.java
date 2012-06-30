
package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import util.RunPhylipOutput.PhylipOutput;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> The class the provides methods for retrieving the results of execution of different 
 * Phylip programs
 */
public class RetrieveResults
{

    public static PhylipOutput retrieveResult(String dirName) throws UnexpectedErrorEx, ErrorRetrievingJob
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        PhylipOutput out = new PhylipOutput();
        
        String outcome = CheckJobStatus.checkStatus(dirName);

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
                        out.outTree += s + "\n";
                    }
                    in.close();

                    fstream = new FileInputStream(absolutePath + dirName + "/outfile");
                    in = new DataInputStream(fstream);
                    br = new BufferedReader(new InputStreamReader(in));

                    while ((s = br.readLine()) != null)
                    {
                        out.consenseTree += s + "\n";
                    }
                    in.close();
                    
                    return out;
                }//try
                catch (Exception e)
                {
                    System.out.println("Exception Occurred " + e);
                    throw new UnexpectedErrorEx("The job could not be retrieved..!");
                }//catch
               
            }//if 
            else
            {
                throw new ErrorRetrievingJob("Error retrieving Job : Invalid Job Id");

            }//else
            
        }//if
        else if (outcome.equalsIgnoreCase("ERROR"))
        {
           throw new ErrorRetrievingJob("The job could not be retrieved..!");
        }//else if
        else if (outcome.equalsIgnoreCase("RUNNING"))
        {
            throw new ErrorRetrievingJob("The job is still running..!");
        }//else if
        else if (outcome.equalsIgnoreCase("Job Not Found"))
        {
            throw new ErrorRetrievingJob("The job could not be found..!");
        }//else if
        else
        {
            throw new UnexpectedErrorEx("Unexpected error occurred..!");
        }//else
        
    }//retrieveResult

    
    /**
     * Retrieves the result of Phylip ProtDist given the Job Id
     * @param dirName Job Identifier
     * @return Protein distance Matrix
     * @throws UnexpectedErrorEx
     * @throws ErrorRetrievingJob 
     */
    public static String retrieveProtdistResult(String dirName) 
            throws UnexpectedErrorEx, ErrorRetrievingJob
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        String output = "";
        
        if (dirName == null || dirName.equals(""))
            throw new ErrorRetrievingJob("Job Id cannot be null..!");
        
        String outcome = CheckJobStatus.checkStatus(dirName);

        if (outcome.equalsIgnoreCase("FINISHED"))
        {
            if (dirName.contains("PhylipProtdist"))
            {
                try
                {
                    FileInputStream fstream = new FileInputStream(
                            absolutePath + dirName + "/outfile");
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s;
                    while ((s = br.readLine()) != null)
                    {
                        output += s + "\n";
                    }
                    in.close();

                    return output;
                }//try 
                catch (Exception e)
                {
                    System.out.println("Exception Occurred " + e);
                    throw new UnexpectedErrorEx("The job could not be retrieved..!");
                }//catch

            }//if
            else
            {
                throw new ErrorRetrievingJob("Invalid JobId..!");
            }//else

        } 
        else if (outcome.equalsIgnoreCase("ERROR"))
        {
           throw new ErrorRetrievingJob("The job could not be retrieved..!");
        } 
        else if (outcome.equalsIgnoreCase("RUNNING"))
        {
            throw new ErrorRetrievingJob("The job is still running..!");
        }
        else
        {
            throw new UnexpectedErrorEx("Unexpected error occurred, The job could not be retrieved..!");
        }
        
    }//retrieveProtDistResult
    
    public static void main(String[] args) throws UnexpectedErrorEx, ErrorRetrievingJob
    {
        //Test code
        
        PhylipOutput o = RetrieveResults.retrieveResult("PhylipProtpars:5bb3c0dc-6ee7-406c-9a45-bb296eac8771");
        System.out.println(o.consenseTree + "\n\n" + o.outTree);
        
        System.out.println();
        
        //System.out.println(retrieveProtdistResult("PhylipProtdist:203d6666-92b2-42a6-83ad-4ab079c2848e"));
        
        
    }//main ends
}//Class ends

