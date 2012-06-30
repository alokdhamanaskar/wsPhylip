
package util;

import javax.xml.ws.WebFault;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> Class that represents Exceptions occurred at server-side due to unexpected inputs
 */
@WebFault(
        name = "ImproperInputsFault",
        targetNamespace = "http://wsannotations.ctegd.uga.edu/services/"
        )
public class ImproperInputEx extends java.lang.Exception { 

    private Fault faultInfo;

    
    public ImproperInputEx(String errMsg, Fault fi, Throwable cause)
    {
        super(errMsg);
        this.faultInfo = fi;
    
    }//ImproperInputEx

    public ImproperInputEx(String ErrMsg, Fault fi)
    {
        super(ErrMsg);
        this.faultInfo = fi;
    
    }//ImproperInputEx    

    public ImproperInputEx(String ErrMsg)
    {
        super(ErrMsg);
        this.faultInfo = new Fault("Fault");
    
    }//ImproperInputEx 
    

    public ImproperInputEx()
    {
        super("ImproperInputsFault");
        this.faultInfo = new Fault("Fault");
    
    }//ImproperInputEx 
        
    public Fault getFaultInfo()
    {
        return this.faultInfo;
    }//getFaultInfo
    
    public static void main(String[] args) throws ImproperInputEx
    {
        //Test code
        throw new ImproperInputEx();
  
    
    }//main

}//ImproperInputEx
