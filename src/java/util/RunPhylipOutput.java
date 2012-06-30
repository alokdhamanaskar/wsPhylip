
package util;

/**
 *
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 * <br/><br/>Represents Phylip output, in terms of job id and the status for the job submitted.
 */
public class RunPhylipOutput
{
    public String jobId;
    public String status;
    
    public static class PhylipOutput
    {
        public String consenseTree;
        public String outTree;
        
        public PhylipOutput()
        {
            consenseTree = "";
            outTree = "";
        
        }
    }
}