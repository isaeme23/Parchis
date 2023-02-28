package Dominio;


/**
 * Write a description of class ParchisException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ParchisException extends Exception{
    // instance variables - replace the example below with your own
    public static final String ALL_IN_HOME = "No puede realizar un movimiento, no han salido fichas";
    public static final String IN_PRISON = "La ficha esta en prision, escoja otra.";
    public static final String FIVES_NO_MOVE = "Ya han salido fichas, no quedan movimientos disponibles";
    public static final String CANT_MOVE = "La ficha no puede moverse.";
    public static final String DAT_ERROR = "El archivo no termina en .dat .";
    public static final String IS_WINNER = "Ha ganado el juego. Felicidades!";

    /**
     * Constructor for objects of class ParchisException
     */
    public ParchisException(String message){
        super(message);
    }
}
