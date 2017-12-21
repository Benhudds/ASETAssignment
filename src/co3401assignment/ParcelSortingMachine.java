/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.HashMap;
import java.util.Map;

public class ParcelSortingMachine implements Runnable {
    
    // Clock
    private Clock clock;
    
    // Array of entry point conveyor belts
    private ConveyorBelt[] entry;
    
    // Array of normal conveyor belts
    private ConveyorBelt[] belts;
    
    // Array of turntables
    private Turntable[] turntables;
    
    // Array of all the sacks in the machine
    private Sack[] sacks;
    
    // Array of green elves
    private Elf[] greenHattedElves;
    
    // Array of red elves
    private Elf[] redHattedElves;
    
    // Array of all the threads created
    private Thread[] threads;
    
    // Constructor
    public ParcelSortingMachine() {
        clock = new Clock();
        
        // Create the conveyor belts, turntables, and sacks
        configureMachine();
        
        // Create the elves
        gatherElves();
    }
    
    // Method to create the elves
    private void gatherElves() {
        redHattedElves = new Elf[5];
        redHattedElves[0] = new RedHattedElf("nick", entry, sacks);
        redHattedElves[1] = new RedHattedElf("chris", entry, sacks);
        redHattedElves[2] = new RedHattedElf("li", entry, sacks);
        redHattedElves[3] = new RedHattedElf("gareth", entry, sacks);
        redHattedElves[4] = new RedHattedElf("jonathan", entry, sacks);
        
        greenHattedElves = new Elf[2];
        greenHattedElves[0] = new GreenHattedElf("doug", sacks);
        greenHattedElves[1] = new GreenHattedElf("josh", sacks);
    }
    
    // Method to create the machine
    private void configureMachine() {
        // - - - - - - - - - - - 
        // - - - E - - - - E - - 
        // - - - E - - - - E - -
        // - E E T C C C C T S -
        // - - - - - - - - C - -
        // - - - - - - - S T S - 
        // - - - - - - - - C - -
        // - - - - - - - S T S - 
        // - - - - - - - - S - -
        // - - - - - - - - - - -
        entry = new ConveyorBelt[3];
        belts = new ConveyorBelt[3];
        sacks = new Sack[6];
        turntables = new Turntable[4];
        
        Sack sack1 = new Sack(0, 3);
        Sack sack2 = new Sack(3, 6);
        Sack sack3 = new Sack(6, 9);
        Sack sack4 = new Sack(9, 12);
        Sack sack5 = new Sack(12, 15);
        Sack sack6 = new Sack(15, 18);
        
        sacks[0] = sack1;
        sacks[1] = sack2;
        sacks[2] = sack3;
        sacks[3] = sack4;
        sacks[4] = sack5;
        sacks[5] = sack6;
        
        ConveyorBelt entry1 = new ConveyorBelt(1, 2);
        ConveyorBelt entry2 = new ConveyorBelt(2, 2);
        ConveyorBelt entry3 = new ConveyorBelt(3, 2);
        
        entry[0] = entry1;
        entry[1] = entry2;
        entry[2] = entry3;
        
        ConveyorBelt conv1 = new ConveyorBelt(4, 4);
        ConveyorBelt conv2 = new ConveyorBelt(5, 1);
        ConveyorBelt conv3 = new ConveyorBelt(6, 1);
        
        belts[0] = conv1;
        belts[1] = conv2;
        belts[2] = conv3;
        
        ConveyorBelt[] inputs1 = new ConveyorBelt[2];
        inputs1[0] = entry1;
        inputs1[1] = entry2;
        
        Map<Sack, ConveyorBelt> outputMapping1 = new HashMap<Sack, ConveyorBelt>();
        outputMapping1.put(sack1, conv1);
        outputMapping1.put(sack2, conv1);
        outputMapping1.put(sack3, conv1);
        outputMapping1.put(sack4, conv1);
        outputMapping1.put(sack5, conv1);
        outputMapping1.put(sack6, conv1);
        
        turntables[0] = new Turntable("Turntable 1", inputs1, null, outputMapping1);
        
        ConveyorBelt[] inputs2 = new ConveyorBelt[2];
        inputs2[0] = entry3;
        inputs2[1] = conv1;
        
        Sack[] sacks2 = new Sack[1];
        sacks2[0] = sack1;
        
        Map<Sack, ConveyorBelt> outputMapping2 = new HashMap<Sack, ConveyorBelt>();
        outputMapping2.put(sack2, conv2);
        outputMapping2.put(sack3, conv2);
        outputMapping2.put(sack4, conv2);
        outputMapping2.put(sack5, conv2);
        outputMapping2.put(sack6, conv2);
        
        turntables[1] = new Turntable("Turntable 2", inputs2, sacks2, outputMapping2);
        
        ConveyorBelt[] inputs3 = new ConveyorBelt[1];
        inputs3[0] = conv2;
        
        Sack[] sacks3 = new Sack[2];
        sacks3[0] = sack2;
        sacks3[1] = sack3;
        
        Map<Sack, ConveyorBelt> outputMapping3 = new HashMap<Sack, ConveyorBelt>();
        outputMapping3.put(sack4, conv3);
        outputMapping3.put(sack5, conv3);
        outputMapping3.put(sack6, conv3);
        
        turntables[2] = new Turntable("Turntable 3", inputs3, sacks3, outputMapping3);
        
        ConveyorBelt[]inputs4 = new ConveyorBelt[1];
        inputs4[0] = conv3;
        
        Sack[] sacks4 = new Sack[3];
        sacks4[0] = sack4;
        sacks4[1] = sack5;
        sacks4[2] = sack6;
        
        turntables[3] = new Turntable("Turntable 4", inputs4, sacks4, null);
    }
    public void run() {
        int threadIndex = redHattedElves.length + greenHattedElves.length + turntables.length;
        threads = new Thread[threadIndex];
        threadIndex = 0;
        
        Thread t = new Thread(clock);
        t.start();
        
        for(Elf elf : redHattedElves) {
            Thread elfThread = new Thread(elf);
            elfThread.start();
            threads[threadIndex] = elfThread;
            threadIndex++;
        }
        
        for(Elf elf : greenHattedElves) {
            Thread elfThread = new Thread (elf);
            elfThread.start();
            threads[threadIndex] = elfThread;
            threadIndex++;
        }
        
        for(Turntable turntable : turntables) {
            Thread turntableThread = new Thread(turntable);
            turntableThread.start();
            threads[threadIndex] = turntableThread;
            threadIndex++;
        }
        
        // Run for 5 ticks (10 mins)
        while(clock.getTime() < 5) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                
            }
        }
        
        try {
            t.interrupt();
            stop();
        } catch (InterruptedException e) {
            
        }
    }
    
    public void stop() throws InterruptedException {    
        for(Elf e : redHattedElves) {
            e.stop();
        }
        
        for(Elf e : greenHattedElves) {
            e.stop();
        }
        
        for(Turntable t : turntables) {
            t.stop();
        }
        
        for(Thread t : threads) {
            t.interrupt();
            t.join();
        }
    }
    
    public void report() {
        int totalPresentsMade = 0;
        int timesReindeerFed = 0;
        long timeSpentWaitingAtConveyor = 0;
        for(Elf e : redHattedElves) {
            totalPresentsMade += ((RedHattedElf)e).getTotalGiftsPlacedOnConveyor();
            timesReindeerFed += ((RedHattedElf)e).getTotalTimesReindeerFed();
            timeSpentWaitingAtConveyor += ((RedHattedElf)e).getTotalTimeWaitingAtConveyor();
        }
        
        int totalSacksTaken = 0;
        long timeSpentWaitingForSacks = 0;
        for(Elf e : greenHattedElves) {
            totalSacksTaken += ((GreenHattedElf)e).getTotalSacksTaken();
            timeSpentWaitingForSacks += ((GreenHattedElf)e).getTotalTimeWaitingForSacksToBeFilled();
        }
        
        int presentsInConveyors = 0;
        for(int index = 0; index < entry.length; index++) {
            
            presentsInConveyors += entry[index].getNumberOfPresents();
        }
        for(ConveyorBelt belt : belts) {
            presentsInConveyors += belt.getNumberOfPresents();
        }
        
        int presentsInTurntables = 0;
        for(Turntable t : turntables) {
            if (t.hasPresent()) {
                presentsInTurntables++;
            }
        }
        
        int presentsInSacks = 0;
        for(int sackIndex = 0; sackIndex < sacks.length; sackIndex++) {
            presentsInSacks += sacks[sackIndex].getNumberOfPresent();
        }
        
        System.out.println("Total presents made = " + totalPresentsMade);
        System.out.println("Total sacks taken = " + totalSacksTaken);
        System.out.println("Times reindeer fed = " + timesReindeerFed);
        System.out.println("Time spent waiting by red hatted elves = " + timeSpentWaitingAtConveyor);
        System.out.println("Time spent waiting by green hatted elves = " + timeSpentWaitingForSacks);
        
        if (totalPresentsMade - totalSacksTaken * 20 - presentsInConveyors - presentsInTurntables - presentsInSacks == 0)
        {
            System.out.println("Good run");
        }
        else
        {
            // Debug logging
            System.out.println("Bad run");

            System.out.println("Defecit = " + (totalPresentsMade - totalSacksTaken * 20 - presentsInConveyors - presentsInTurntables - presentsInSacks));
            int tindex = 0;
            for(Turntable t : turntables) {
                tindex++;
                System.out.println("Present in turntable " + tindex + " = " + t.hasPresent());
            }

            for(int index = 0; index < entry.length; index++) {
                System.out.println("Presents in entry " + (index + 1) + " = " + entry[index].getNumberOfPresents());
            }

            for(int index = 0; index < belts.length; index++) {
                System.out.println("Presents in belt " + (index + 1) + " = " + belts[index].getNumberOfPresents());
            }

            for(int sackIndex = 0; sackIndex < sacks.length; sackIndex++) {
                System.out.println("Presents in sack " + (sackIndex + 1) + " = " + sacks[sackIndex].getNumberOfPresent());
            }
        }
        System.out.println("\n");
    }
}
