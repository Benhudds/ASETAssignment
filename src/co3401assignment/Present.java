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
public class Present {
    private PresentType typeOfToy;
    private int age;
    
    public PresentType getPresentType(){
        return typeOfToy;
    }
    
    
    public int getAge() {
        return age;
    }
    
    public Present(PresentType type, int age) {
        typeOfToy = type;
        this.age = age;
    }
}
