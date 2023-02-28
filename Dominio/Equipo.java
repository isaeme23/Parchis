package Dominio;

import java.util.*;
import java.awt.*;
import java.io.Serializable;

/**
 * This class is used to represent the equipos participating in the game.
 * 
 * @author (Santiago Ospina & Isabella Manrique) 
 * @version (27/04/2022)
 */
public class Equipo implements Serializable {
    private Color color;
    private ArrayList<Ficha> fichas = new ArrayList();
    private Home home;
    private int inicio;
    private int end;

    /**
     * Constructor of class Equipo
     * @param color: color of the equipo
     */
    public Equipo(Color color, int saltarinas){
        this.color = color;
        home = new Home(color);
        switch(saltarinas){
            case 0:
                for (int i = 0; i < 4; i++){
                    fichas.add(new Ficha(this.color, home));
                    home.setFicha(fichas.get(i));
                    fichas.get(i).setPrison(true);
                }
                break;
            case 1:
                fichas.add(new Ficha(this.color, home));
                fichas.add(new Saltarina(this.color, home));
                for (int i = 0; i < 2; i++){
                    fichas.add(new Ficha(this.color, home));
                }
                for (int i = 0; i < 4; i++){
                    home.setFicha(fichas.get(i));
                    fichas.get(i).setPrison(true);
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++){
                    if (i % 2 == 0){
                        fichas.add(new Ficha(this.color, home));
                        //fichas.get(i).setSalta(false);
                    } else{
                        fichas.add(new Saltarina(this.color, home));
                        //fichas.get(i).setSalta(true);
                    }
                    home.setFicha(fichas.get(i));
                    fichas.get(i).setPrison(true);
                }
                break;
            case 3:
                fichas.add(new Ficha(this.color, home));
                for (int i = 0; i < 3; i++){
                    fichas.add(new Saltarina(this.color, home));
                }
                for (int i = 0; i < 4; i++){
                    home.setFicha(fichas.get(i));
                    fichas.get(i).setPrison(true);
                }
                break;
            case 4:
                for (int i = 0; i < 4; i++){
                    fichas.add(new Saltarina(this.color, home));
                    home.setFicha(fichas.get(i));
                    fichas.get(i).setPrison(true);
                }
                break;
        }
    }
    /**
     * Sets the number of the casilla where the equipo starts
     */
    public void setInicio(int i){
        inicio = i;
    }
    /**
     * Sets the number of the casilla where the equipo ends
     */
    public void setEnd(int i){
        end = i;
    }
    /**
     * Gets the number of the casilla where the equipo starts
     * @return the number of the casilla where the equipo starts
     */
    public int getInicio(){
        return inicio;
    }
    /**
     * Gets the number of the casilla where the equipo ends
     * @return the number of the casilla where the equipo ends
     */
    public int getEnd(){
        return end;
    }
    /**
     * Changes the color of the equipo
     * @param color: the color that we want to change 
     */
    public void changeColor(Color color){
        this.color = color;
    }
    /**
     * Gets the color of the equipo
     * @param the color of the equipo
     */
    public Color getColor(){
        return color;
    }
    /**
     * Gets the number of the piece of the equipo that is requested
     * @param i: number of piece that we want to get
     * @return the ficha that was requested
     */
    public Ficha getFicha(int i){
        return fichas.get(i);
    }
    /**
     * Gets the home of the equipo
     * @return the home of the equipo
     */
    public Home getHome(){
        return home;
    }
    /**
     * Gets an ArrayList of the pieces that belong to the equipo that are in prison
     * @return Arraylist of the pieces that belong to the equpo that are in prison
     */
    public ArrayList<Ficha> inPrison(){
        ArrayList<Ficha> inPrison = new ArrayList();
        for (Ficha f: fichas){
            if (f.getPrison()){
                inPrison.add(f);
            }
        }
        return inPrison;
    }
    
    /**
     * Gets an ArrayList of the pieces that belong to the equipo that are in prison
     * @return Arraylist of the pieces that belong to the equpo that are in prison
     */
    public ArrayList<Ficha> notPrison(){
        ArrayList<Ficha> notPrison = new ArrayList();
        for (Ficha f: fichas){
            if (!f.getPrison()){
                notPrison.add(f);
            }
        }
        return notPrison;
    }
    
    public int getWinners(){
        ArrayList<Ficha> winners = new ArrayList();
        for (Ficha f: fichas){
            if (f.getWinner()){
                winners.add(f);
            }
        }
        return winners.size();
    }
}
