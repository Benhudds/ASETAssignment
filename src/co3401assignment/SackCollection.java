/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.Semaphore;

public class SackCollection {
    // Array of Sacks
    private final Sack[] arr;
    
    // Total capacity in collection
    private final int capacity;
    
    // 
    private int index;
    
    public SackCollection(int capacity) {
        arr = new Sack[capacity];
        index = 0;
        this.capacity = capacity;
    }
    
    public int size() {
        return index;
    }
    
    public void replace(int rindex, Sack sack) {
        
        if (0 <= rindex && rindex < capacity) {
            arr[rindex] = sack;
        }
        
    }
    
    public void replace(Sack sack1, Sack sack2) {
        
        for(int i = 0; i < capacity; i++) {
            if (arr[i] == sack1) {
                arr[i] = sack2;
                break;
            }
        }
        
    }
    
    public void add(Sack sack) {
        
        if (0 <= index && index < capacity)
        {
            arr[index] = sack;
            index++;
        }
        
    }
    
    public Sack get(int index) {
        return arr[index];
    }
}
