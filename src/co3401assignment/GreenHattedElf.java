/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

/**
 *
 * @author Ben
 */
public class GreenHattedElf extends Elf {
    private int totalSacksTaken;
    
    public int getTotalSacksTaken() {
        return totalSacksTaken;
    }
    
    private long totalTimeWaitingForSacksToBeFilled;
    
    public long getTotalTimeWaitingForSacksToBeFilled() {
        return totalTimeWaitingForSacksToBeFilled;
    }
    
    public GreenHattedElf(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        
    }
}
