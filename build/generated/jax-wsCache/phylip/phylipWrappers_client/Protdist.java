
package phylipWrappers_client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protdist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protdist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="query" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GammaDistrOfRates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CoeffOfVariation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="fracOfInvSites" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="oneCatOfSubRates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noOfCat" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rateForEachCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoriesFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UseWts4Posn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weightsFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="analyzeMultipleDataSets" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataWeights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noOfMultipleDataSets" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="inputSequencesInterleaved" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transitionTransversion" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="baseFreq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProbChangeCat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="geneticCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="catOfAminoAcids" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protdist", propOrder = {
    "query",
    "model",
    "gammaDistrOfRates",
    "coeffOfVariation",
    "fracOfInvSites",
    "oneCatOfSubRates",
    "noOfCat",
    "rateForEachCat",
    "categoriesFile",
    "useWts4Posn",
    "weightsFile",
    "analyzeMultipleDataSets",
    "dataWeights",
    "noOfMultipleDataSets",
    "inputSequencesInterleaved",
    "transitionTransversion",
    "baseFreq",
    "probChangeCat",
    "geneticCode",
    "catOfAminoAcids"
})
public class Protdist {

    protected String query;
    protected String model;
    @XmlElement(name = "GammaDistrOfRates")
    protected String gammaDistrOfRates;
    @XmlElement(name = "CoeffOfVariation")
    protected double coeffOfVariation;
    protected double fracOfInvSites;
    protected String oneCatOfSubRates;
    protected int noOfCat;
    protected String rateForEachCat;
    protected String categoriesFile;
    @XmlElement(name = "UseWts4Posn")
    protected String useWts4Posn;
    protected String weightsFile;
    protected String analyzeMultipleDataSets;
    @XmlElement(name = "DataWeights")
    protected String dataWeights;
    protected int noOfMultipleDataSets;
    protected String inputSequencesInterleaved;
    protected double transitionTransversion;
    protected String baseFreq;
    @XmlElement(name = "ProbChangeCat")
    protected double probChangeCat;
    protected String geneticCode;
    protected String catOfAminoAcids;

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the gammaDistrOfRates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGammaDistrOfRates() {
        return gammaDistrOfRates;
    }

    /**
     * Sets the value of the gammaDistrOfRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGammaDistrOfRates(String value) {
        this.gammaDistrOfRates = value;
    }

    /**
     * Gets the value of the coeffOfVariation property.
     * 
     */
    public double getCoeffOfVariation() {
        return coeffOfVariation;
    }

    /**
     * Sets the value of the coeffOfVariation property.
     * 
     */
    public void setCoeffOfVariation(double value) {
        this.coeffOfVariation = value;
    }

    /**
     * Gets the value of the fracOfInvSites property.
     * 
     */
    public double getFracOfInvSites() {
        return fracOfInvSites;
    }

    /**
     * Sets the value of the fracOfInvSites property.
     * 
     */
    public void setFracOfInvSites(double value) {
        this.fracOfInvSites = value;
    }

    /**
     * Gets the value of the oneCatOfSubRates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOneCatOfSubRates() {
        return oneCatOfSubRates;
    }

    /**
     * Sets the value of the oneCatOfSubRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOneCatOfSubRates(String value) {
        this.oneCatOfSubRates = value;
    }

    /**
     * Gets the value of the noOfCat property.
     * 
     */
    public int getNoOfCat() {
        return noOfCat;
    }

    /**
     * Sets the value of the noOfCat property.
     * 
     */
    public void setNoOfCat(int value) {
        this.noOfCat = value;
    }

    /**
     * Gets the value of the rateForEachCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateForEachCat() {
        return rateForEachCat;
    }

    /**
     * Sets the value of the rateForEachCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateForEachCat(String value) {
        this.rateForEachCat = value;
    }

    /**
     * Gets the value of the categoriesFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriesFile() {
        return categoriesFile;
    }

    /**
     * Sets the value of the categoriesFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriesFile(String value) {
        this.categoriesFile = value;
    }

    /**
     * Gets the value of the useWts4Posn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseWts4Posn() {
        return useWts4Posn;
    }

    /**
     * Sets the value of the useWts4Posn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseWts4Posn(String value) {
        this.useWts4Posn = value;
    }

    /**
     * Gets the value of the weightsFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightsFile() {
        return weightsFile;
    }

    /**
     * Sets the value of the weightsFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightsFile(String value) {
        this.weightsFile = value;
    }

    /**
     * Gets the value of the analyzeMultipleDataSets property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnalyzeMultipleDataSets() {
        return analyzeMultipleDataSets;
    }

    /**
     * Sets the value of the analyzeMultipleDataSets property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnalyzeMultipleDataSets(String value) {
        this.analyzeMultipleDataSets = value;
    }

    /**
     * Gets the value of the dataWeights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataWeights() {
        return dataWeights;
    }

    /**
     * Sets the value of the dataWeights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataWeights(String value) {
        this.dataWeights = value;
    }

    /**
     * Gets the value of the noOfMultipleDataSets property.
     * 
     */
    public int getNoOfMultipleDataSets() {
        return noOfMultipleDataSets;
    }

    /**
     * Sets the value of the noOfMultipleDataSets property.
     * 
     */
    public void setNoOfMultipleDataSets(int value) {
        this.noOfMultipleDataSets = value;
    }

    /**
     * Gets the value of the inputSequencesInterleaved property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputSequencesInterleaved() {
        return inputSequencesInterleaved;
    }

    /**
     * Sets the value of the inputSequencesInterleaved property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputSequencesInterleaved(String value) {
        this.inputSequencesInterleaved = value;
    }

    /**
     * Gets the value of the transitionTransversion property.
     * 
     */
    public double getTransitionTransversion() {
        return transitionTransversion;
    }

    /**
     * Sets the value of the transitionTransversion property.
     * 
     */
    public void setTransitionTransversion(double value) {
        this.transitionTransversion = value;
    }

    /**
     * Gets the value of the baseFreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseFreq() {
        return baseFreq;
    }

    /**
     * Sets the value of the baseFreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseFreq(String value) {
        this.baseFreq = value;
    }

    /**
     * Gets the value of the probChangeCat property.
     * 
     */
    public double getProbChangeCat() {
        return probChangeCat;
    }

    /**
     * Sets the value of the probChangeCat property.
     * 
     */
    public void setProbChangeCat(double value) {
        this.probChangeCat = value;
    }

    /**
     * Gets the value of the geneticCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneticCode() {
        return geneticCode;
    }

    /**
     * Sets the value of the geneticCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneticCode(String value) {
        this.geneticCode = value;
    }

    /**
     * Gets the value of the catOfAminoAcids property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatOfAminoAcids() {
        return catOfAminoAcids;
    }

    /**
     * Sets the value of the catOfAminoAcids property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatOfAminoAcids(String value) {
        this.catOfAminoAcids = value;
    }

}
