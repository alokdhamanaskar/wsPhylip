
package util;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> Class that represents Exceptions occurred at server-side apart from errors due to improper inputs.
 */
public class UnexpectedErrorEx extends java.lang.Exception{ 
    
    public UnexpectedErrorEx(String errMsg)
    {
        super(errMsg);
    }//constructor
    
    public UnexpectedErrorEx()
    {
        super("Unexpected Exception Occurred at Server side..!");
    }//constructor

    public static void main(String[] args) throws UnexpectedErrorEx
    {
        //Test code
        throw new UnexpectedErrorEx("Unexpected Exception Occurred at Server side..!");
    
    }//main

}//UnexpectedErrorEx
