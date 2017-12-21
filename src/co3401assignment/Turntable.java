/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.Map;

public class Turntable extends ThreadBase implements Runnable {
    
    // Current present in the turntable
    private Present currentPresent;

    // Map collection defining the assoications between sack and conveyor belt
    private final Map<Sack, ConveyorBelt> outputMapping;
    
    // Array of sacks attached to this turntable
    private final Sack[] connectedSacks;
    
    // Getter for the attached sacks
    public Sack[] getConnectedSacks() {
        return this.connectedSacks;
    }
    
    // Collection of input conveyor belts attached to this turntable
    private final ConveyorBelt[] inputs;
    
    // Constructor
    public Turntable(String filename, ConveyorBelt[] inputs, Sack[] connectedSacks, Map<Sack, ConveyorBelt> outputMapping) {
        super(filename);
        stopped = false;
        this.inputs = inputs;
        this.connectedSacks = connectedSacks;
        this.outputMapping = outputMapping;
        currentPresent = null;
        
        // Set the attach turntable for the given sacks
        if (this.connectedSacks != null) {
            for(int sackIndex = 0; sackIndex < this.connectedSacks.length; sackIndex++) {
                this.connectedSacks[sackIndex].setAttachedTurntable(this);
            }
        }
    }
    
    // Method to try to input from a conveyor belt
    private void tryInput() throws InterruptedException{
        for(ConveyorBelt conv : inputs) {
            if (!conv.empty()) {
                currentPresent = conv.dequeue();
                return;
            }
        }
    }
    
    // Method to try to output a present to a conveyor belt or sack
    private void tryOutput() throws InterruptedException{
        // Try outputting directly to a connected sack (if there are any)
        if (connectedSacks != null) {
            for(int i = 0; i < connectedSacks.length; i++) {
                // Get the sack object
                Sack sack = connectedSacks[i];
                
                // Check age range
                if (sack.inAgeRange(currentPresent.getAge())) {
                    // Check if there is space
                    if(sack.hasSpace()) {
                        // Get the lock
                        if (sack.getLockOrReturn()) {
                            // Insert the present
                            sack.insertPresent(currentPresent);
                            
                            // Logging
                            log("Put present " + currentPresent.getPresentType() + " with age " + currentPresent.getAge() + " into sack");
                            sack.releaseLock();
                            currentPresent = null;
                            return;
                        }
                    }    
                }
            }
        }
        
        // Return if this turntable only connectes to sacks
        if (outputMapping == null) {
            return;
        }
        
        // Try outputting to a conveyor belt via the output mapping
        for(Sack sack : outputMapping.keySet()) {
            if (sack.inAgeRange(currentPresent.getAge())) {
                ConveyorBelt out = outputMapping.get(sack);
                if (out.hasSpace()) {
                    // Enqueue the present
                    out.enqueue(currentPresent);
                    
                    // Logging
                    log("Put present " + currentPresent.getPresentType() + " with age " + currentPresent.getAge() + " onto conveyor belt " + out.getId());
                    currentPresent = null;
                    return;
                }
            }
        }
    }    
    
    // Run method
    @Override
    public void run() {
        while(!stopped) {
            try {
                if (currentPresent == null) {
                    // Poll the inputs to accept a present
                    tryInput();
                } else {
                    // Poll the outputs to take a present
                    tryOutput();
                }
            } catch (InterruptedException e) {
                
            }
        }
    }
    
    // Method to stop the turntable
    public void stop() {
        stopped = true;
    }
    
    // Method to indicate if there is a present in the turntable
    public boolean hasPresent() {
        return currentPresent != null;
    }
}
