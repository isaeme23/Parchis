package Dominio;
import java.awt.*;
import java.util.*;
import java.io.Serializable;

/**
 * This class is used to represent the fichas of the game.
 * 
 * @author (Santiago Ospina & Isabella Manrique) 
 * @version (27/04/2022)
 */
public class Ficha implements Serializable{
    private Color color;
    private Casilla casilla;
    private boolean winner;
    private boolean prison;
    private boolean inEscalera = false;
    private int steps;
    private boolean salta = false;
    
    /**
     * Constructor of class Ficha
     * @param color: color of the piece
     * @param casilla: casilla where the piece is placed
     */
    public Ficha(Color color, Casilla casilla){
        this.color = color;
        this.casilla = casilla;
        steps = 0;
    }
    
    /**
     * Gets the color of the piece
     * @return the color of the piece
     */
    public Color getColor(){
        return color;
    }
    
    /**
     * Gets the number of the casilla where the piece is placed
     * @return the number of the casilla where the piece is placed
     */
    public int getNumber(){
        return casilla.getNumber();
    }
    
    /**
     * Sets the state of prison of the piece
     */
    public void setPrison(boolean inPrison){
        prison = inPrison;
    }
    
    /**
     * Gets the state prison of the piece
     * @return the state prison of the piece
     */
    public boolean getPrison(){
        return prison;
    }
    
    /**
     * Changes the casilla where the piece is placed
     */
    public void setCasilla(Casilla casilla){
        this.casilla = casilla;
    }
    
    /**
     * Changes the state of inEscalera
     * @param escalera: value for which we want to change the state of inEscalera
     */
    public void setInEscalera(boolean escalera){
        inEscalera = escalera;
    }
    
    /**
     * Changes the casilla where the piece is placed
     */
    public boolean getInEscalera(){
        return inEscalera;
    }
    
    public void setSteps(int steps){
        this.steps = steps;
    }
    
    public int getSteps(){
        return steps;
    }
    
    public String toString() {
        return "Ficha :" + steps + inEscalera + this.getNumber() + prison;
    }
    
    public boolean getSalta(){
        return salta;
    }
    
    public void setSalta(boolean salta){
        this.salta = salta;
    }
    
    public void setWinner(boolean winner){
        this.winner = winner;
    }
    
    public boolean getWinner(){
        return winner;
    }
}
