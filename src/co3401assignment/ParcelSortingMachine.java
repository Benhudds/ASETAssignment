/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ben
 */
public class ParcelSortingMachine implements Runnable {
    
    List<ConveyorBelt> entry = new ArrayList<ConveyorBelt>();
    List<ConveyorBelt> belts = new ArrayList<ConveyorBelt>();
    Set<Turntable> turntables = new HashSet<Turntable>();
    SackCollection sacks = new SackCollection(6);
    
    List<Elf> greenHattedElves = new ArrayList<Elf>();
    List<Elf> redHattedElves = new ArrayList<Elf>();
    
    List<Thread> threads = new ArrayList<Thread>();
    
    public ParcelSortingMachine() {
        configureMachine();
        gatherElves();
    }
    
    private void gatherElves() {
        redHattedElves.add(new RedHattedElf("nick", entry, sacks));
        redHattedElves.add(new RedHattedElf("chris", entry, sacks));
        redHattedElves.add(new RedHattedElf("li", entry, sacks));
        redHattedElves.add(new RedHattedElf("gareth", entry, sacks));
        redHattedElves.add(new RedHattedElf("jonathan", entry, sacks));
        
        
        greenHattedElves.add(new GreenHattedElf("doug", sacks));
        greenHattedElves.add(new GreenHattedElf("josh", sacks));
    }
    
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
        try {
        Sack sack1 = new Sack(0, 3);
        Sack sack2 = new Sack(3, 6);
        Sack sack3 = new Sack(6, 9);
        Sack sack4 = new Sack(9, 12);
        Sack sack5 = new Sack(12, 15);
        Sack sack6 = new Sack(15, 18);
        
        sacks.add(sack1);
        sacks.add(sack2);
        sacks.add(sack3);
        sacks.add(sack4);
        sacks.add(sack5);
        sacks.add(sack6);
        
        ConveyorBelt entry1 = new ConveyorBelt(1, 2);
        ConveyorBelt entry2 = new ConveyorBelt(2, 2);
        ConveyorBelt entry3 = new ConveyorBelt(3, 2);
        
        entry.add(entry1);
        entry.add(entry2);
        entry.add(entry3);
        
        ConveyorBelt conv1 = new ConveyorBelt(4, 4);
        ConveyorBelt conv2 = new ConveyorBelt(5, 1);
        ConveyorBelt conv3 = new ConveyorBelt(6, 1);
        
        belts.add(conv1);
        belts.add(conv2);
        belts.add(conv3);
        
        ArrayList<ConveyorBelt> inputs1 = new ArrayList<ConveyorBelt>();
        inputs1.add(entry1);
        inputs1.add(entry2);
        
        Map<Sack, ConveyorBelt> outputMapping1 = new HashMap<Sack, ConveyorBelt>();
        outputMapping1.put(sack1, conv1);
        outputMapping1.put(sack2, conv1);
        outputMapping1.put(sack3, conv1);
        outputMapping1.put(sack4, conv1);
        outputMapping1.put(sack5, conv1);
        outputMapping1.put(sack6, conv1);
        
        turntables.add(new Turntable(inputs1, null, outputMapping1));
        
        ArrayList<ConveyorBelt> inputs2 = new ArrayList<ConveyorBelt>();
        inputs2.add(entry3);
        inputs2.add(conv1);
        
        SackCollection sacks2 = new SackCollection(1);
        sacks2.add(sack1);
        
        Map<Sack, ConveyorBelt> outputMapping2 = new HashMap<Sack, ConveyorBelt>();
        outputMapping2.put(sack2, conv2);
        outputMapping2.put(sack3, conv2);
        outputMapping2.put(sack4, conv2);
        outputMapping2.put(sack5, conv2);
        outputMapping2.put(sack6, conv2);
        
        turntables.add(new Turntable(inputs2, sacks2, outputMapping2));
        
        ArrayList<ConveyorBelt> inputs3 = new ArrayList<ConveyorBelt>();
        inputs3.add(conv2);
        
        SackCollection sacks3 = new SackCollection(2);
        sacks3.add(sack2);
        sacks3.add(sack3);
        
        Map<Sack, ConveyorBelt> outputMapping3 = new HashMap<Sack, ConveyorBelt>();
        outputMapping3.put(sack4, conv3);
        outputMapping3.put(sack5, conv3);
        outputMapping3.put(sack6, conv3);
        
        turntables.add(new Turntable(inputs3, sacks3, outputMapping3));
        
        ArrayList<ConveyorBelt> inputs4 = new ArrayList<ConveyorBelt>();
        inputs4.add(conv3);
        
        SackCollection sacks4 = new SackCollection(3);
        sacks4.add(sack4);
        sacks4.add(sack5);
        sacks4.add(sack6);
        
        turntables.add(new Turntable(inputs4, sacks4, null));
        } catch (InterruptedException e) {
            
        }
    }
    public void run() {
        for(Elf elf : redHattedElves) {
            Thread elfThread = new Thread(elf);
            elfThread.start();
            threads.add(elfThread);
        }
        
        for(Elf elf : greenHattedElves) {
            Thread elfThread = new Thread (elf);
            elfThread.start();
            threads.add(elfThread);
        }
        
        for(Turntable turntable : turntables) {
            Thread turntableThread = new Thread(turntable);
            turntableThread.start();
            threads.add(turntableThread);
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
        for(Elf e : redHattedElves) {
            totalPresentsMade += ((RedHattedElf)e).getTotalGiftsPlacedOnConveyor();
            timesReindeerFed += ((RedHattedElf)e).getTotalTimesReindeerFed();
        }
        
        int totalSacksTaken = 0;
        for(Elf e : greenHattedElves) {
            totalSacksTaken += ((GreenHattedElf)e).getTotalSacksTaken();
        }
        
        int presentsInConveyors = 0;
        for(ConveyorBelt belt : entry) {
            presentsInConveyors += belt.getNumberOfPresents();
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
        for(int sackIndex = 0; sackIndex < sacks.size(); sackIndex++) {
            presentsInSacks += sacks.get(sackIndex).getNumberOfPresent();
        }
        
        
        if (totalPresentsMade - totalSacksTaken * 20 - presentsInConveyors - presentsInTurntables - presentsInSacks == 0)
        {
            System.out.println("Good run");
            
        }
        else
        {
            System.out.println("/n Bad run /n");
        }
        System.out.println("Total presents made = " + totalPresentsMade);
        System.out.println("Total sacks taken = " + totalSacksTaken);
        System.out.println("Presents in conveyors = " + presentsInConveyors);
        System.out.println("Presents in turntables = " + presentsInTurntables);
        System.out.println("Defecit = " + (totalPresentsMade - totalSacksTaken * 20 - presentsInConveyors - presentsInTurntables - presentsInSacks));
        
        System.out.println("Times reindeer fed = " + timesReindeerFed);
        
        int tindex = 0;
        for(Turntable t : turntables) {
            tindex++;
            System.out.println("Present in turntable " + tindex + " = " + t.hasPresent());
        }
        
        for(int index = 0; index < entry.size(); index++) {
            System.out.println("Presents in entry " + (index + 1) + " = " + entry.get(index).getNumberOfPresents());
        }
        
        for(int index = 0; index < belts.size(); index++) {
            System.out.println("Presents in belt " + (index + 1) + " = " + belts.get(index).getNumberOfPresents());
        }
        
        for(int sackIndex = 0; sackIndex < sacks.size(); sackIndex++) {
            System.out.println("Presents in sack " + (sackIndex + 1) + " = " + sacks.get(sackIndex).getNumberOfPresent());
        }
        System.out.println("\n");
    }
}
