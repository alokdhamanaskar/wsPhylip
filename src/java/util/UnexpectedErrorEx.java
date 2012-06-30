
package util;

import javax.xml.ws.WebFault;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> Class that represents Exceptions occurred at server-side apart from errors due to improper inputs.
 */
@WebFault(
        name = "UnexpectedErrorFault",
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/"
        )
public class UnexpectedErrorEx extends java.lang.Exception{ 
    
    private Fault faultInfo;
    
    public UnexpectedErrorEx(String errMsg)
    {
        super(errMsg);
        this.faultInfo = new Fault("FAULT");        
    }//constructor
    
    public UnexpectedErrorEx()
    {
        super("Unexpected Exception Occurred at Server side..!");
        this.faultInfo = new Fault("FAULT");        
    }//constructor

    public UnexpectedErrorEx(String errMsg, Fault fi, Throwable cause)
    {
        super(errMsg);
        this.faultInfo = fi;
    }
    
    public UnexpectedErrorEx(String errMsg, Fault fi)
    {
        super(errMsg);
        this.faultInfo = fi;
    }
    
    public Fault getFaultInfo()
    {
        return this.faultInfo;
    }//getFaultInfo
    
    public static void main(String[] args) throws UnexpectedErrorEx
    {
        //Test code
        throw new UnexpectedErrorEx("Unexpected Exception Occurred at Server side..!");
    
    }//main

}//UnexpectedErrorEx
