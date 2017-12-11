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
public class Sack {
    private Present[] sack;
    private int currentIndex;
    private final int sackCapacity = 20;
    
    private int ageLower;
    private int ageUpper;
    
    public Sack(int ageLower, int ageUpper) {
        sack = new Present[20];
        currentIndex = 0;
        
        this.ageLower = ageLower;
        this.ageUpper = ageUpper;
    }
    
    public int getNumberOfPresent() {
        return currentIndex;
    }
    
    public synchronized void insertPresent(Present p) {
        if (currentIndex != sackCapacity) {
            sack[currentIndex] = p;
            currentIndex++;
        }
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
    
    public synchronized void empty() {
        currentIndex = 0;
    }
}
