/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

public class Clock implements Runnable {
    // Tick counter
    private static int tickCount;
    
    // Remainder for modulus operation to print out hour
    private int remainder;
    
    // Getter for the tick counter 
    public static int getTime() {
        return tickCount;
    }
        
    // Constructor
    public Clock() {
        tickCount = 0;
        remainder = 0;
    }
    
    // Run method
    @Override
    public void run() {
        try {
            while(true) {
                int temp = (tickCount + 1)%30;
                if (temp < remainder) {
                    System.out.println("One hour passed");
                }
                
                remainder = temp;
                
                Thread.sleep(1000);
                tickCount++;
            }
        } catch (InterruptedException e) {

        }
    }
}
