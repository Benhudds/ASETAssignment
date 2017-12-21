/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.Semaphore;

public class Sack {
    // Array of Presents
    private final Present[] sack;
    
    // Current write position of array
    private int currentIndex;
    
    // Getter for the number of presents in this sack
    public int getNumberOfPresent() {
        return currentIndex;
    }
    
    // Total capacity
    private final int sackCapacity = 20;
    
    // Mutex
    private final Semaphore mutex;
    
    // Lower age limit of this sack
    private int ageLower;
    
    // Getter for the lower age limit
    public int getAgeLower() {
        return ageLower;
    }
    
    // Upper age limit of this sack
    private int ageUpper;
    
    // Getter for the upper age limit
    public int getAgeUpper() {
        return ageUpper;
    }
    
    // Reference to the turntable this sack is attached to
    private Turntable attachedToTurntable;
    
    // Getter for the turntable this sack is attached to
    public Turntable getAttachedTurntable() {
        return attachedToTurntable;
    }
    
    // Setter for the turntable this sack is attached to
    public void setAttachedTurntable(Turntable newTurntable) {
        attachedToTurntable = newTurntable;
    }
    
    //Constructor
    public Sack(int ageLower, int ageUpper) {
        sack = new Present[20];
        currentIndex = 0;
        mutex = new Semaphore(1);
        
        this.ageLower = ageLower;
        this.ageUpper = ageUpper;
    }
    
    // Method to insert a present at the current index
    public boolean insertPresent(Present p) {
        sack[currentIndex] = p;
        currentIndex++;
        return true;
    }
    
    // Method to check if a given age is within the age range for this sack
    public boolean inAgeRange(int age) {
        return ageLower <= age && ageUpper >= age;
    }
    
    // Method to check if there is space in the sasck
    public boolean hasSpace() {
        return currentIndex != sackCapacity;
    }
    
    // Method to empty the sack
    public void empty() {
        for(Present p : sack) {
            p = null;
        }
        
        // Set the current index to 0
        currentIndex = 0;
    }
    
    // Method to check if sack is more than half full
    public boolean moreThanHalfFull() {
        return currentIndex > (sackCapacity / 2);
    }
    
    // Getter for the mutex lock (acquire)
    public void getLock() throws InterruptedException {
        mutex.acquire();
    }
    
    // Getter for the mutex lock (tryAcquire) that returns true if locked and false if not
    public boolean getLockOrReturn() {
        return mutex.tryAcquire();
    }
    
    // Releases the lock
    public void releaseLock() {
        mutex.release();
    }
}
