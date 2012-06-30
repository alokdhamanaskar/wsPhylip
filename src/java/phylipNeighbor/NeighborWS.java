
package phylipNeighbor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import util.*;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> The class the exposes the methods offered by Neighbor.java as a Web service.
 */

@WebService(
        name            = "wsPhylipNeighbor", 
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/",
        serviceName     = "wsPhylipNeighbor",
        portName        = "wsPhylipNeighborPort"//,
        //wsdlLocation    = "wsPhylipNeighbor.wsdl"
        )
public class NeighborWS { 


    @WebMethod(operationName = "runNeighborDefaultParam")
    @WebResult(name="JobId")
    public String runNeighborDefParam(
                @WebParam(name = "proteinDistanceMatrix") String query, 
                @WebParam(name = "clusteringMethod") String clusteringMethod
                )
                throws UnexpectedErrorEx, ImproperInputEx
    {
        if (query == null) query = "";
        
        if (clusteringMethod == null) clusteringMethod = "";
        
        return Neighbor.runNeighborDefaultParam(query, clusteringMethod);
        
    }//runNeighborDefParam
    
    @WebMethod(operationName = "runNeighbor")
    @WebResult(name="JobId")    
    public String runNeighbor(
            @WebParam(name = "proteinDistanceMatrix") String query, 
            @WebParam(name = "clusteringMethod") String clusteringMethod, 
            @WebParam(name = "USElowerTriangular") Boolean lowerTriangular, 
            @WebParam(name = "USEupperTrangular") Boolean upperTrangular, 
            @WebParam(name = "USEsubReplicates") Boolean subReplicates
            )
            throws UnexpectedErrorEx, ImproperInputEx
    {
 
        if (query == null) query = "";
        
        if (clusteringMethod == null) clusteringMethod = "";
        
        if(lowerTriangular == null)
            lowerTriangular = Boolean.FALSE;
        if(upperTrangular == null)
            upperTrangular = Boolean.FALSE;
        if(subReplicates == null)
            subReplicates = Boolean.FALSE;
        
        return Neighbor.runNeighbor
                (query, clusteringMethod, lowerTriangular.booleanValue(), upperTrangular.booleanValue(), subReplicates.booleanValue());
    
    }//runNeighbor
    
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


    @WebMethod(operationName = "retrieveNeighborResult")
    public util.RunPhylipOutput.PhylipOutput retrieveNeighborResult(@WebParam(name = "jobId") String jobId) 
            throws UnexpectedErrorEx, ErrorRetrievingJob, ImproperInputEx
    {   
        if(jobId == null) throw new ImproperInputEx("Job Id cannot be null");
        
        jobId = jobId.trim();

        if(jobId.equals("")) throw new ImproperInputEx("Job Id cannot be null");        
        
        return RetrieveResults.retrieveResult(jobId);
    }//retrieveNeighborResult 

}//NeighborWS
