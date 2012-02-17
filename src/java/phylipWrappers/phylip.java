/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phylipWrappers;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Alok Dhamanaskar
 */
@WebService(serviceName = "phylip")
public class phylip
{

    /**
     * Web service operation
     */
    @WebMethod(operationName = "consenseRootedTrees")
    public String consenseRootedTrees(@WebParam(name = "query")
    String query, @WebParam(name = "consensusType")
    String consensusType)
    {
        String output= consense.consenseRootedTrees(query, consensusType);
        return output;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "consenseNonRootedTrees")
    public String consenseNonRootedTrees(@WebParam(name = "query")
    String query, @WebParam(name = "consensusType")
    String consensusType, @WebParam(name = "OutgroupRoot")
    String OutgroupRoot, @WebParam(name = "nofOutgroup")
    int nofOutgroup)
    {
        //            (String query, String consensusType, String OutgroupRoot, int nofOutgroup)
        String output = consense.consenseNonRootedTrees(query, consensusType, OutgroupRoot, nofOutgroup);
        return output;
    }
    
    
    
    
    
}
