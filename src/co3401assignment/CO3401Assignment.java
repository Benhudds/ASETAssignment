/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

public class CO3401Assignment {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(int i = 0; i < 1000; i++) {
            ParcelSortingMachine psm = new ParcelSortingMachine();
            psm.run();
            /*
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CO3401Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                psm.stop();
            } catch (InterruptedException ex) {
                Logger.getLogger(CO3401Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            psm.report();
        }
    }
}
