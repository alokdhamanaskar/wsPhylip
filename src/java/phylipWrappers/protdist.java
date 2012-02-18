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
            int noOfCat, String rateForEachCat, String UseWts4Posn, String analyzeMultipleDataSets,
            int noOfMultipleDataSets, String inputSequencesInterleaved)
    {
        String partCode = "";
        String restCode = "";
        String errorMsg = "";
        partCode += generatePartCode4Model(model);

        if (model.equalsIgnoreCase("JTT") || model.equalsIgnoreCase("PMB")
                || model.equalsIgnoreCase("PAM"))
        {
            if (GammaDistrOfRates.equalsIgnoreCase("no") || GammaDistrOfRates.equalsIgnoreCase("false"))
            {
                CoeffOfVariation = 0;
                fracOfInvSites = 0;
            } else if (GammaDistrOfRates.equalsIgnoreCase("yes") || GammaDistrOfRates.equalsIgnoreCase("true"))
            {
                if (CoeffOfVariation <= 0)
                {
                    errorMsg += "CoeffOfVariation : Coefficient of variation of substitution rate among sites (must be positive) In gamma distribution parameters, this is 1/(square root of alpha)\n";
                } else
                {
                    partCode += "G\n";
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
                    partCode += "G\nG\n";
                    restCode += Double.toString(CoeffOfVariation) + "\n";
                    restCode += Double.toString(fracOfInvSites) + "\n";
                }
            } else
            {
                errorMsg += "GammaDistrOfRates: Gamma distribution of rates among positions, takes only following values\n"
                        + "yes, no or Gamma+Invariant\n";
            }

        }//end GammaDistrOfRates
        //--------------------------------------------------------
        //begin One category of substitution rates
        //if (oneCatOfSubRates.equals("")) 
            oneCatOfSubRates="yes";
        if (oneCatOfSubRates.equalsIgnoreCase("yes") || oneCatOfSubRates.equalsIgnoreCase("true"))
        {
            noOfCat = 0;
            rateForEachCat = "";
        } else if (oneCatOfSubRates.equalsIgnoreCase("no") || oneCatOfSubRates.equalsIgnoreCase("false"))
        {
            rateForEachCat.trim();
            String[] s = rateForEachCat.split(" ");
            if (noOfCat < 1 || noOfCat > 9)
            {
                errorMsg += "noOfCat : Number of categories, should be between 1-9 (inclusive of 1 and 9)\n";
            } else if (s.length != noOfCat)
            {
                errorMsg += "rateForEachCat : Rate for each category\n"
                        + "Please enter exactly number of values separated bya a space\n";
            } else
            {
                partCode += "C\n" + Integer.toString(noOfCat)
                        + "\n" + rateForEachCat
                        + "\n";
            }

        } else
        {
            errorMsg += "oneCatOfSubRates: One category of substitution rates:, takes only following values\n"
                    + "yes or no\n";
        }
        //end One category of substitution rates
        //--------------------------------------------------------
        // begin wights for position
        //if (UseWts4Posn.equals("")) 
            UseWts4Posn="no" ;
        if (UseWts4Posn.equalsIgnoreCase("no") || UseWts4Posn.equalsIgnoreCase("false"))
        {
            //cool
        }
        else if (UseWts4Posn.equalsIgnoreCase("yes") || UseWts4Posn.equalsIgnoreCase("true"))
        {
            //to do, allow specifing weights, requires storing them in a file
            errorMsg += "UseWts4Posn : Currently we do not support weights for positions\n";
        }
        else
        {
            errorMsg += "UseWts4Posn : Currently we do not support weights for positions\n";        
        }        
        // begin wights for position
        //--------------------------------------------------------        
        
        return true;
    }

    public static String protdist(String query, String model, String GammaDistrOfRates,
            double CoeffOfVariation, double fracOfInvSites, String oneCatOfSubRates,
            int noOfCat, String rateForEachCat, String UseWts4Posn, String analyzeMultipleDataSets,
            int noOfMultipleDataSets, String inputSequencesInterleaved)
    {
        String output = "";
        try
        {
            //Are the inputs entered valid
            boolean check =
                    verify(model, GammaDistrOfRates, CoeffOfVariation, fracOfInvSites,
                    oneCatOfSubRates, noOfCat, rateForEachCat, UseWts4Posn,
                    analyzeMultipleDataSets, noOfMultipleDataSets, inputSequencesInterleaved);
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

                //Create .sh file in the newly created Dir
                String partCode = generatePartCode();

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

    public static void main(String[] args) throws FileNotFoundException, IOException
    {

        String query = "";

        FileInputStream fstream = new FileInputStream("/home/alok/Desktop/tmp/PhylipSampleInputs/protdist.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            query += strLine + "\n";
        }
        in.close();

        //System.out.println(query);

        System.out.println(protdist.protdistDefaultParameters(query, "Categories model"));


    }

    private static String generatePartCode()
    {
        throw new UnsupportedOperationException("Not yet implemented");
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
