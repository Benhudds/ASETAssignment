/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class Turntable implements Runnable {
    private boolean stopped;
    private Present currentPresent;

    private Map<Sack, ConveyorBelt> outputMapping;
    private final SackCollection connectedSacks;
    
    private List<ConveyorBelt> inputs;
    private PrintStream out;
    
    public Turntable(List<ConveyorBelt> inputs, SackCollection connectedSacks, Map<Sack, ConveyorBelt> outputMapping) {
        stopped = false;
        this.inputs = inputs;
        this.connectedSacks = connectedSacks;
        this.outputMapping = outputMapping;
        
        if (this.connectedSacks != null) {
            for(int sackIndex = 0; sackIndex < this.connectedSacks.size(); sackIndex++) {
                this.connectedSacks.get(sackIndex).setAttachedTurntable(this);
            }
        }
        currentPresent = null;
        
        try {
            out = new PrintStream(new FileOutputStream("Turntable.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Elf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        while(!stopped) {
            if (currentPresent == null) {
                // Poll the inputs to accept a present
                tryInput();
            } else {
                try {
                // Poll the outputs
                tryOutput();
                } catch (InterruptedException e) {}
            }
        }
    }
    
/*    public void removeSack(Sack sack) {
        // Locked to ensure safety
        synchronized(connectedSacks) {
            connectedSacks.remove(sack);
        }
    }
    
    public void addSack(Sack sack) {
        // Locked to ensure safety
        synchronized(connectedSacks) {
            connectedSacks.add(sack);
        }
    }
  */  
    public SackCollection getConnectedSacks() {
        return this.connectedSacks;
    }
    
    private void tryInput() {
        for(ConveyorBelt conv : inputs) {
            if (!conv.empty()) {
                try {
                    currentPresent = conv.dequeue();
                    return;
                } catch (InterruptedException e) {
                    
                }
            }
        }
    }
    
    private void tryOutput() throws InterruptedException{
        // Try outputting directly to a connected sack
        // Locked around the collection so sacks can be safely removed (and added)
        if (connectedSacks != null) {
            //synchronized (connectedSacks) {
                for(int i = 0; i < connectedSacks.size(); i++) {
                    Sack sack = connectedSacks.get(i);
                    if (sack.inAgeRange(currentPresent.getAge())) {
                        if(sack.hasSpace()) {
                            if (sack.getLockOrReturn()) {
                                sack.insertPresent(currentPresent);
                                out.println("put present " + currentPresent.getPresentType() + " with age " + currentPresent.getAge() + " into sack");
                                sack.releaseLock();
                                currentPresent = null;
                                return;
                            }
                        }    
                    }
                }        
            //}
        }
        
        // This turntable may only connect directly to sacks
        if (outputMapping == null) {
            return;
        }
        
        // Try outputting to a conveyor belt
        for(Sack sack : outputMapping.keySet()) {
            if (sack.inAgeRange(currentPresent.getAge())) {
                ConveyorBelt out = outputMapping.get(sack);
                if (out.hasSpace()) {
                    out.enqueue(currentPresent);
                    currentPresent = null;
                    return;
                }
            }
        }
    }
    
    public void stop() {
        stopped = true;
    }
    
    public boolean hasPresent() {
        return currentPresent != null;
    }
}
