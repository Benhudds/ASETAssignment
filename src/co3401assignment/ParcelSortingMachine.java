/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Ben
 */
public class ParcelSortingMachine implements Runnable {
    
    List<ConveyorBelt> belts = new ArrayList<ConveyorBelt>();
    Set<Turntable> turntables = new HashSet<Turntable>();
    List<Sack> sacks = new ArrayList<Sack>();
    
    List<Elf> greenHattedElves = new ArrayList<Elf>();
    List<Elf> redHattedElves = new ArrayList<Elf>();
    
    List<Thread> threads = new ArrayList<Thread>();
    
    public ParcelSortingMachine() {
        configureMachine();
        gatherElves();
    }
    
    private void gatherElves() {
        redHattedElves.add(new RedHattedElf("nick", belts, sacks));
        redHattedElves.add(new RedHattedElf("chris", belts, sacks));
        redHattedElves.add(new RedHattedElf("li", belts, sacks));
        redHattedElves.add(new RedHattedElf("gareth", belts, sacks));
        
        greenHattedElves.add(new GreenHattedElf("doug", sacks));
    }
    
    private void configureMachine() {
        // C - T - S
        sacks.add(new Sack(0, 18));
        sacks.add(new Sack(0, 18));
        belts.add(new ConveyorBelt(1, 1));
        belts.add(new ConveyorBelt(2, 1));
       
        turntables.add(new Turntable(belts, sacks));
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
        for(Elf e : redHattedElves) {
            totalPresentsMade += ((RedHattedElf)e).getTotalGiftsPlacedOnConveyor();
        }
        
        int totalSacksTaken = 0;
        for(Elf e : greenHattedElves) {
            totalSacksTaken += ((GreenHattedElf)e).getTotalSacksTaken();
        }
        
        int presentsInConveyors = 0;
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
        for(Sack s : sacks) {
            presentsInSacks += s.getNumberOfPresent();
        }
        
        
        if (totalPresentsMade - totalSacksTaken * 20 - presentsInConveyors - presentsInTurntables - presentsInSacks == 0)
        {
            System.out.println("Good run");
            return;
        }
        System.out.println("Total presents made = " + totalPresentsMade);
        System.out.println("Total sacks taken = " + totalSacksTaken);
        System.out.println("Presents in conveyors = " + presentsInConveyors);
        System.out.println("Presents in turntables = " + presentsInTurntables);
        System.out.println("Defecit = " + (totalPresentsMade - totalSacksTaken * 20 - presentsInConveyors - presentsInTurntables - presentsInSacks));
        System.out.println("Presents in sack 1 = " + sacks.get(0).getNumberOfPresent());
        System.out.println("Presents in sack 2 = " + sacks.get(1).getNumberOfPresent());
        System.out.println("\n");
    }
}
