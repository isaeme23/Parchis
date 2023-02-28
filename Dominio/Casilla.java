package Dominio;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.io.Serializable;

/**
 * This class is used to represent the casillas of the game.
 * 
 * @author (Santiago Ospina & Isabella Manrique) 
 * @version (27/04/2022)
 */
public class Casilla implements Serializable{
    private int number;
    private ArrayList<Ficha> fichas = new ArrayList();
    private boolean blocked = false;
    private boolean combat = true;
    private int posX;
    private int posY;
    private Color color;
    private Comodin comodin = null;
    
    /**
     * Constructor of class Casilla
     * @param number: number assigned to the casilla
     */
    public Casilla(int number){
        this.number = number;
    }
    
    public void setPos(int x, int y){
        posX = x;
        posY = y;
    }
    
    public int getX(){
        return posX;
    }
    
    public int getY(){
        return posY;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public Color getColor(){
        return color;
    }
    
    /**
     * Gets the number assigned to the casilla
     * @return the number of the casilla
     */
    public int getNumber(){
        return number;
    } 
    /**
     * Gets the status blocked of the casilla
     * @return blocked state
     */
    public boolean getBlocked(){
        return blocked;
    }
    /**
     * Gets the ficha of fichas that are in the casilla
     * @return Arraylist with the fichas that are in the casilla
     */
    public ArrayList<Ficha> getFicha(){
        return fichas;
    }
    /**
     * Adds a ficha to the casilla
     * @param ficha: ficha that wants to be in the casilla
     */
    public void setFicha(Ficha ficha){
        fichas.add(ficha);
    }
    /**
     * Removes a certain ficha from the casilla
     */
    public void removeFicha(Ficha ficha){
        fichas.remove(ficha);
    }
    /**
     * Gets the status combat of the casilla depending on its type
     */
    public boolean getCombat(){
        return combat;
    }
    /**
     * Changes the state blocked
     * @param block: value that wants to be set as blocked
     */
    public void setBlock(boolean block){
        blocked = block;
    }
    
    public void setCombat(boolean combat){
        this.combat = combat;
    }
    
    public void setComodin(Comodin comodin){
        this.comodin = comodin;
    }
    
    public Comodin getComodin(){
        return comodin;
    }
    
    public void removeComodin(){
        this.comodin = null;
    }
    
    public String toString() {
        return "Casilla : "+ number + " "+ posX +" "+ posY;
    }
}