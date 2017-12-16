/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class CO3401Assignment {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            ParcelSortingMachine psm = new ParcelSortingMachine();
            Thread t = new Thread(psm);
            t.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CO3401Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                psm.stop();
            } catch (InterruptedException ex) {
                Logger.getLogger(CO3401Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }
            psm.report();
        }
    }
}
