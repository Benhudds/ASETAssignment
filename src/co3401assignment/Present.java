/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co3401assignment;

public class Present {
    
    // Type of present
    private PresentType typeOfToy;
    
    // Getter for type of present
    public PresentType getPresentType(){
        return typeOfToy;
    }
    
    // Age of present
    private int age;
    
    // Getter for age of present
    public int getAge() {
        return age;
    }
    
    // Constructor
    public Present(PresentType type, int age) {
        typeOfToy = type;
        this.age = age;
    }
}
