/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.ThreadLocalRandom;

public class RedHattedElf extends Elf {
    // Array of conveyor belts presents can be placed on
    private ConveyorBelt[] conveyors;
    
    // Array of sacks presents will be placed in
    private Sack[] sacks;
    
    // Check if the reindeer have been fed(since the last time more than half the sacks were more than half full)
    private boolean reindeerFed = false;
    
    // Total presents placed on a conveyor belt
    private int totalGiftsPlacedOnConveyor;
    
    // Getter for total presents placed on conveyor belt
    public int getTotalGiftsPlacedOnConveyor() {
        return totalGiftsPlacedOnConveyor;
    }
    
    // Total time spent waiting at a conveyor belt
    private long totalTimeWaitingAtConveyor;
    
    // Getter for total time spent waiting at a conveyor belt
    public long getTotalTimeWaitingAtConveyor() {
        return totalTimeWaitingAtConveyor;
    }
    
    //Total times the reindeer were fed
    private int totalTimesReindeerFed;
    
    // Getter for total times the reindeer were fed
    public int getTotalTimesReindeerFed() {
        return totalTimesReindeerFed;
    }
    
    // Constructor
    public RedHattedElf(String name, ConveyorBelt[] conveyors, Sack[] sacks) {
        super(name);
        this.conveyors = conveyors;
        this.sacks = sacks;
    }
    
    // Method to feed the reindeer
    private void feedReindeer() throws InterruptedException {
        // Wait some random time
        int ranSleep = ThreadLocalRandom.current().nextInt(1000, 5000);
        Thread.sleep(ranSleep);
        
        totalTimesReindeerFed++;
    }
    
    // Method to place a given present on a conveyor belt
    private void placePresent(Present newPresent) {
        // Get a conveyor belt
        ConveyorBelt conveyor = selectConveyorBelt();
        try {
            // Enqueue present
            long startTime = System.currentTimeMillis();
            conveyor.enqueue(newPresent);
            long endTime = System.currentTimeMillis();
            
            // Increment counters
            totalGiftsPlacedOnConveyor++;
            totalTimeWaitingAtConveyor += endTime - startTime;
            
            // Logging
            log (name + "\t Deposited toy " + newPresent.getPresentType().toString() + " with age " + newPresent.getAge() + " on belt " + conveyor.getId());
            
        } catch (InterruptedException e) {
            log (name + " was interrupted while placing a present");
        }
    }
    
    // Method to select a random conveyor belt
    private ConveyorBelt selectConveyorBelt() {
        int index =  ThreadLocalRandom.current().nextInt(0, conveyors.length);
        return conveyors[index];
    }
    
    // Method to spend some time creating a random present
    private Present selectToy() throws InterruptedException
    {
        // Wait some random time
        int ranSleep = ThreadLocalRandom.current().nextInt(1000, 2500);
        Thread.sleep(ranSleep);
        
        // Create and return a present with random type and age
        int randomNum = ThreadLocalRandom.current().nextInt(0, 6);
        int age = ThreadLocalRandom.current().nextInt(0, 18);
        return new Present(PresentType.values()[randomNum], age);
   }
    
    // Run method
    @Override
    public void run(){
        while(!stopped) {
            try {
            
            // Check if more than half the sacks are more than half full
            // Get the number of sacks more than half full
            int numberOfSacksMoreThanHalfFull = 0;

            for(int sackIndex = 0; sackIndex < sacks.length; sackIndex++) {
                if (sacks[sackIndex].moreThanHalfFull()) {
                    numberOfSacksMoreThanHalfFull++;
                }
            }
            
            // Check against the number of sacks
            if (numberOfSacksMoreThanHalfFull > (sacks.length / 2)) {
                // Feed the reindeer
                if (!reindeerFed) {
                    feedReindeer();
                    reindeerFed = true;
                }
            } else {
                reindeerFed = false;
            }
                
            // Create and place a present on a conveyor
            placePresent(selectToy());
            } catch (InterruptedException e) {
                log ("Was interrupted");
            }
        }
    }
}
