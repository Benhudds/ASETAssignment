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
public class ConveyorBelt {
    private int id;
    
    public int getId() {
        return id;
    }
    
    private Present[] queue;
    private int head;
    private int tail;
    public final int capacity;
    
    private Semaphore mutex;
    private Semaphore avail;
    private Semaphore free;
   
    public ConveyorBelt(int id, int size) {
        this.id = id;
        
        // Create array
        capacity = size;
        queue = new Present[capacity];
        
        // Set as initial positions
        head = -1;
        tail = 0;
        
        // Set mutex semaphore
        mutex = new Semaphore(1);
        
        // Set avail semaphore
        avail = new Semaphore(0);
        
        // Set free semaphore;
        free = new Semaphore(size);
        
    }
    
    public void enqueue(Present newPresent) throws InterruptedException {
        // Get the free position mutex
        free.acquire();
        
        // Get the mutex lock
        mutex.acquire();
        
        if (head != tail) {
            if (head == -1) {
                head = 0;
                
                queue[head] = newPresent;
                tail = 1;
            } else if (tail == capacity) {
                tail = 0;
                queue[tail] = newPresent;
                tail++;
            } else if (tail + 1 != head){
                queue[tail] = newPresent;
                tail++;
            }
        }
        
        avail.release();
        mutex.release();
    }
    
    public Present dequeue() throws InterruptedException {
        avail.acquire();
        mutex.acquire();
        
        Present p = queue[head];
        if (head == tail) {
            head = -1;
            tail = 0;
        } else if (head == capacity - 1) {
            head = 0;
        } else {
            head++;
        }
        
        mutex.release();
        free.release();
        
        return p;
    }
    
    public boolean hasSpace() {
        return head != tail;
    }
    
    public boolean empty() {
        return avail.availablePermits() == 0;
    }
}