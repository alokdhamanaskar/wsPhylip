
package phylipConsense;

import util.CheckJobStatus;
import util.RetrieveResults;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

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
        portName        = "wsPhylipConsensePort"//,
        //wsdlLocation    = "wsPhylipConsense.wsdl"
        )
public class ConsenseWS
{

    @WebMethod(operationName = "consenseRootedTrees")
    public util.PhylipOutput consenseRootedTrees(
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
    public util.PhylipOutput consenseNonRootedTrees(
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
    public Consense.ConsenseOutput RetrieveConsenseDistResult(@WebParam(name = "jobId") String jobId)
    {   
        if(jobId == null)
            jobId = "";
        Consense.ConsenseOutput out  = (Consense.ConsenseOutput) RetrieveResults.retrieveResult(jobId);
        return out;
    }//RetrieveConsenseDistResult    
    
    @WebMethod(operationName = "getStatus")
    @WebResult(name = "jobStatus")
    public String getStatus(@WebParam(name = "jobId") String jobId) 
    {
        return CheckJobStatus.checkStatus(jobId);
    }//getStatus
    
}