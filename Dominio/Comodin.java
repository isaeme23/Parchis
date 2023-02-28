package Dominio;

import java.io.Serializable;
/**
 * Write a description of class Comodin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Comodin implements Serializable{
    private String type;
    private Casilla casilla;

    /**
     * Constructor for objects of class Comodin
     */
    public Comodin(String type){
        this.type = type;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void removeComodin(){
        type = null;
    }
    
    private void setCasilla(Casilla casilla){
        this.casilla = casilla;
    }
    
    public String getType(){
        return type;
    }
}
