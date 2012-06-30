
package util;

/**
 * @author Alok Dhamanaskar (alokd@uga.edu)
 * @see LICENSE (MIT style license file). 
 *
 * <br/><br/> Class that represents Exceptions occurred at server-side due to unexpected inputs
 */
public class ImproperInputEx extends java.lang.Exception { 
    
    public ImproperInputEx()
    {
        super("Unexpected inputs..!!");
    
    }//ImproperInputEx

    public ImproperInputEx(String ErrMsg)
    {
        super(ErrMsg);
    
    }//ImproperInputEx    
    
    public static void main(String[] args) throws ImproperInputEx
    {
        //Test code
        throw new ImproperInputEx();
  
    
    }//main

}//ImproperInputEx
