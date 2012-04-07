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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "protdist")
    public String protdist(
                    @WebParam(name = "query") String query, 
                    @WebParam(name = "model") String model, 
                    @WebParam(name = "GammaDistrOfRates") String GammaDistrOfRates,
                    @WebParam(name = "CoeffOfVariation") double CoeffOfVariation, 
                    @WebParam(name = "fracOfInvSites") double fracOfInvSites, 
                    @WebParam(name = "oneCatOfSubRates") String oneCatOfSubRates,
                    @WebParam(name = "noOfCat")int noOfCat, 
                    @WebParam(name = "rateForEachCat")String rateForEachCat,
                    @WebParam(name = "categoriesFile")String categoriesFile, 
                    @WebParam(name = "UseWts4Posn")String UseWts4Posn, 
                    @WebParam(name = "weightsFile")String weightsFile,
                    @WebParam(name = "analyzeMultipleDataSets")String analyzeMultipleDataSets, 
                    @WebParam(name = "DataWeights")String DataWeights,
                    @WebParam(name = "noOfMultipleDataSets")int noOfMultipleDataSets,
                    @WebParam(name = "inputSequencesInterleaved") String inputSequencesInterleaved, 
                    @WebParam(name = "transitionTransversion")double transitionTransversion, 
                    @WebParam(name = "baseFreq")String baseFreq, 
                    @WebParam(name = "ProbChangeCat")double ProbChangeCat,
                    @WebParam(name = "geneticCode") String geneticCode, 
                    @WebParam(name = "catOfAminoAcids")String catOfAminoAcids
                    ) 
    {
        String jobID = protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids
                );

        return jobID;
    }
    

    
    
    
}
