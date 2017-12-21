/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

public abstract class Elf extends ThreadBase implements Runnable{
    // Name attribute
    protected String name;
    
    // Getter for the name
    public String getName()
    {
        return name;
    }
    
    // Constructor
    public Elf(String name) {
        super("Elf " + name);
        
        this.name = name;
        stopped = false;
    }
}
