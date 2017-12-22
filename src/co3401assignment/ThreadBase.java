/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public abstract class ThreadBase {
    // Output stream
    private PrintStream out;
    
    // Filename/console logging prefix
    private String logtag;
    
    // Has the thread been told to stop
    protected boolean stopped;
    
    // Protected constructor
    protected ThreadBase(String fileName) {
        this.logtag = fileName;
        
        try {
            this.out = new PrintStream(new FileOutputStream(fileName + ".txt"));
        } catch (FileNotFoundException ex) {
            this.out = null;
        }
    }
    
    // Protected log method
    protected void log(String log) {
        //System.out.println(logtag + " " + log);
        
        if (this.out == null) {
            return;
        }
        
        this.out.println("Time " + Clock.getTime() + ": " + log);
    }
    
    public void close() {
        out.close();
    }
    
    public void stop() {
        stopped = true;
    }
}
