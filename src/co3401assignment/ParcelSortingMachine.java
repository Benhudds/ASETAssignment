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
    
    List<Elf> elves = new ArrayList<Elf>();
    
    List<Thread> threads = new ArrayList<Thread>();
    
    public ParcelSortingMachine() {
        configureMachine();
        gatherElves();
    }
    
    private void gatherElves() {
        elves.add(new RedHattedElf("nick", belts));
        elves.add(new RedHattedElf("chris", belts));
        elves.add(new RedHattedElf("li", belts));
        elves.add(new RedHattedElf("gareth", belts));
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
        for(Elf elf : elves) {
            Thread elfThread = new Thread(elf);
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
        for(Elf e : elves) {
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
        System.out.println(((RedHattedElf)elves.get(0)).getTotalGiftsPlacedOnConveyor());
        System.out.println(((RedHattedElf)elves.get(1)).getTotalGiftsPlacedOnConveyor());
        System.out.println(((RedHattedElf)elves.get(2)).getTotalGiftsPlacedOnConveyor());
        System.out.println(((RedHattedElf)elves.get(3)).getTotalGiftsPlacedOnConveyor());
        System.out.println(sacks.get(0).getNumberOfPresent());
        System.out.println(sacks.get(1).getNumberOfPresent());
    }
}
