/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Ben
 */
public class RedHattedElf extends Elf {
    
    private List<ConveyorBelt> conveyors;
    private List<Sack> sacks;
    
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
    
    public RedHattedElf(String name, List<ConveyorBelt> conveyors, List<Sack> sacks) {
        super(name);
        this.conveyors = conveyors;
        this.sacks = sacks;
    }
    
    @Override
    public void run(){
        while(!stopped) {
            
            // Check if more than half the sacks are more than half full
            // Get the number of sacks more than half full
            /*int numberOfSacksMoreThanHalfFull = 0;
            for(int sackIndex = 0; sackIndex < sacks.size(); sackIndex++) {
                if (sacks.get(sackIndex).moreThanHalfFull()) {
                    numberOfSacksMoreThanHalfFull++;
                }
            }
            
            
            
            // Check against the number of sacks
            if (numberOfSacksMoreThanHalfFull > (sacks.size() / 2)) {
                // Feed the reindeer
                
            }*/
            
            try {
            placePresent(selectToy());
            } catch (InterruptedException e) {
                
            }
        }
    }
    
    public void feedReindeer() throws InterruptedException {
        // Wait some random time
        int ranSleep = ThreadLocalRandom.current().nextInt(0, 50);
        Thread.sleep(ranSleep);
        
        totalTimesReindeerFed++;
    }
    
    private void placePresent(Present newPresent) {
        ConveyorBelt conveyor = selectConveyorBelt();
        try {
            long startTime = System.currentTimeMillis();
            conveyor.enqueue(newPresent);
            long endTime = System.currentTimeMillis();
            log (name + "\t Deposited toy " + newPresent.getPresentType().toString() + " on belt " + conveyor.getId());
            totalGiftsPlacedOnConveyor++;
            
        } catch (InterruptedException e) {
            log (name + " was interrupted while placing a present");
        }
    }
    
    private ConveyorBelt selectConveyorBelt() {
        int index =  ThreadLocalRandom.current().nextInt(0, conveyors.size());
        return conveyors.get(index);
    }
    
    
    private Present selectToy() throws InterruptedException
    {
        // Wait some random time
        int ranSleep = ThreadLocalRandom.current().nextInt(0, 50);
      
        Thread.sleep(ranSleep);
        
        int randomNum = ThreadLocalRandom.current().nextInt(0, 6);
        int age = ThreadLocalRandom.current().nextInt(0, 18);
        return new Present(PresentType.values()[randomNum], age);
   }
}
