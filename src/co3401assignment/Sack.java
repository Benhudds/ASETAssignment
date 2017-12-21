/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Ben
 */
public class Sack {
    private Present[] sack;
    private int currentIndex;
    
    private final int sackCapacity = 20;
    private Semaphore mutex;
    
    public void getLock() throws InterruptedException {
        mutex.acquire();
    }
    
    public boolean getLockOrReturn() throws InterruptedException {
        if (mutex.tryAcquire()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void releaseLock() {
        mutex.release();
    }
    
    private int ageLower;
    
    public int getAgeLower() {
        return ageLower;
    }
    
    private int ageUpper;
    
    public int getAgeUpper() {
        return ageUpper;
    }
    
    private Turntable attachedToTurntable;
    
    public Turntable getAttachedTurntable() {
        return attachedToTurntable;
    }
    
    public void setAttachedTurntable(Turntable newTurntable) {
        attachedToTurntable = newTurntable;
    }
    
    public Sack(int ageLower, int ageUpper) {
        sack = new Present[20];
        currentIndex = 0;
        mutex = new Semaphore(1);
        
        this.ageLower = ageLower;
        this.ageUpper = ageUpper;
    }
    
    public int getNumberOfPresent() {
        return currentIndex;
    }

    public boolean insertPresent(Present p) {
        if (currentIndex != sackCapacity) {
            sack[currentIndex] = p;
            currentIndex++;
            return true;
        }
        
        return false;
    }
    
    public boolean inAgeRange(int age) {
        if (ageLower <= age && ageUpper >= age) {
            return true;
        }
        
        return false;
    }
    
    public boolean hasSpace() {
        return currentIndex != sackCapacity;
    }
    
    public void empty() {
        // GC will pick up any unreferenceables
        // when we overwrite each index
        currentIndex = 0;
    }
    
    public boolean moreThanHalfFull() {
        return currentIndex > (sackCapacity / 2);
    }
}
