
package phylipProtPars;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import util.*;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/>The class that exposes operations required in invoke Phylip ProtPars 
 * and get the results as Web service operations of wsPhylipProtPars
 */

@WebService(
        name            = "wsPhylipProtPars", 
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/",
        serviceName     = "wsPhylipProtPars",
        portName        = "wsPhylipProtParsPort"//,
        //wsdlLocation    = "wsPhylipProtPars.wsdl"
        )
public class ProtParsWS { 
    
    @WebMethod(operationName = "runProtPars")
    @WebResult(name = "jobId")
    public String runProtPars(
            @WebParam(name = "multipleAlignedSequences") String query,
            @WebParam(name = "geneticCode") String geneticCode,
            @WebParam(name = "threshold") Double threshold
            ) throws UnexpectedErrorEx, ImproperInputEx
    {
        return ProtPars.runProtPars(query, geneticCode, threshold);
    }//runProtPars

    @WebMethod(operationName = "retrieveProtParsResult")
    public util.RunPhylipOutput.PhylipOutput RetrieveProtParsResult(@WebParam(name = "jobId") String jobId) 
            throws UnexpectedErrorEx, ErrorRetrievingJob, ImproperInputEx
    {   
        if(jobId == null) 
            throw new ImproperInputEx("Job Id cannot be null");
        
        jobId = jobId.trim();

        if(jobId.equals("")) 
            throw new ImproperInputEx("Job Id cannot be null");        

        return RetrieveResults.retrieveResult(jobId);
        
    }//RetrieveProtParsResult

    
    @WebMethod(operationName = "getStatus")
    @WebResult(name = "jobStatus")
    public String getStatus(@WebParam(name = "jobId") String jobId) throws ImproperInputEx
    {
        if(jobId == null) 
            throw new ImproperInputEx("Job Id cannot be null");
        
        jobId = jobId.trim();

        if(jobId.equals("")) 
            throw new ImproperInputEx("Job Id cannot be null");        
        return CheckJobStatus.checkStatus(jobId);
    }//getStatus

}//ProtParsWS
