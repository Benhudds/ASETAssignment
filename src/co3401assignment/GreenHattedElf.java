/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.ThreadLocalRandom;

public class GreenHattedElf extends Elf {
    // Array of all sacks
    private final Sack[] sacks;
    
    // Spare sack to replace a full one with
    private Sack spare;
    
    // Total number of sacks taken
    private int totalSacksTaken;
    
    // Getter for total number of sacks taken
    public int getTotalSacksTaken() {
        return totalSacksTaken;
    }
    
    // Total amount of time waiting for sacks to be filled
    private long totalTimeWaitingForSacksToBeFilled;
    
    // Getter for total amount of time waiting for sacks to be filled
    public long getTotalTimeWaitingForSacksToBeFilled() {
        return totalTimeWaitingForSacksToBeFilled;
    }
    
    // Constructor
    public GreenHattedElf(String name, Sack[] sacks) {
        super(name);
        this.sacks = sacks;
    }
    
    // Run method
    @Override
    public void run() {
        long endTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        
        while(!stopped)
        {
            try {
                for(int sackIndex = 0; sackIndex < sacks.length; sackIndex++) {
                    // Get the current sack
                    Sack sack = sacks[sackIndex];
                    
                    // If the sack is full, get the lock
                    if (!sack.hasSpace() && sack.getLockOrReturn()) {
                        endTime = System.currentTimeMillis();
                        totalTimeWaitingForSacksToBeFilled += endTime - startTime;
                        
                        // Need to get the turntable for which the sack is connected so that it can be replaced
                        Turntable t = sack.getAttachedTurntable();
                        
                        // Set spare sack age ranges
                        spare = new Sack(sack.getAgeLower(), sack.getAgeUpper());

                        Sack[] tSacks = t.getConnectedSacks();
                        for(int i = 0; i < tSacks.length; i++) {
                            if (tSacks[i] == sack) {
                                tSacks[i] = spare;
                                break;
                            }
                        }
                        
                        //Set main sack array reference to the new sack (spare)
                        sacks[sackIndex] = spare;

                        // Set the attached turntable of the new sack
                        spare.setAttachedTurntable(t);
                        
                        // Set the spare to the full sack then empty it
                        spare = sack;
                        sack.empty();
                        totalSacksTaken++;
                        log(name + " has emptied a sack with " + sack.getNumberOfPresent());

                        // Spend some time "emptying" the sack
                        int ranSleep = ThreadLocalRandom.current().nextInt(0, 50);
                        Thread.sleep(ranSleep);
                        
                        sack.releaseLock();
                        
                        startTime = System.currentTimeMillis();
                    }
                }
            } catch (InterruptedException e) {
                log("Was interrupted");
            }
        }
        if (startTime > endTime) {
            endTime = System.currentTimeMillis();
            totalTimeWaitingForSacksToBeFilled += endTime - startTime;
        }
    }
}
