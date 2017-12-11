/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public abstract class Elf implements Runnable{
    protected String name;
    private PrintStream out;
    protected boolean stopped;
    
    public Elf(String name) {
        this.name = name;
        stopped = false;
        try {
            out = new PrintStream(new FileOutputStream("Elf " + name + ".txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Elf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        out.close();
    }
    
    public String getName()
    {
        return name;
    }
    
    protected void log(String line) {
        out.println(line);
    }
    
    public void stop() {
        stopped = true;
    }
}
