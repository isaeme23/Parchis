package Dominio;
import java.awt.Color;

/**
 * Write a description of class Saltarina here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Saltarina extends Ficha{
    // instance variables - replace the example below with your own
    private boolean salta = true;

    /**
     * Constructor for objects of class Saltarina
     */
    public Saltarina(Color color, Casilla casilla){
        super(color, casilla);
    }
    
    public boolean getSalta(){
        return salta;
    }
}
