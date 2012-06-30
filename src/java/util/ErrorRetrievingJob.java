
package util;

import javax.xml.ws.WebFault;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> Class that represents Exceptions occurred at server-side when Retrieving the results.
 */

@WebFault(
        name = "ErrorRetrievingJob",
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/"
        )
public class ErrorRetrievingJob extends java.lang.Exception { 
    
    private Fault faultInfo;
    
    public ErrorRetrievingJob()
    {
        super(" Error Retrieving Job..!!");
        this.faultInfo = new Fault("FAULT");
    }//ImproperInputEx

    public ErrorRetrievingJob(String ErrMsg)
    {
        super(ErrMsg);
        this.faultInfo = new Fault("FAULT");
    }//ImproperInputEx  
    
    public ErrorRetrievingJob(String errMsg, Fault fi, Throwable cause)
    {
        super(errMsg);
        this.faultInfo = fi;
    }
    
    public ErrorRetrievingJob(String errMsg, Fault fi)
    {
        super(errMsg);
        this.faultInfo = fi;
    }
    
    public Fault getFaultInfo()
    {
        return this.faultInfo;
    }//getFaultInfo
    
    public static void main(String[] args) throws ErrorRetrievingJob
    {
        //Test code
        throw new ErrorRetrievingJob();
  
    }//main

}