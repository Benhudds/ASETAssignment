/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.concurrent.Semaphore;

public class ConveyorBelt {
    // Conveyor belt id
    private int id;
    
    // Getter for the id
    public int getId() {
        return id;
    }
    
    // Present array
    private final Present[] queue;
    
    // Head of queue
    private int head;
    
    // Tail of queue
    private int tail;
    
    // Capacity of the queue
    private final int capacity;
    
    // Producer Consumer problem semaphores
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
    
    // Enqueue method
    public void enqueue(Present newPresent) throws InterruptedException {
        // Get the free position mutex
        free.acquire();
        
        // Get the mutex lock
        mutex.acquire();
        
        if ((head == 0 && tail == capacity - 1) || tail == head - 1) {
        } else {
            if (head == -1) {
                // Insert first element
                head = tail = 0;
                queue[tail] = newPresent;
            } else if (tail == capacity - 1 && head != 0) {
                tail = 0;
                queue[tail] = newPresent;
            } else {
                tail++;
                queue[tail] = newPresent;
            }
        
            avail.release();
        }
    
        mutex.release();
    }
    
    // Dequeue method
    public Present dequeue() throws InterruptedException {
        avail.acquire();
        mutex.acquire();
        Present p = null;
        
        if (head != -1) {
            p = queue[head];
            queue[head] = null;
            
            if (head == tail) {
                head = tail = -1;
            } else if (head == capacity - 1) {
                head = 0;
            } else {
                head++;
            }
            
            free.release();
        }
        
        mutex.release();
        return p;
    }
    
    // Checks if there is space in the queue
    public boolean hasSpace() {
        return free.availablePermits() != 0;
    }
    
    // Checks if the queue is empty
    public boolean empty() {
        return avail.availablePermits() == 0;
    }
    
    // Returns the number of presents in the queue
    public int getNumberOfPresents() {
        return avail.availablePermits();
    }
}