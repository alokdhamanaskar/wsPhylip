
package phylipProtDist;

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
 * <br/><br/> The class exposes methods for invoking Phylip ProtDist program.
 */
@WebService(
        name            = "wsPhylipProtDist", 
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/",
        serviceName     = "wsPhylipProtDist",
        portName        = "wsPhylipProtDistPort"//,
        //wsdlLocation    = "wsPhylipProtDist.wsdl"
        )
public class ProtDistWS
{
    @WebMethod(operationName = "protdist")
    public util.PhylipOutput protdist(
                    @WebParam(name = "query") String query, 
                    @WebParam(name = "model") String model, 
                    @WebParam(name = "GammaDistrOfRates") String GammaDistrOfRates,
                    @WebParam(name = "CoeffOfVariation") Double CoeffOfVariation, 
                    @WebParam(name = "fracOfInvSites") Double fracOfInvSites, 
                    @WebParam(name = "oneCatOfSubRates") String oneCatOfSubRates,
                    @WebParam(name = "noOfCat")Integer noOfCat, 
                    @WebParam(name = "rateForEachCat")String rateForEachCat,
                    @WebParam(name = "categoriesFile")String categoriesFile, 
                    @WebParam(name = "UseWts4Posn")String UseWts4Posn, 
                    @WebParam(name = "weightsFile")String weightsFile,
                    @WebParam(name = "analyzeMultipleDataSets")String analyzeMultipleDataSets, 
                    @WebParam(name = "DataWeights")String DataWeights,
                    @WebParam(name = "noOfMultipleDataSets")Integer noOfMultipleDataSets,
                    @WebParam(name = "inputSequencesInterleaved") String inputSequencesInterleaved, 
                    @WebParam(name = "transitionTransversion")Double transitionTransversion, 
                    @WebParam(name = "baseFreq")String baseFreq, 
                    @WebParam(name = "ProbChangeCat")Double ProbChangeCat,
                    @WebParam(name = "geneticCode") String geneticCode, 
                    @WebParam(name = "catOfAminoAcids")String catOfAminoAcids
                    ) 
    {
        if (query == null)
            query = "";
        if (model == null)
            model = "";
        if(GammaDistrOfRates == null)
            GammaDistrOfRates="";
        if(CoeffOfVariation == null)
                CoeffOfVariation=0.00;
        if(fracOfInvSites == null)
            fracOfInvSites = 0.00;
        if(oneCatOfSubRates == null)
            oneCatOfSubRates="";
        if(noOfCat == null)
            noOfCat = 0;
        if(rateForEachCat == null)    
            rateForEachCat="";
        if(categoriesFile == null)
            categoriesFile= "";
        if(UseWts4Posn == null)
            UseWts4Posn= "";
        if(weightsFile == null)
            weightsFile = "";
        if(analyzeMultipleDataSets == null)
            analyzeMultipleDataSets="";
        if(DataWeights == null)
            DataWeights = "";
        if(noOfMultipleDataSets == null)
            noOfMultipleDataSets = 0;
        if(inputSequencesInterleaved == null)
            inputSequencesInterleaved = "";
        if(transitionTransversion == null)
            transitionTransversion = 0.00;
        if(baseFreq == null)
            baseFreq = "";
        if(ProbChangeCat == null)
            ProbChangeCat = 0.00;
        if(geneticCode == null)
            geneticCode="";
        if(catOfAminoAcids==null)
            catOfAminoAcids="";
        
       return Protdist.protdist(query, model, GammaDistrOfRates, CoeffOfVariation.doubleValue(), 
                fracOfInvSites.doubleValue(), oneCatOfSubRates, noOfCat.intValue(), 
                rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets.intValue(), 
                inputSequencesInterleaved, transitionTransversion.doubleValue(),baseFreq, 
                ProbChangeCat.doubleValue(), geneticCode, catOfAminoAcids
                );
    }//protdist

    @WebMethod(operationName = "protdistDefaultParameters")
    public util.PhylipOutput protdistDefaultParameters(
            @WebParam(name = "query") String query, 
            @WebParam(name = "model") String model
            )            
    {
        if (query == null)
            query = "";
        if (model == null)
            model = "";            
        return Protdist.protdistDefaultParameters(query, model);
    }//protdistDefaultParameters

    @WebMethod(operationName = "retrieveProtDistResult")
    public Protdist.ProteinDistOutput RetrieveProtDistResult(@WebParam(name = "jobId") String jobId)
    {   
        if(jobId == null)
            jobId = "";
        Protdist.ProteinDistOutput out  = (Protdist.ProteinDistOutput) RetrieveResults.retrieveResult(jobId);
        return out;
    }//RetrieveProtDistResult

    
    @WebMethod(operationName = "getStatus")
    @WebResult(name = "jobStatus")
    public String getStatus(@WebParam(name = "jobId") String jobId)
    {
        return CheckJobStatus.checkStatus(jobId);
    }//getStatus
    
}