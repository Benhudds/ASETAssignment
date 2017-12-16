/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

/**
 *
 * @author Ben
 */
public class Clock implements Runnable {
    
    private int tickCount;
    
    public int getTime() {
        return tickCount;
    }
        
    public Clock() {
        tickCount = 0;
    }
    
    public void run() {
        // Run until tick count is 600 (5 hours with 2 minute ticks)
        while(tickCount != 600) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                
            }
            
            tickCount++;
        }
    }
}
