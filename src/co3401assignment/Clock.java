/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

public class Clock implements Runnable {
    // Tick counter
    private static int tickCount;
    
    // Getter for the tick counter 
    public static int getTime() {
        return tickCount;
    }
        
    // Constructor
    public Clock() {
        tickCount = 0;
    }
    
    // Run method
    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(1000);
                tickCount++;
            }
        } catch (InterruptedException e) {

        }
    }
}
