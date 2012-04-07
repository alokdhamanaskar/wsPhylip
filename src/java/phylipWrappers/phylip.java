/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phylipWrappers;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.DefaultValue;

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
                    @WebParam(name = "query") @DefaultValue("") String query, 
                    @WebParam(name = "model") @DefaultValue("") String model, 
                    @WebParam(name = "GammaDistrOfRates") @DefaultValue("") String GammaDistrOfRates,
                    @WebParam(name = "CoeffOfVariation") @DefaultValue("0.00") double CoeffOfVariation, 
                    @WebParam(name = "fracOfInvSites") @DefaultValue("0.00") double fracOfInvSites, 
                    @WebParam(name = "oneCatOfSubRates") @DefaultValue("") String oneCatOfSubRates,
                    @WebParam(name = "noOfCat") @DefaultValue("0") int noOfCat, 
                    @WebParam(name = "rateForEachCat") @DefaultValue("") String rateForEachCat,
                    @WebParam(name = "categoriesFile") @DefaultValue("") String categoriesFile, 
                    @WebParam(name = "UseWts4Posn") @DefaultValue("") String UseWts4Posn, 
                    @WebParam(name = "weightsFile") @DefaultValue("") String weightsFile,
                    @WebParam(name = "analyzeMultipleDataSets") @DefaultValue("") String analyzeMultipleDataSets, 
                    @WebParam(name = "DataWeights") @DefaultValue("") String DataWeights,
                    @WebParam(name = "noOfMultipleDataSets") @DefaultValue("0") int noOfMultipleDataSets,
                    @WebParam(name = "inputSequencesInterleaved") @DefaultValue("") String inputSequencesInterleaved, 
                    @WebParam(name = "transitionTransversion") @DefaultValue("0.00") double transitionTransversion, 
                    @WebParam(name = "baseFreq") @DefaultValue("") String baseFreq, 
                    @WebParam(name = "ProbChangeCat") @DefaultValue("0.00") double ProbChangeCat,
                    @WebParam(name = "geneticCode") @DefaultValue("") String geneticCode, 
                    @WebParam(name = "catOfAminoAcids") @DefaultValue("") String catOfAminoAcids
                    ) 
    {

//        double fracOfInvSites = .5;
//        String oneCatOfSubRates = "no";
//        int noOfCat = 1;
//        String rateForEachCat = "1";
//        String categoriesFile = "1222111112211122";
//        String UseWts4Posn = "";
//        String weightsFile = "";
//        String analyzeMultipleDataSets = "yes";
//        String DataWeights = "d";
//        int noOfMultipleDataSets = 2;
//        String inputSequencesInterleaved = "no";
//        
//        double transitionTransversion =3; 
//        String baseFreq = "1 0 0 0 ";
//        double ProbChangeCat = .1; 
//        String geneticCode = "Yeast mitochondrial"; 
//        String catOfAminoAcids = "Chemical";    
        
        
        
        String jobID = protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids
                );
        
        
        

        return jobID;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "retrieveResult")
    public String retrieveResult(@WebParam(name = "jobId") String jobId) {
        if (jobId == null)
            jobId="";        
        return retrieveResult(jobId);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "protDistDefaultParameters")
    public String protDistDefaultParameters(@WebParam(name = "query") String query, @WebParam(name = "model") String model) {
        
        //String jobId = protdist.protdistDefaultParameters(query, model);
        if (query == null)
            query="";
        if (model == null)
            model="";
        
        return  protdist.protdistDefaultParameters(query, model);
    }
    

    
    
    
}
