/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phylipWrappers;

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

/**
 *
 * @author Alok Dhamanaskar
 */
public class protdist
{
    
    public static String errorMsg="";
    public static String partCodeG = "";
    public static String restCode = "";
    
    public static String protdistDefaultParameters(String query, String model)
    {

        String output = "";
        try
        {
            //Are the inputs entered valid
            if (model.equals(""))
            {
                model = "JTT";
            }
            if (model.equalsIgnoreCase("JTT") || model.equalsIgnoreCase("PMB")
                    || model.equalsIgnoreCase("PAM") || model.equalsIgnoreCase("Kimura")
                    || model.equalsIgnoreCase("Similarity Table")
                    || model.equalsIgnoreCase("Categories model"))
            {
                //Create a new Directory for Current request in tmp folder
                String dirName = "PhylipProtdist:" + UUID.randomUUID().toString();
                String dirNamePath = "tmp/" + dirName;
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
                output = "ERROR: This operation takes only following values for model\n"
                        + "JTT (Jones-Taylor-Thornton matrix), "
                        + "PMB (Henikoff/Tillier PMB matrix), "
                        + "PAM (Dayhoff PAM matrix), "
                        + "kimura, Similarity Table and Categories model";
            }//end else


        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            output = "";

        } finally
        {
            return output;
        }

    }

    public static boolean verify(String model, String GammaDistrOfRates,
            double CoeffOfVariation, double fracOfInvSites, String oneCatOfSubRates,
            int noOfCat, String rateForEachCat, String categoriesFile, String UseWts4Posn, String weightsFile,
            String analyzeMultipleDataSets, String DataWeights,
            int noOfMultipleDataSets, String inputSequencesInterleaved)
    {
        partCodeG += generatePartCode4Model(model);

        if (model.equalsIgnoreCase("JTT") || model.equalsIgnoreCase("PMB")
                || model.equalsIgnoreCase("PAM"))
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
                partCodeG += "d\n";
                if (noOfMultipleDataSets <= 0)
                {
                    errorMsg += "noOfMultipleDataSets : A value greater than 0 must be entered\n";
                } else
                {
                    partCodeG += Integer.toString(noOfMultipleDataSets) + "\n";
                }
            }
            else if(DataWeights.equalsIgnoreCase("w"))
            {
                partCodeG += "w\n";
                if (noOfMultipleDataSets <= 0)
                {
                    errorMsg += "noOfMultipleDataSets : A value greater than 0 must be entered\n";
                } else
                {
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
        
        if(errorMsg.equals(""))
            return true;
        else
            return false;
    }

    public static String protdist(String query, String model, String GammaDistrOfRates,
            double CoeffOfVariation, double fracOfInvSites, String oneCatOfSubRates,
            int noOfCat, String rateForEachCat, String categoriesFile, String UseWts4Posn, String weightsFile,
            String analyzeMultipleDataSets, String DataWeights,
            int noOfMultipleDataSets, String inputSequencesInterleaved)
    {
        String output = "";
        try
        {
            //Are the inputs entered valid
            boolean check =
                    verify(model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                    oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                    analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved);
            if (check)
            {
                //Create a new Directory for Current request in tmp folder
                String dirName = "PhylipProtdist:" + UUID.randomUUID().toString();
                String dirNamePath = "tmp/" + dirName;
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
                output = errorMsg;
            }//end else


        } catch (Exception ex)
        {
            System.out.println("Program failed due to : " + ex);
            output = "";

        } finally
        {
            return output;
        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException
    {

        String query = "";

        FileInputStream fstream = new FileInputStream("/home/alok/Desktop/tmp/PhylipSampleInputs/protdist/infile");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            query += strLine + "\n";
        }
        in.close();

        //System.out.println(query);

        //System.out.println(protdist.protdistDefaultParameters(query, "Categories model"));
        String model="PMB"; 
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
        int noOfMultipleDataSets = 1;
        String inputSequencesInterleaved = "no";
        
        
        System.out.println(
                protdist(query, model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                oneCatOfSubRates, noOfCat, rateForEachCat, categoriesFile, UseWts4Posn, weightsFile,
                analyzeMultipleDataSets, DataWeights, noOfMultipleDataSets, inputSequencesInterleaved)
                );

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
        return partCode;

    }
}
