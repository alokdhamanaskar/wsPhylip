package phylipProtDist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import util.GetAbsolutePath;
import util.PhylipOutput;

/**
 * @author Alok Dhamanaskar
 * @see    LICENSE (MIT style license file).
 * 
 */
public class Protdist
{
    public static class ProteinDistOutput
    {
        public String proteinDistanceMatrix;
        public String status;
    }
    
    public static String errorMsg="";
    public static String partCodeG = "";
    public static String restCode = "";
    
    public static PhylipOutput protdistDefaultParameters(String query, String model)
    {       
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        String output = "";
        String status = "Job Successfully Submitted";
        PhylipOutput out = new PhylipOutput();
        try
        {
            if (query.equals(""))
            {
                out.jobId = output;
                status = "Job not Submitted: Query Cannot be Null";
                out.status = status;
                return out;
            }
                    
            if (model.equals(""))
                model = "JTT";
            if (model.equalsIgnoreCase("JTT") || model.equalsIgnoreCase("PMB")
                    || model.equalsIgnoreCase("PAM") || model.equalsIgnoreCase("Kimura")
                    || model.equalsIgnoreCase("Similarity Table")
                    || model.equalsIgnoreCase("Categories model"))
            {
                //Create a new Directory for Current request in tmp folder
                String dirName = "PhylipProtdist:" + UUID.randomUUID().toString();
                String dirNamePath = absolutePath + dirName;
                boolean success = (new File(dirNamePath)).mkdir();
                if (success)
                {
                    System.out.println("Directory: " + dirNamePath + " created");
                } else
                {
                    System.out.println("Directory: " + dirNamePath + " Could not be created");
                }
                //to do in esle throw a user defined exception, so that execution haults beyond this point

                FileWriter f = new FileWriter(dirNamePath + "/query.txt");
                BufferedWriter w = new BufferedWriter(f);
                w.write(query);
                w.close();

                //Create .sh file in the newly created Dir
                String partCode = generatePartCode4Model(model);
                partCode += "Y\n";

                String code = "#!/bin/bash\n"
                        + "cd " + dirNamePath + "\n"
                        + "phylip protdist <<EOD\n"
                        + "query.txt\n"
                        + partCode
                        + "EOD";

                f = new FileWriter(dirNamePath + "/protdist.sh");
                BufferedWriter ww = new BufferedWriter(f);
                ww.write(code);
                ww.close();
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec("sh " + dirNamePath + "/protdist.sh");

                output = dirName;
            }//end if
            else
            {
                errorMsg += "ERROR: This operation takes only following values for model\n"
                        + "JTT (Jones-Taylor-Thornton matrix), "
                        + "PMB (Henikoff/Tillier PMB matrix), "
                        + "PAM (Dayhoff PAM matrix), "
                        + "kimura, Similarity Table and Categories model";
                status = errorMsg;
            }//end else


        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            status = "Program failed due to : " + ex;
            output = "";

        } finally
        {
            out.jobId = output;
            out.status = status;
            return out;
        }

    }

    public static boolean verify(String model, String GammaDistrOfRates,
            double CoeffOfVariation, double fracOfInvSites, String oneCatOfSubRates,
            int noOfCat, String rateForEachCat, String categoriesFile, String UseWts4Posn, String weightsFile,
            String analyzeMultipleDataSets, String DataWeights, int noOfMultipleDataSets, String inputSequencesInterleaved,
            double transitionTransversion, String baseFreq, double ProbChangeCat, String geneticCode, String catOfAminoAcids)
    {
        if (model.equals(""))
            model = "JTT";
        partCodeG += generatePartCode4Model(model);

        if (model.equalsIgnoreCase("JTT") || model.equalsIgnoreCase("PMB")
                || model.equalsIgnoreCase("PAM") || model.equalsIgnoreCase("Categories model"))
        {
            if (GammaDistrOfRates.equalsIgnoreCase(""))
                GammaDistrOfRates="no";
            if (GammaDistrOfRates.equalsIgnoreCase("yes"))
            {
                if (CoeffOfVariation <= 0)
                {
                    errorMsg += "CoeffOfVariation : Coefficient of variation of substitution rate among sites (must be positive) In gamma distribution parameters, this is 1/(square root of alpha)\n";
                } else
                {
                    partCodeG += "G\n";
                    restCode += Double.toString(CoeffOfVariation) + "\n";
                }

                fracOfInvSites = 0;
            } else if (GammaDistrOfRates.equalsIgnoreCase("Gamma+Invariant"))
            {
                int flag = 0;
                if (CoeffOfVariation <= 0)
                {
                    errorMsg += "CoeffOfVariation : Coefficient of variation of substitution rate among sites (must be positive) In gamma distribution parameters, this is 1/(square root of alpha)\n";
                    flag = 1;
                }
                if (fracOfInvSites > 1 || fracOfInvSites <= 0)
                {
                    errorMsg += "fracOfInvSites : Fraction of invariant positions must be a fraction\n";
                    flag = 1;
                }
                if (flag == 0)
                {
                    partCodeG += "G\nG\n";
                    restCode += Double.toString(CoeffOfVariation) + "\n";
                    restCode += Double.toString(fracOfInvSites) + "\n";
                }
            } else if (!GammaDistrOfRates.equalsIgnoreCase("no"))
            {
                errorMsg += "GammaDistrOfRates: Gamma distribution of rates among positions, takes only following values\n"
                        + "yes, no or Gamma+Invariant\n";
            }

        }//end GammaDistrOfRates
        //--------------------------------------------------------
        //begin One category of substitution rates
        if (oneCatOfSubRates.equals("")) 
            oneCatOfSubRates="yes";
        if (oneCatOfSubRates.equalsIgnoreCase("no"))
        {
            rateForEachCat.trim();
            String[] s = rateForEachCat.split(" ");
            if (noOfCat < 1 || noOfCat > 9)
            {
                errorMsg += "noOfCat : Number of categories, should be between 1-9 (inclusive of 1 and 9)\n";
            } else if (s.length != noOfCat)
            {
                errorMsg += "rateForEachCat : Rate for each category\n"
                        + "Please enter exact number of values separated by a space\n";
            } else if(categoriesFile.equals(""))
            {
                errorMsg += "Data should be entered for categories file\n";            
            }
            else
            {
                partCodeG += "C\n" + Integer.toString(noOfCat)
                        + "\n" + rateForEachCat
                        + "\n";
            }

        } else if (!oneCatOfSubRates.equalsIgnoreCase("yes"))
        {
            errorMsg += "oneCatOfSubRates: One category of substitution rates:, takes only following values\n"
                    + "yes or no\n";
        }
        //end One category of substitution rates
        //--------------------------------------------------------
        // begin wights for position
        if (UseWts4Posn.equals("")) 
            UseWts4Posn="no" ;
        if (UseWts4Posn.equalsIgnoreCase("yes"))
        {
            //to do, allow specifing weights, requires storing them in a file
            errorMsg += "UseWts4Posn : Currently we do not support weights for positions\n";
        }
        else if (!UseWts4Posn.equalsIgnoreCase("no"))
        {
            errorMsg += "UseWts4Posn : takes only following values\n"
                     + "yes or no\n";
        }        
        // end wights for position
        //--------------------------------------------------------        
        // begin Analyze multiple datasets
        if (analyzeMultipleDataSets.equalsIgnoreCase(""))
            analyzeMultipleDataSets="no";
        if (analyzeMultipleDataSets.equalsIgnoreCase("yes"))
        {
            if(DataWeights.equalsIgnoreCase("d"))
            {
                if (noOfMultipleDataSets <= 1)
                {
                    errorMsg += "noOfMultipleDataSets : A value greater than 1 must be entered\n";
                } else
                {
                    partCodeG += "m\nd\n";
                    partCodeG += Integer.toString(noOfMultipleDataSets) + "\n";
                }
            }
            else if(DataWeights.equalsIgnoreCase("w"))
            {

                if (noOfMultipleDataSets <= 1)
                {
                    errorMsg += "noOfMultipleDataSets : A value greater than 1 must be entered\n";
                } else
                {
                    partCodeG += "m\nw\n";
                    partCodeG += Integer.toString(noOfMultipleDataSets) + "\n";
                }
            }
            else
               errorMsg += "DataWeights : takes only following values\n"
                     + "D: data or W: weights\n";
        }
        else if (!analyzeMultipleDataSets.equalsIgnoreCase("no"))
        {
               errorMsg += "analyzeMultipleDataSets : takes only following values\n"
                     + "yes or no\n";        
        }
        
        // end Analyze multiple datasets
        //--------------------------------------------------------
        // begin inputSequencesInterleaved
        if (inputSequencesInterleaved.equalsIgnoreCase(""))
            inputSequencesInterleaved = "yes";
        if (inputSequencesInterleaved.equalsIgnoreCase("no"))
        {
            partCodeG += "i\n";
        }
        else if (!inputSequencesInterleaved.equalsIgnoreCase("yes"))
        {
               errorMsg += "inputSequencesInterleaved : takes only following values\n"
                     + "yes or no\n";                
        }
        // endinputSequencesInterleaved
        //--------------------------------------------------------
        //--------------------------------------------------------
        // Special inputs that are part of only Categories model
        if (model.equalsIgnoreCase("Categories model"))
        {
            if(transitionTransversion!=0)
            {
                if(transitionTransversion<0)
                   errorMsg += "transition/Transversion ratio should be greater that 0\n";         
                else
                    partCodeG += "t\n"+ Double.toString(transitionTransversion) + "\n";                
            }

            if(ProbChangeCat!=0)
            {
                if(ProbChangeCat < 0 || ProbChangeCat > 1)
                   errorMsg += "Probability of change category should be between 0 and 1\n";         
                else
                    partCodeG += "E\n"+ Double.toString(ProbChangeCat) + "\n";                
            }
            
            if (!baseFreq.equals(""))
            {
                baseFreq.trim();
                String[] freqs = baseFreq.split(" ");
                if (freqs.length != 4)
                    errorMsg +="Frequencies of bases A,C,G,T MUST SUM TO 1, enter values separated by space\n";
                else
                {
                    int sum = 0 ;
                    try{
                        for (int j =0; j<4 ; j++)
                            sum += Integer.parseInt(freqs[j]);
                        if (sum!= 1)
                            errorMsg +="Frequencies of bases A,C,G,T MUST SUM TO 1, enter values separated by space\n";
                        else
                            partCodeG += "F\n"+ baseFreq + "\n";                
                    }
                    catch(Exception e)
                    {
                        errorMsg +="Frequencies of bases A,C,G,T MUST SUM TO 1, enter values separated by space\n";
                    }
                }
            }// if for BaseFreq ends
            
            if(!geneticCode.equalsIgnoreCase(""))
            {
                if(geneticCode.equalsIgnoreCase("Universal") || geneticCode.equalsIgnoreCase("Mitochondrial") || 
                        geneticCode.equalsIgnoreCase("Vertebrate mitochondrial") || geneticCode.equalsIgnoreCase("Fly mitochondrial") ||
                        geneticCode.equalsIgnoreCase("Yeast mitochondrial"))
                    partCodeG += "U\n"+ geneticCode.charAt(0) + "\n";                                                
                else
                    errorMsg += "geneticCode can take only following values: Universal, Mitochondrial,"
                            + " Vertebrate mitochondrial, Fly mitochondrial, Yeast mitochondrial\n";
            }//if for Genetic code ends
            
            if(!catOfAminoAcids.equalsIgnoreCase(""))
            {
                if(catOfAminoAcids.equalsIgnoreCase("George/Hunt/Barker") || catOfAminoAcids.equalsIgnoreCase("Chemical") ||
                        catOfAminoAcids.equalsIgnoreCase("Hall"))
                    partCodeG += "A\n"+ catOfAminoAcids.charAt(0) + "\n";                                                                
                else
                    errorMsg += "categorization of amino acids can take only following values: "
                            + "George/Hunt/Barker, Chemical, Hall\n";                    
            }
        }//if Categories Model special inputs end
        
        if(errorMsg.equals(""))
            return true;
        else
            return false;
    }

    public static PhylipOutput protdist(String query, String model, String GammaDistrOfRates,
            double CoeffOfVariation, double fracOfInvSites, String oneCatOfSubRates,
            int noOfCat, String rateForEachCat, String categoriesFile, String UseWts4Posn, String weightsFile,
            String analyzeMultipleDataSets, String DataWeights, int noOfMultipleDataSets, String inputSequencesInterleaved, 
            double transitionTransversion, String baseFreq, double ProbChangeCat, String geneticCode, String catOfAminoAcids)
    {
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        
        String output = "";
        String status = "Job Successfully Submitted";
        if (query.equals(""))
        {
            PhylipOutput out = new PhylipOutput();
            out.jobId = output;
            status = "Job not Submitted: Query Cannot be Null";
            out.status = status;
            return out;
        }
        try
        {
            //Are the inputs entered valid
            boolean check =
                    verify(model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                    oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                    analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
                    transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids);
            if (check)
            {
                //Create a new Directory for Current request in tmp folder
                String dirName = "PhylipProtdist:" + UUID.randomUUID().toString();
                String dirNamePath = absolutePath + dirName;
                boolean success = (new File(dirNamePath)).mkdir();
                if (success)
                {
                    System.out.println("Directory: " + dirNamePath + " created");
                } else
                {
                    System.out.println("Directory: " + dirNamePath + " Could not be created");
                }
                //to do in esle throw a user defined exception, so that execution haults beyond this point

                FileWriter f = new FileWriter(dirNamePath + "/query.txt");
                BufferedWriter w = new BufferedWriter(f);
                w.write(query);
                w.close();
                
                if (categoriesFile.length() > 0)
                {
                    f = new FileWriter(dirNamePath + "/categories");
                    BufferedWriter w1 = new BufferedWriter(f);
                    w1.write(categoriesFile);
                    w1.close();

                }
                
                if (weightsFile.length() > 0)
                {
                    f = new FileWriter(dirNamePath + "/weights");
                    BufferedWriter w2 = new BufferedWriter(f);
                    w2.write(weightsFile);
                    w2.close();
                }

                //Create .sh file in the newly created Dir
                String partCode = partCodeG;
                partCode +="y\n";
                partCode += restCode;

                String code = "#!/bin/bash\n"
                        + "cd " + dirNamePath + "\n"
                        + "phylip protdist >>op.txt <<EOD\n"
                        + "query.txt\n"
                        + partCode
                        + "EOD";

                f = new FileWriter(dirNamePath + "/protdist.sh");
                BufferedWriter ww = new BufferedWriter(f);
                ww.write(code);
                ww.close();
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec("sh " + dirNamePath + "/protdist.sh");

                output = dirName;
            }//end if
            else
            {
                status = errorMsg;
            }//end else


        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            output = "";
            status = "Program failed due to : " + ex;
        } finally
        {
            PhylipOutput out = new PhylipOutput();
            out.jobId = output;
            out.status = status;
            return out;
        }

    }
    private static String generatePartCode4Model(String model)
    {
        String partCode = "";
        if (model.equalsIgnoreCase("JTT"))
        {
            partCode = "";
        } else if (model.equalsIgnoreCase("PMB"))
        {
            partCode = "P\n";
        } else if (model.equalsIgnoreCase("PAM"))
        {
            partCode = "P\nP\n";
        } else if (model.equalsIgnoreCase("Kimura"))
        {
            partCode = "P\nP\nP\n";
        } else if (model.equalsIgnoreCase("Similarity Table"))
        {
            partCode = "P\nP\nP\nP\n";
        } else if (model.equalsIgnoreCase("Categories model"))
        {
            partCode = "P\nP\nP\nP\nP\n";
        }
        else
        {
            errorMsg += "ERROR: This operation takes only following values for model\n"
                    + "JTT (Jones-Taylor-Thornton matrix), "
                    + "PMB (Henikoff/Tillier PMB matrix), "
                    + "PAM (Dayhoff PAM matrix), "
                    + "kimura, Similarity Table and Categories model";
        }
        return partCode;

    }
    public static void main(String[] args) throws FileNotFoundException, IOException
    {

        String query = "";
        GetAbsolutePath pt = new GetAbsolutePath();
        String absolutePath = pt.getPath();
        FileInputStream fstream = new FileInputStream(absolutePath +"PhylipSampleInputs/protdist.txt");

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            query += strLine + "\n";
        }
        in.close();

        //System.out.println(query);
        //PhylipOutput out = Protdist.protdistDefaultParameters(query, "Categories modeld");
        //PhylipOutput out = Protdist.protdistDefaultParameters("", "Categories modeld");
        //System.out.println(out.jobId + "-- " + out.status);

        String model="Categories model"; 
        String GammaDistrOfRates = "Gamma+Invariant";
        double CoeffOfVariation = 2;
        double fracOfInvSites = .5;
        String oneCatOfSubRates = "no";
        int noOfCat = 1;
        String rateForEachCat = "1";
        String categoriesFile = "1222111112211122";
        String UseWts4Posn = "";
        String weightsFile = "";
        String analyzeMultipleDataSets = "yes";
        String DataWeights = "d";
        int noOfMultipleDataSets = 2;
        String inputSequencesInterleaved = "no";        
        double transitionTransversion =3; 
        String baseFreq = "1 0 0 0 ";
        double ProbChangeCat = .1; 
        String geneticCode = "Yeast mitochondrial"; 
        String catOfAminoAcids = "Chemical";        

        //        PhylipOutput out1 = 
        //                protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
        //                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
        //                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
        //                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids);
        //        System.out.println(out1.jobId + "-- " + out1.status);

        //--------------------------------------------------------------------------------------        
        // Test  2 
        model="PAM"; 
        GammaDistrOfRates = "";
        CoeffOfVariation = 0.00;
        fracOfInvSites = 0.00;
        oneCatOfSubRates = "";
        noOfCat = 0;
        rateForEachCat = "";
        categoriesFile = "";
        UseWts4Posn = "";
        weightsFile = "";
        analyzeMultipleDataSets = "";
        DataWeights = "";
        noOfMultipleDataSets = 0;
        inputSequencesInterleaved = "";
        transitionTransversion = 0; 
        baseFreq = "";
        ProbChangeCat =  0.00; 
        geneticCode = ""; 
        catOfAminoAcids = "";        
        
//        PhylipOutput out1 = 
//                protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
//                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
//                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
//                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids);
//        System.out.println(out1.jobId + "-- " + out1.status);
        
        //--------------------------------------------------------------------------------------        
        // Test  3 
        model="PAM"; 
        GammaDistrOfRates = "no";
        CoeffOfVariation = 0.00;
        fracOfInvSites = 0.00;
        oneCatOfSubRates = "yes";
        noOfCat = 0;
        rateForEachCat = "";
        categoriesFile = "";
        UseWts4Posn = "no";
        weightsFile = "";
        analyzeMultipleDataSets = "no";
        DataWeights = "";
        noOfMultipleDataSets = 0;
        inputSequencesInterleaved = "yes";
        transitionTransversion = 0; 
        baseFreq = "";
        ProbChangeCat =  0.00; 
        geneticCode = ""; 
        catOfAminoAcids = "";        
        
//        PhylipOutput out3 = 
//                protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
//                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
//                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
//                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids);
//        System.out.println(out3.jobId + "-- " + out3.status);

        //--------------------------------------------------------------------------------------        
        // Test  4 
        model="PAM"; 
        GammaDistrOfRates = "Gamma+Invariant";
        CoeffOfVariation = 1;
        fracOfInvSites = .5;
        oneCatOfSubRates = "yes";
        noOfCat = 0;
        rateForEachCat = "";
        categoriesFile = "";
        UseWts4Posn = "no";
        weightsFile = "";
        analyzeMultipleDataSets = "no";
        DataWeights = "";
        noOfMultipleDataSets = 0;
        inputSequencesInterleaved = "yes";
        transitionTransversion = 0; 
        baseFreq = "";
        ProbChangeCat =  0.00; 
        geneticCode = ""; 
        catOfAminoAcids = "";        
        
//        PhylipOutput out4 = 
//                protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
//                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
//                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
//                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids);
//        System.out.println(out4.jobId + "-- " + out4.status);

        //--------------------------------------------------------------------------------------        
        // Test  5 
        model="PAM"; 
        GammaDistrOfRates = "Gamma+Invariant";
        CoeffOfVariation = 1;
        fracOfInvSites = .5;
        oneCatOfSubRates = "no";
        noOfCat = 1;
        rateForEachCat = "1";
        categoriesFile = "1222111112211122";
        UseWts4Posn = "no";
        weightsFile = "";
        analyzeMultipleDataSets = "no";
        DataWeights = "";
        noOfMultipleDataSets = 0;
        inputSequencesInterleaved = "yes";
        transitionTransversion = 0; 
        baseFreq = "";
        ProbChangeCat =  0.00; 
        geneticCode = ""; 
        catOfAminoAcids = "";        
        
        PhylipOutput out5 = 
                protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved,
                transitionTransversion,baseFreq, ProbChangeCat, geneticCode, catOfAminoAcids);
        System.out.println(out5.jobId + "-- " + out5.status);
        
        
    }// main ends

}
