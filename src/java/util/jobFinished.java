/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;

/**
 *
 * @author Alok Dhamanaskar
 */
public class jobFinished
{
    
    public static boolean CheckStatus(String dirName) throws InterruptedException
    {
            File file1 = new File(dirName + "/outfile");
            File file2 = new File(dirName + "/outtree");
            boolean exists1= file1.exists();
            boolean exists2 = file2.exists();
            int sleepTime = 50, tryCount = 0;
            
            while (exists1 == false || exists2 == false)
            {
                Thread.sleep(sleepTime);
                sleepTime = sleepTime * 2;
                exists1 = file1.exists();
                exists2 = file2.exists();
                if (++tryCount > 10)
                    return false;
            }
        return true;
    }
    
        public static boolean CheckStatusOutfile(String dirName) throws InterruptedException
    {
            File file1 = new File(dirName + "/outfile");
            boolean exists1= file1.exists();
            int sleepTime = 50, tryCount = 0;
            
            while (exists1 == false)
            {
                Thread.sleep(sleepTime);
                sleepTime = sleepTime * 2;
                exists1 = file1.exists();
                if (++tryCount > 10)
                    return false;
            }
        return true;
    }
    
}
