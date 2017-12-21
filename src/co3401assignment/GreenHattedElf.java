/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Ben
 */
public class GreenHattedElf extends Elf {
    private int totalSacksTaken;
    private SackCollection sacks;
    public int getTotalSacksTaken() {
        return totalSacksTaken;
    }
    
    private long totalTimeWaitingForSacksToBeFilled;
    
    public long getTotalTimeWaitingForSacksToBeFilled() {
        return totalTimeWaitingForSacksToBeFilled;
    }
    
    public GreenHattedElf(String name, SackCollection sacks) {
        super(name);
        this.sacks = sacks;
    }
    
    @Override
    public void run() {
        while(!stopped)
        {
            try {
                for(int sackIndex = 0; sackIndex < sacks.size(); sackIndex++) {
                    Sack sack = sacks.get(sackIndex);
                    
                    if (!sack.hasSpace() && sack.getLockOrReturn()) {
                        
                        // Check again in case the sack has already been emptied
                        if (!sack.hasSpace()) {
                        
                            // Need to get the turntable for which the sack is connected so that it can be replaced
                            Turntable t = sack.getAttachedTurntable();
                            //t.removeSack(sack);
                           
                            Sack newSack = new Sack(sack.getAgeLower(), sack.getAgeUpper());
                            
                            sacks.replace(sackIndex, newSack);
                            
                            newSack.setAttachedTurntable(t);
                            t.getConnectedSacks().replace(sack, newSack);
                            //t.addSack(newSack);
                            
                            log(name + " has emptied a sack with " + sack.getNumberOfPresent());
                            
                            sack.empty();
                            totalSacksTaken++;
                            
                            // Thread should spend some time emptying the sack
                            int ranSleep = ThreadLocalRandom.current().nextInt(0, 50);
                            Thread.sleep(ranSleep);

                        }
                    }
                }
            } catch (InterruptedException e) {
                log(name + "\t" + "was interrupted");
            }
        }
    }
}
