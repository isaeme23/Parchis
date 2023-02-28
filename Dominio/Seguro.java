package Dominio;

import java.util.*;
import java.awt.Color;
/**
 * This class is used to represent the Casillas that are safe in the game.
 * 
 * @author (Santiago Ospina & Isabella Manrique) 
 * @version (27/04/2022)
 */
public class Seguro extends Casilla{
    private int number;
    private boolean blocked;
    private boolean combat = true;
    private ArrayList<Ficha> fichas = new ArrayList();
    private boolean inEscalera;

    /**
     * Constructor for objects of class Seguro
     */
    public Seguro(int number){
        super(number);
        this.number = number;
    }
    
    public void setEscalera(boolean inEscalera){
        this.inEscalera = inEscalera;
    }
    
    public boolean getEscalera(){
        return inEscalera;
    }
}
