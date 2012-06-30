
package util;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/> Fault Bean for ImproperInputsFault
 */

public class Fault { 

    //The fault code
    protected String code;

    Fault(String improperInputs) {
        this.code = improperInputs;
    }//Fault

    /**
     * Returns the Fault code
     * @return the code
     */
    public String getCode() {
        return code;
    }//getCode

    /**
     * Sets the fault code.
     * @param code the code to set
     */
    public void setCode(String code1) {
        this.code = code1;
    }//setcode


}