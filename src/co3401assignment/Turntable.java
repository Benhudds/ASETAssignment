/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ben
 */
public class Turntable implements Runnable {
    private boolean stopped;
    private Present currentPresent;

    private Map<Sack, ConveyorBelt> outputMapping;
    private List<Sack> connectedSacks;
    private List<ConveyorBelt> inputs;
    
    public Turntable(List<ConveyorBelt> inputs, List<Sack> connectedSacks) {
        stopped = false;
        this.inputs = inputs;
        this.connectedSacks = connectedSacks;
        currentPresent = null;
    }
    
    public void run() {
        while(!stopped) {
            if (currentPresent == null) {
                // Poll the inputs to accept a present
                tryInput();
            } else {
                // Poll the outputs
                tryOutput();
            }
        }
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
    
    private void tryOutput() {
        // Try outputting directly to a connected sack
        for(Sack sack : connectedSacks) {
            if (sack.inAgeRange(currentPresent.getAge())) {
                if(sack.hasSpace()) {
                    sack.insertPresent(currentPresent);
                    currentPresent = null;
                    return;
                }    
            }
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
                    try {
                        out.enqueue(currentPresent);
                    } catch (InterruptedException e) {
                    }
                    currentPresent = null;
                    return;
                }
            }
        }
    }
    
    public void stop() {
        stopped = true;
    }
}
