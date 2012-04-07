
package phylipWrappers_client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.3-b01-
 * Generated source version: 2.2
 * 
 */
@WebService(name = "phylip", targetNamespace = "http://phylipWrappers/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Phylip {


    /**
     * 
     * @param consensusType
     * @param query
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "consenseRootedTrees", targetNamespace = "http://phylipWrappers/", className = "phylipWrappers_client.ConsenseRootedTrees")
    @ResponseWrapper(localName = "consenseRootedTreesResponse", targetNamespace = "http://phylipWrappers/", className = "phylipWrappers_client.ConsenseRootedTreesResponse")
    @Action(input = "http://phylipWrappers/phylip/consenseRootedTreesRequest", output = "http://phylipWrappers/phylip/consenseRootedTreesResponse")
    public String consenseRootedTrees(
        @WebParam(name = "query", targetNamespace = "")
        String query,
        @WebParam(name = "consensusType", targetNamespace = "")
        String consensusType);

    /**
     * 
     * @param nofOutgroup
     * @param consensusType
     * @param outgroupRoot
     * @param query
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "consenseNonRootedTrees", targetNamespace = "http://phylipWrappers/", className = "phylipWrappers_client.ConsenseNonRootedTrees")
    @ResponseWrapper(localName = "consenseNonRootedTreesResponse", targetNamespace = "http://phylipWrappers/", className = "phylipWrappers_client.ConsenseNonRootedTreesResponse")
    @Action(input = "http://phylipWrappers/phylip/consenseNonRootedTreesRequest", output = "http://phylipWrappers/phylip/consenseNonRootedTreesResponse")
    public String consenseNonRootedTrees(
        @WebParam(name = "query", targetNamespace = "")
        String query,
        @WebParam(name = "consensusType", targetNamespace = "")
        String consensusType,
        @WebParam(name = "OutgroupRoot", targetNamespace = "")
        String outgroupRoot,
        @WebParam(name = "nofOutgroup", targetNamespace = "")
        int nofOutgroup);

    /**
     * 
     * @param model
     * @param fracOfInvSites
     * @param dataWeights
     * @param transitionTransversion
     * @param query
     * @param noOfMultipleDataSets
     * @param rateForEachCat
     * @param probChangeCat
     * @param useWts4Posn
     * @param categoriesFile
     * @param inputSequencesInterleaved
     * @param coeffOfVariation
     * @param gammaDistrOfRates
     * @param baseFreq
     * @param geneticCode
     * @param analyzeMultipleDataSets
     * @param catOfAminoAcids
     * @param oneCatOfSubRates
     * @param noOfCat
     * @param weightsFile
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "protdist", targetNamespace = "http://phylipWrappers/", className = "phylipWrappers_client.Protdist")
    @ResponseWrapper(localName = "protdistResponse", targetNamespace = "http://phylipWrappers/", className = "phylipWrappers_client.ProtdistResponse")
    @Action(input = "http://phylipWrappers/phylip/protdistRequest", output = "http://phylipWrappers/phylip/protdistResponse")
    public String protdist(
        @WebParam(name = "query", targetNamespace = "")
        String query,
        @WebParam(name = "model", targetNamespace = "")
        String model,
        @WebParam(name = "GammaDistrOfRates", targetNamespace = "")
        String gammaDistrOfRates,
        @WebParam(name = "CoeffOfVariation", targetNamespace = "")
        double coeffOfVariation,
        @WebParam(name = "fracOfInvSites", targetNamespace = "")
        double fracOfInvSites,
        @WebParam(name = "oneCatOfSubRates", targetNamespace = "")
        String oneCatOfSubRates,
        @WebParam(name = "noOfCat", targetNamespace = "")
        int noOfCat,
        @WebParam(name = "rateForEachCat", targetNamespace = "")
        String rateForEachCat,
        @WebParam(name = "categoriesFile", targetNamespace = "")
        String categoriesFile,
        @WebParam(name = "UseWts4Posn", targetNamespace = "")
        String useWts4Posn,
        @WebParam(name = "weightsFile", targetNamespace = "")
        String weightsFile,
        @WebParam(name = "analyzeMultipleDataSets", targetNamespace = "")
        String analyzeMultipleDataSets,
        @WebParam(name = "DataWeights", targetNamespace = "")
        String dataWeights,
        @WebParam(name = "noOfMultipleDataSets", targetNamespace = "")
        int noOfMultipleDataSets,
        @WebParam(name = "inputSequencesInterleaved", targetNamespace = "")
        String inputSequencesInterleaved,
        @WebParam(name = "transitionTransversion", targetNamespace = "")
        double transitionTransversion,
        @WebParam(name = "baseFreq", targetNamespace = "")
        String baseFreq,
        @WebParam(name = "ProbChangeCat", targetNamespace = "")
        double probChangeCat,
        @WebParam(name = "geneticCode", targetNamespace = "")
        String geneticCode,
        @WebParam(name = "catOfAminoAcids", targetNamespace = "")
        String catOfAminoAcids);

}