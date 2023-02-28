package Dominio;
import java.util.*;
import java.awt.Color;

/**
 * This class is used to represent the home of a equipo in the game.
 * It extends from the Casilla class
 * 
 * @author (Santiago Ospina & Isabella Manrique) 
 * @version (27/04/2022)
 */
public class Home extends Casilla{
    private Color color;
    private ArrayList<Ficha> fichas = new ArrayList();
    private static final boolean blocked = false;
    private boolean combat = false;
    /**
     * Constructor for objects of class Home
     */
    public Home(Color color){
        super(0);
        this.color = color;
    }
}
