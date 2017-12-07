/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Ben
 */
public class RedHattedElf extends Elf {
    
    private int totalGiftsPlacedOnConveyor;
    
    public int getTotalGiftsPlacedOnConveyor() {
        return totalGiftsPlacedOnConveyor;
    }
    
    private long totalTimeWaitingAtConveyor;
    
    public long getTotalTimeWaitingAtConveyor() {
        return totalTimeWaitingAtConveyor;
    }
    
    private int totalTimesReindeerFed;
    
    public int getTotalTimesReindeerFed() {
        return totalTimesReindeerFed;
    }
    
    @Override
    public void run(){
        
    }
    
    private PresentType selectToy()
    {
        // Wait
        int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
        
        return PresentType.values()[randomNum];
   }
}
