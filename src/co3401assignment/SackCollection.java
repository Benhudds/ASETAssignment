/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Ben
 */
public class SackCollection {
    private Sack[] arr;
    private Semaphore mutex;
    private int index;
    private int capacity;
    
    public SackCollection(int capacity) {
        arr = new Sack[capacity];
        mutex = new Semaphore(1);
        index = 0;
        this.capacity = capacity;
    }
    
    public int size() {
        return capacity;
    }
    
    public void replace(int rindex, Sack sack) throws InterruptedException {
        mutex.acquire();
        
        if (0 <= rindex && rindex < capacity) {
            arr[rindex] = sack;
        }
        
        mutex.release();
    }
    
    public void replace(Sack sack1, Sack sack2) throws InterruptedException {
        mutex.acquire();
        
        for(int i = 0; i < capacity; i++) {
            if (arr[i] == sack1) {
                arr[i] = sack2;
                break;
            }
        }
        
        mutex.release();
    }
    
    public void add(Sack sack) throws InterruptedException{
        mutex.acquire();
        
        if (0 <= index && index < capacity)
        {
            arr[index] = sack;
            index++;
        }
        
        mutex.release();
    }
    
    public Sack get(int index) {
        return arr[index];
    }
}
