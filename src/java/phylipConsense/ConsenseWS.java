
package phylipConsense;

import util.CheckJobStatus;
import util.RetrieveResults;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import util.*;

/**
 *
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 * <br/><br/>This class serves as a  Web service interface for the Phylip Consense Program
 * 
 */
@WebService(
        name            = "wsPhylipConsense", 
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/",
        serviceName     = "wsPhylipConsense",
        portName        = "wsPhylipConsensePort",
        wsdlLocation    = "wsPhylipConsense.wsdl"
        )
public class ConsenseWS
{

    @WebMethod(operationName = "consenseRootedTrees")
    public util.RunPhylipOutput consenseRootedTrees(
            @WebParam(name = "query") String query, 
            @WebParam(name = "consensusType") String consensusType
            )
    {
        if (query == null)
            query = "";
        if (consensusType == null )
            consensusType = "";
        
        return Consense.consenseRootedTrees(query, consensusType);
    }//consenseRootedTrees


    @WebMethod(operationName = "consenseNonRootedTrees")
    public util.RunPhylipOutput consenseNonRootedTrees(
            @WebParam(name = "query") String query, 
            @WebParam(name = "consensusType") String consensusType, 
            @WebParam(name = "OutgroupRoot") String OutgroupRoot, 
            @WebParam(name = "nofOutgroup") Integer nofOutgroup
            )
    {
        if (query == null)
            query = "";
        if (consensusType == null)
            consensusType = "";
        if(OutgroupRoot == null)
            OutgroupRoot = "";
        if (nofOutgroup == null)
            nofOutgroup = 0;
        return Consense.consenseNonRootedTrees(query, consensusType, OutgroupRoot, nofOutgroup.intValue());
    }//consenseNonRootedTrees


    @WebMethod(operationName = "retrieveConsenseResult")
    public util.RunPhylipOutput.PhylipOutput RetrieveConsenseDistResult(@WebParam(name = "jobId") String jobId) 
            throws UnexpectedErrorEx, ErrorRetrievingJob, ImproperInputEx
    {   
        if(jobId == null) 
            throw new ImproperInputEx("Job Id cannot be null");
        
        jobId = jobId.trim();

        if(jobId.equals("")) 
            throw new ImproperInputEx("Job Id cannot be null");        

        return RetrieveResults.retrieveResult(jobId);
    }//RetrieveConsenseDistResult    
    
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
    
}