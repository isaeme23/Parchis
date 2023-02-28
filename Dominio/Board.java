package Dominio;

import java.util.*;
import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.Serializable;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is used to represent the board of the game.
 * 
 * @author (Santiago Ospina & Isabella Manrique) 
 * @version (27/04/2022)
 */
public class Board implements Serializable{
    private HashMap<Integer, Casilla> casillas = new HashMap();
    private HashMap<Equipo, ArrayList<Seguro>> escaleras = new HashMap();
    private HashMap<Color, Equipo> equipos = new HashMap();
    private HashMap<Color, String> jugadores = new HashMap();
    private ArrayList<Color> colores = new ArrayList<>();
    private ArrayList<Integer> horizon = new ArrayList();
    private Color player = Color.blue;
    private Ficha inicial;
    private int[] Dice = new int[2];
    private int pares;
    private Ficha ultima;
    private boolean salida;
    private int juegos;
    private int salieron;
    private String tipoDado;
    private ArrayList<Integer> randoms = new ArrayList();
    private ArrayList<String> nombres = new ArrayList();;
    
    /**
     * Constructor of class Board
     * @param especial: number of special tokens that are going to play
     */
    public Board(int especial){
        coloresA();
        randomCasillas();
        for (int i = 1; i <= 68; i++){
            if (i != 1 && i != 8 && i != 13 && i != 18 && i != 25 && i != 30 && i != 35 && i != 42 && i != 47 && i != 52 && i != 59 && i != 64 ){
                casillas.put(i, new Casilla(i));
                setCordinates(i, casillas.get(i));
            } else{
                casillas.put(i, new Seguro(i));
                setCordinates(i, casillas.get(i));
                casillas.get(i).setCombat(false);
            }
        }
        for (int i = 0; i < 2; i++){
            equipos.put(colores.get(i), new Equipo(colores.get(i), especial));
            ArrayList<Seguro> escalera = new ArrayList();
            for (int j = 1; j <= 8; j++){
                escalera.add(new Seguro(j));
            }
            escaleras.put(equipos.get(colores.get(i)), escalera);
        }
        for (Color c: colores){
            int var = 0;
            while (var < 2){
                this.equipos.get(c).getFicha(var).setCasilla(this.equipos.get(c).getHome());
                var++;
            }
            if (c.equals(Color.green)){
                this.equipos.get(c).setInicio(18);
                this.equipos.get(c).setEnd(13);
                this.equipos.get(c).getHome().setPos(399, 430);
            } else if (c.equals(Color.blue)){
                this.equipos.get(c).setInicio(52);
                this.equipos.get(c).setEnd(47);
                this.equipos.get(c).getHome().setPos(10, 30);
            }
        }
        orgEscalera();
        playsComodin();
    }

    /**
     * Makes the arraylist for the available colors
     */
    private void coloresA(){
        colores.add(Color.blue);
        colores.add(Color.green);
    }
    
    private void randomCasillas(){
        for (int i = 0; i < 4; i++){
             Random r = new Random();
             int value = r.nextInt(68) + 1;
             while(randoms.contains(value)){
                 value = r.nextInt(68) + 1;
             }
             randoms.add(value);
        }
    }
    
    private void playsComodin(){
        for (Integer i: randoms){
            if (i % 2 == 0){
                casillas.get(i).setComodin(new Comodin("Avanzar"));
            } else{
                casillas.get(i).setComodin(new Comodin("Carcel"));
            }
        }
    }
    
    private void setComodin(int finalPosition){
        for (int i = 0; i < randoms.size(); i++){
            if (randoms.get(i) == finalPosition){
                randoms.remove(i);
            }
        }
        Random r = new Random();
        int value = r.nextInt(68) + 1;
        int c = casillas.get(value).getFicha().size();
        while(randoms.contains(value) && value != finalPosition && c == 0){
            value = r.nextInt(68) + 1;
        }
        randoms.add(value);
        casillas.get(value).setComodin(casillas.get(finalPosition).getComodin());
        casillas.get(finalPosition).removeComodin();
    }
    
    /**
     * Throws both dice
     * @return the values of each dice
     */
    public void throwDice(){
        if (tipoDado.equals("Dado Normal")){
            int value1, value2, suma;
            Random nDigit = new Random();
            value1 = nDigit.nextInt(6) + 1;
            Random nDigit1 = new Random();
            value2 = nDigit1.nextInt(6) + 1;
            Dice[0] = value1;
            Dice[1] = value2;
            suma = Dice[0] + Dice[1];
        }else{
            int value1, value2, suma;
            Random nDigit = new Random();
            value1 = nDigit.nextInt(8) + 1;
            Random nDigit1 = new Random();
            value2 = nDigit1.nextInt(8) + 1;
            Dice[0] = value1;
            Dice[1] = value2;
            suma = Dice[0] + Dice[1];
        }
    }
    
    public void fichaDado(){
        int var1 = Dice[0];
        int var2 = Dice[1];
        if (!getTypeDado().equals("Dado Normal")){
            if (!inicial.getSalta()){
                if (Dice[0] == 1 || Dice[0] == 2 || Dice[0] == 3){
                    Dice[0] = var1 * 2;
                } 
                if (Dice[1] == 1 || Dice[1] == 2 || Dice[1] == 3){
                    Dice[1] = var2 * 2;
                }
            }else{
                if (Dice[0] == 8){
                    Dice[0] = var1 / 2;
                } 
                if (Dice[1] == 8){
                    Dice[1] = var2 / 2;
                }
            }
        }
    }
    
    public void setTypeDado(String type){
        this.tipoDado = type;
    }
    
    public String getTypeDado(){
        return tipoDado;
    }
    
    public void Dice() throws ParchisException{
        salieron = 0;
        if (Dice[0] == 5 || Dice[1] == 5 || (Dice[0] + Dice[1]) == 5){
            getOut();
        }
        if (salieron == 0 && equipos.get(player).getHome().getFicha().size() == 4){
            throw new ParchisException(ParchisException.ALL_IN_HOME);
        } else if (salieron > 0 && ((Dice[0] + Dice[1] == 5) || (Dice[0] == 5 && Dice[1] == 5))){
            throw new ParchisException(ParchisException.FIVES_NO_MOVE);
        }
    }
    /**
     * Chooses the ficha
     */
    public void choose(int choose)throws ParchisException{
        if (choose == 1 && !equipos.get(player).getFicha(0).getPrison()){
            inicial = equipos.get(player).getFicha(0);
        } else if (choose == 2 && !equipos.get(player).getFicha(1).getPrison()){
            inicial = equipos.get(player).getFicha(1);
        } else if (choose == 3 && !equipos.get(player).getFicha(2).getPrison()){
            inicial = equipos.get(player).getFicha(2);
        } else if (choose == 4 && !equipos.get(player).getFicha(3).getPrison()){
            inicial = equipos.get(player).getFicha(3);
        } else{
            throw new ParchisException(ParchisException.IN_PRISON);
        }
    }
    /**
     * Defines the diferent actions that can be made if one or both dices get 5 or the addition of them its 5 as well
     * @return the action that has to be made based on the number of fichas that can get out of prison
     */
    public String defineFive(){
        String five = "Ninguna";
        if ((Dice[0] == 5 && Dice[1] != 5) || (Dice[0] != 5 && Dice[1] == 5) || (Dice[0] + Dice[1]) == 5){
            five = "Una ficha";
        } else if (Dice[0] == 5 && Dice[1] == 5){
            five = "Dos fichas";
        }
        return five;
    }
    /**
     * Tells the user if one or two pieces got out of prision
     * @param dice1: value of the first dice
     * @param dice2: value of the second dice
     * @return true if one or two pieces got out of prision
     */
    public void getOut(){
        salieron = 0;
        salida = false;
        String five = defineFive();
        ArrayList<Ficha> prision = new ArrayList();
        prision = equipos.get(player).inPrison();
        if (five.equals("Una ficha") && prision.size() > 0){
            if (!casillas.get(equipos.get(player).getInicio()).getBlocked()){
                prision.get(0).setCasilla(casillas.get(equipos.get(player).getInicio()));
                prision.get(0).setSteps(1);
                prision.get(0).setPrison(false);
                casillas.get(equipos.get(player).getInicio()).setFicha(prision.get(0));
                if (casillas.get(equipos.get(player).getInicio()).getFicha().size() == 2){
                    casillas.get(equipos.get(player).getInicio()).setBlock(true);
                }
                equipos.get(player).getHome().removeFicha(prision.get(0));
                salida = true;
                salieron = 1;
            } else{
                salieron = 0;
            }
        } else if (five.equals("Dos fichas") && prision.size() == 1){
            if (!casillas.get(equipos.get(player).getInicio()).getBlocked()){
                prision.get(0).setCasilla(casillas.get(equipos.get(player).getInicio()));
                prision.get(0).setSteps(1);
                prision.get(0).setPrison(false);
                casillas.get(equipos.get(player).getInicio()).setFicha(prision.get(0));
                if (casillas.get(equipos.get(player).getInicio()).getFicha().size() == 2){
                    casillas.get(equipos.get(player).getInicio()).setBlock(true);
                }
                equipos.get(player).getHome().removeFicha(prision.get(0));
                salida = true;
                salieron = 1;
            }
        } else if (five.equals("Dos fichas") && prision.size() > 1){
            if (casillas.get(equipos.get(player).getInicio()).getFicha().size() == 0){
                prision.get(0).setCasilla(casillas.get(equipos.get(player).getInicio()));
                prision.get(0).setSteps(1);
                prision.get(0).setPrison(false);
                casillas.get(equipos.get(player).getInicio()).setFicha(prision.get(0));
                equipos.get(player).getHome().removeFicha(prision.get(0));
                casillas.get(equipos.get(player).getInicio());
                prision.get(1).setCasilla(casillas.get(equipos.get(player).getInicio()));
                prision.get(1).setPrison(false);
                prision.get(1).setSteps(1);
                casillas.get(equipos.get(player).getInicio()).setFicha(prision.get(1));
                casillas.get(equipos.get(player).getInicio()).setBlock(true);
                equipos.get(player).getHome().removeFicha(prision.get(1));
                salida = true;
                salieron = 2;
            }
        }
    }
    /**
     * Plays the game
     */
    public void playFicha1(int Ficha)throws ParchisException{
        int suma = Dice[0] + Dice[1];
        choose(Ficha);
        if (inicial != null){
            if (Dice[1] == 5 && Dice[0] != 5 && salieron == 1) {
                play1(inicial, Dice[0]);
            } else if (Dice[0] == 5 && Dice[1] == 5 && salieron == 0) {
                play1(inicial, Dice[0]);
            } else if (suma == 5 && salieron == 0){
                play1(inicial, Dice[0]);
            } else if (Dice[0] != 5 && Dice[1] != 5 && Dice[0] + Dice[1] != 5 && equipos.get(player).notPrison().size() > 0){
                play1(inicial, Dice[0]);
            } else if(Dice[0] == 5 && Dice[1] != 5 && equipos.get(player).inPrison().size() == 0){
                play1(inicial, Dice[0]);
            } else if (Dice[0] != 5 && Dice[1] == 5 && equipos.get(player).inPrison().size() == 0){
                play1(inicial, Dice[0]);
            } else if (Dice[0] == 5 && Dice[1] != 5 && salieron == 0 || Dice[0] != 5 && Dice[1] == 5 && salieron == 0){
                play1(inicial, Dice[0]);
            }
        }
        juegos++;
        decide(Dice[0], Dice[1], pares);
    }
    
    public void playFicha2(int Ficha)throws ParchisException{
        int suma = Dice[0] + Dice[1];
        boolean jugable = false;
        choose(Ficha);
        if (inicial != null){
            if (Dice[0] == 5 && Dice[1] != 5 && salieron == 1) {
                play1(inicial, Dice[1]);
                jugable = true;
            } else if (Dice[0] == 5 && Dice[1] == 5 && salieron == 0) {
                play1(inicial, Dice[1]);
                jugable = true;
            } else if(Dice[0] == 5 && Dice[1] == 5 && salieron == 1){
                play1(inicial, Dice[1]);
            } else if (suma == 5 && salieron == 0){
                play1(inicial, Dice[1]);
            } else if (Dice[0] != 5 && Dice[1] != 5 && Dice[0] + Dice[1] != 5 && equipos.get(player).notPrison().size() > 0){
                play1(inicial, Dice[1]);
            } else if(Dice[0] != 5 && Dice[1] == 5 && equipos.get(player).inPrison().size() == 0 && salieron == 0){
                play1(inicial, Dice[1]);
            } else if (Dice[0] == 5 && Dice[1] != 5 && equipos.get(player).inPrison().size() == 0 && salieron == 0){
                play1(inicial, Dice[1]);
            } else if (Dice[0] == 5 && Dice[1] != 5 && salieron == 0 || Dice[0] != 5 && Dice[1] == 5 && salieron == 0){
                play1(inicial, Dice[1]);
            }
        }
        juegos++;
        decide(Dice[0], Dice[1], pares);
    }
    /**
     * Tells the player of the ficha that was selected was able to move the value that was selected
     * @param ficha: ficha selected to play
     * @param value: number of casillas that the player wants the casilla to move
     */
    public void play1(Ficha ficha, int value)throws ParchisException{
        int initPosition = ficha.getNumber();
        int finalPosition = initPosition + value;
        int result;
        boolean moved = false;
        if (finalPosition > 68){
            finalPosition = finalPosition - 68;
        }
        if (ficha.getInEscalera()){
            if (ficha.getNumber() + value <= 8){
                escaleras.get(equipos.get(player)).get(initPosition - 1).removeFicha(ficha);
                ficha.setCasilla(escaleras.get(equipos.get(player)).get(finalPosition - 1));
                escaleras.get(equipos.get(player)).get(finalPosition - 1).setFicha(ficha);
                ultima = ficha;
                if (ficha.getNumber() == 8){
                    ficha.setWinner(true);
                }
                moved = true;
                checkWinner();
            } else{
                throw new ParchisException(ParchisException.CANT_MOVE);
            }
        } else{
            if (ficha.getSteps() + value > 64){
                result = finalPosition - equipos.get(player).getEnd();
                casillas.get(initPosition).removeFicha(ficha);
                ficha.setCasilla(escaleras.get(equipos.get(player)).get(result - 1));
                escaleras.get(equipos.get(player)).get(result - 1).setFicha(ficha);
                ficha.setSteps(ficha.getSteps() + value);
                ficha.setInEscalera(true);
                ultima = ficha;
            } else{
                moved = moveRules(ficha, initPosition, finalPosition);
                if (moved){
                    int pasos = ficha.getSteps();
                    ficha.setSteps(pasos + value);
                    ultima = ficha;
                    if (casillas.get(finalPosition).getComodin() != null){
                        if (casillas.get(finalPosition).getComodin().getType() == "Avanzar"){
                            play1(ficha, 5);
                            setComodin(finalPosition);
                        } else if (casillas.get(finalPosition).getComodin().getType() == "Carcel"){
                            ficha.setSteps(0);
                            casillas.get(finalPosition).removeFicha(ficha);
                            ficha.setCasilla(equipos.get(player).getHome());
                            equipos.get(player).getHome().setFicha(ficha);
                            ficha.setPrison(true);
                            setComodin(finalPosition);
                        }
                    }
                } else{
                    throw new ParchisException(ParchisException.CANT_MOVE);
                }
            }
            inicial = null;
        }
    }
    /**
     * Evaluates the different rules of the game that allow the ficha to move of not move and tells the player if it was able to move
     * @param ficha: ficha selected to play
     * @param start: number of the casilla where the selected ficha is
     * @param finish: number of the casilla where the player wants to move the selected ficha
     */
    public boolean moveRules(Ficha ficha, int start, int finish)throws ParchisException{
        boolean moved = false;
        if (!existBlock(start, finish)){
            if (canEat(ficha, finish)){
                play1(inicial, 20);
                moved = true;
                casillas.get(start).removeFicha(ficha);
                casillas.get(start).setBlock(false);
            } else if (!canEat(ficha, finish) && canBlock(ficha, finish)){
                moved = true;
                casillas.get(start).removeFicha(ficha);
                casillas.get(start).setBlock(false);
            } else if (!canEat(ficha, finish) && !canBlock(ficha, finish)){
                casillas.get(finish).setFicha(ficha);
                casillas.get(start).removeFicha(ficha);
                casillas.get(start).setBlock(false);
                ficha.setCasilla(casillas.get(finish));
                moved = true;
            }
        }
        return moved;
    }
    /**
     * Tells if there are any blockades ahead in the range of casillas where the piece is going to be moved
     * @param start: number of casilla where the selected ficha is
     * @param finish: number of the casilla where the player wants to move the selected ficha
     * @return if there are blockades ahead
     */
    public boolean existBlock(int start, int finish){
        boolean blocked = false;
        if (!inicial.getSalta()){
           for (int i = start + 1; i <= finish; i++){
                if (casillas.get(i).getBlocked()){
                    blocked = true;
                }
            } 
        } else{
            if (casillas.get(finish).getBlocked()){
                blocked = true;
            }
        }
        return blocked;
    }
    /**
     * Tells if the selected ficha can eat another one when it moves depending on the tipe of casilla where it is placed and eats it
     * @param ficha: ficha that wants to me moved and wants to know if it can eat another one
     * @param finish: number of the casilla where the player wants to move the selected ficha
     * @return if the ficha can eat another one
     */
    public boolean canEat(Ficha ficha, int finish){
        boolean eat = false;
        Ficha pieza;
        if (casillas.get(finish).getFicha().size() > 0 && casillas.get(finish).getCombat()){
            if (!ficha.getColor().equals(casillas.get(finish).getFicha().get(0).getColor())){
                eat = true;
                pieza = casillas.get(finish).getFicha().get(0);
                casillas.get(finish).removeFicha(pieza);
                casillas.get(finish).setFicha(ficha);
                pieza.setCasilla(this.equipos.get(pieza.getColor()).getHome());
                equipos.get(pieza.getColor()).getHome().setFicha(pieza);
                ficha.setCasilla(casillas.get(finish));
                pieza.setPrison(true);
                pieza.setSteps(0);
            }
        }
        return eat;
    }
    /**
     * Tells if the selected ficha can make a blockade with one when it moves depending on the tipe of casilla where it is placed and makes a blockade
     * @param ficha: ficha that wants to me moved and wants to know if it can make a blockade with another one
     * @param finish: number of the casilla where the player wants to move the selected ficha
     */
    public boolean canBlock(Ficha ficha, int finish){
        boolean block = false;
        if (casillas.get(finish).getFicha().size() > 0 && casillas.get(finish).getCombat()){
           if (ficha.getColor().equals(casillas.get(finish).getFicha().get(0).getColor())){
                block = true;
                casillas.get(finish).setBlock(true);
                casillas.get(finish).setFicha(ficha);
                ficha.setCasilla(casillas.get(finish));
            }
        } else if (casillas.get(finish).getFicha().size() > 0 && !casillas.get(finish).getCombat()){
            block = true;
            casillas.get(finish).setBlock(true);
            casillas.get(finish).setFicha(ficha);
            ficha.setCasilla(casillas.get(finish));
        }
        return block;
    }
    /**
     * Modifies the player that is playing
     */
    public void turn(){
        if (player.equals(Color.blue)){
            player = Color.green;
            pares = 0;
            juegos = 0;
        } else{
            player = Color.blue;
            pares = 0;
            juegos = 0;
        }
    }
    
    public void decide(int dice1, int dice2, int pares){
        if (juegos == 2){
            if (dice1 == dice2){
               if (pares == 2){
                   ultima.setCasilla(this.equipos.get(ultima.getColor()).getHome());
                   ultima.setSteps(0);
                   this.equipos.get(ultima.getColor()).getHome().setFicha(ultima);
                   juegos = 0;
               } else{
                   pares++;
                   juegos = 0;
               }
            } else{
                turn();
                juegos = 0;
            }
        }
    }
    
    public void checkWinner()throws ParchisException{
        if (equipos.get(player).getWinners() == 4){
            throw new ParchisException(ParchisException.IS_WINNER);
        }
    }
    
    public void addNames(String nombre){
        nombres.add(nombre);
    }
    
    public void colorName(){
        jugadores.put(colores.get(0), nombres.get(0));
        jugadores.put(colores.get(1), nombres.get(1));
    }
    
    public String getName(){
        return jugadores.get(player);
    }
    
    public Color getJugadorTurno(){
        return player;
    }
    
    public void setDice(int a, int b){
        Dice[0]  = a;
        Dice[1] = b;
    }
    
    public Equipo getEquipo(){
        return equipos.get(player);
    }
    
    public Casilla getCasilla(int num){
        return casillas.get(num);
    }
    
    public HashMap getCasillas(){
        return casillas;
    }
    
    public Equipo getEquipo(Color color){
        return equipos.get(color);
    }
    
    public ArrayList getEscalera(Color color){
        return escaleras.get(equipos.get(color));
    }
    
    public ArrayList getHorizon(){
        return horizon;
    }
    
    public int[] getDice(){
        return Dice;
    }
    
    public String getPlayer(){
        String colour ="";
        if (player.equals(Color.blue)){
            colour = "Azul";
        } else{
            colour= "Verde";
        }
        return colour;
    }
    
    /**
    * Metodo que nos permitira abrir un archivo. Abre el archivoy lo lee.
    * Maneja nuevos tipos de excepciones a diferencia de la version anterior.
    * @param file: archivo que abriremos
     */

    public Board abra(File file) throws ParchisException{
        if (!file.getName().endsWith(".dat")) throw new ParchisException(ParchisException.DAT_ERROR);
        Board board = null;
    try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        board = (Board) in.readObject();
        in.close();
    }catch (IOException | ClassNotFoundException e) {
        System.out.println(e.getMessage());
        throw new ParchisException("Error general al abrir" + file.getName());
    }
    return board;
    }

    /**
    * Metodo que nos permitira guardar un archivo, abre el archivo nuevo y lo escribe con la informacion proporcionada.
    * Maneja nuevos tipos de excepcion a diferencia de la version anterior.
    * @param file: archivo que guardaremos
     */
    public void guarde(File file) throws ParchisException{
        if (!file.getName().endsWith(".dat")) throw new ParchisException(ParchisException.DAT_ERROR);
    try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();
    }catch (IOException e) {
        throw new ParchisException("Ocurrio un error al salvar " + file.getName());
        }
    }

    private void setCordinates(int n, Casilla casilla){
        switch (n){
            case 1:
                casilla.setPos(111, 366);
                casilla.setColor(Color.red);
                horizon.add(1);
                break;
            case 2:
                casilla.setPos(136, 366);
                casilla.setColor(Color.white);
                horizon.add(2);
                break;
            case 3:
                casilla.setPos(161, 366);
                casilla.setColor(Color.white);
                horizon.add(3);
                break;
            case 4:
                casilla.setPos(186, 366);
                casilla.setColor(Color.white);
                horizon.add(4);
                break;
            case 5:
                casilla.setPos(210, 432);
                casilla.setColor(Color.white);
                break;
            case 6:
                casilla.setPos(210, 457);
                casilla.setColor(Color.white);
                break;
            case 7:
                casilla.setPos(210, 482);
                casilla.setColor(Color.white);
                break;
            case 8:
                casilla.setPos(210, 507);
                casilla.setColor(Color.gray);
                break;
            case 9:
                casilla.setPos(210, 532);
                casilla.setColor(Color.white);
                break;
            case 10:
                casilla.setPos(210, 557);
                casilla.setColor(Color.white);
                break;
            case 11:
                casilla.setPos(210, 582);
                casilla.setColor(Color.white);
                break;
            case 12:
                casilla.setPos(210, 607);
                casilla.setColor(Color.white);
                break;
            case 13:
                casilla.setPos(273, 607);
                casilla.setColor(Color.gray);
                break;
            case 14:
                casilla.setPos(336, 607);
                casilla.setColor(Color.white);
                break;
            case 15:
                casilla.setPos(336, 582);
                casilla.setColor(Color.white);
                break;
            case 16:
                casilla.setPos(336, 557);
                casilla.setColor(Color.white);
                break;
            case 17:
                casilla.setPos(336, 532);
                casilla.setColor(Color.white);
                break;
            case 18:
                casilla.setPos(336, 507);
                casilla.setColor(Color.green);
                break;
            case 19:
                casilla.setPos(336, 482);
                casilla.setColor(Color.white);
                break;
            case 20:
                casilla.setPos(336, 457);
                casilla.setColor(Color.white);
                break;
            case 21:
                casilla.setPos(336, 432);
                casilla.setColor(Color.white);
                break;
            case 22:
                casilla.setPos(400, 366);
                casilla.setColor(Color.white);
                horizon.add(22);
                break;
            case 23:
                casilla.setPos(425, 366);
                casilla.setColor(Color.white);
                horizon.add(23);
                break;
            case 24:
                casilla.setPos(450, 366);
                casilla.setColor(Color.white);
                horizon.add(24);
                break;
            case 25:
                casilla.setPos(475, 366);
                casilla.setColor(Color.gray);
                horizon.add(25);
                break;
            case 26:
                casilla.setPos(500, 366);
                casilla.setColor(Color.white);
                horizon.add(26);
                break;
            case 27:
                casilla.setPos(525, 366);
                casilla.setColor(Color.white);
                horizon.add(27);
                break;
            case 28:
                casilla.setPos(550, 366);
                casilla.setColor(Color.white);
                horizon.add(28);
                break;
            case 29:
                casilla.setPos(575, 366);
                casilla.setColor(Color.white);
                horizon.add(29);
                break;
            case 30:
                casilla.setPos(575, 302);
                casilla.setColor(Color.gray);
                horizon.add(30);
                break;
            case 31:
                casilla.setPos(575, 237);
                casilla.setColor(Color.white);
                horizon.add(31);
                break;
            case 32:
                casilla.setPos(550, 237);
                casilla.setColor(Color.white);
                horizon.add(32);
                break;
            case 33:
                casilla.setPos(525, 237);
                casilla.setColor(Color.white);
                horizon.add(33);
                break;
            case 34:
                casilla.setPos(500, 237);
                casilla.setColor(Color.white);
                horizon.add(34);
                break;
            case 35:
                casilla.setPos(475, 237);
                casilla.setColor(Color.yellow);
                horizon.add(35);
                break;
            case 36:
                casilla.setPos(450, 237);
                casilla.setColor(Color.white);
                horizon.add(36);
                break;
            case 37:
                casilla.setPos(425, 237);
                casilla.setColor(Color.white);
                horizon.add(37);
                break;
            case 38:
                casilla.setPos(400, 237);
                casilla.setColor(Color.white);
                horizon.add(38);
                break;
            case 39:
                casilla.setPos(336, 210);
                casilla.setColor(Color.white);
                break;
            case 40:
                casilla.setPos(336, 185);
                casilla.setColor(Color.white);
                break;
            case 41:
                casilla.setPos(336, 160);
                casilla.setColor(Color.white);
                break;
            case 42:
                casilla.setPos(336, 135);
                casilla.setColor(Color.gray);
                break;
            case 43:
                casilla.setPos(336, 110);
                casilla.setColor(Color.white);
                break;
            case 44:
                casilla.setPos(336, 85);
                casilla.setColor(Color.white);
                break;
            case 45:
                casilla.setPos(336, 60);
                casilla.setColor(Color.white);
                break;
            case 46:
                casilla.setPos(336, 35);
                casilla.setColor(Color.white);
                break;
            case 47:
                casilla.setPos(273, 35);
                casilla.setColor(Color.gray);
                break;
            case 48:
                casilla.setPos(210, 35);
                casilla.setColor(Color.white);
                break;
            case 49:
                casilla.setPos(210, 60);
                casilla.setColor(Color.white);
                break;
            case 50:
                casilla.setPos(210, 85);
                casilla.setColor(Color.white);
                break;
            case 51:
                casilla.setPos(210, 110);
                casilla.setColor(Color.white);
                break;
            case 52:
                casilla.setPos(210, 135);
                casilla.setColor(Color.blue);
                break;
            case 53:
                casilla.setPos(210, 160);
                casilla.setColor(Color.white);
                break;
            case 54:
                casilla.setPos(210, 185);
                casilla.setColor(Color.white);
                break;
            case 55:
                casilla.setPos(210, 210);
                casilla.setColor(Color.white);
                break;
            case 56:
                casilla.setPos(186, 237);
                casilla.setColor(Color.white);
                horizon.add(56);
                break;
            case 57:
                casilla.setPos(161, 237);
                casilla.setColor(Color.white);
                horizon.add(57);
                break;
            case 58:
                casilla.setPos(136, 237);
                casilla.setColor(Color.white);
                horizon.add(58);
                break;
            case 59:
                casilla.setPos(111, 237);
                casilla.setColor(Color.gray);
                horizon.add(59);
                break;
            case 60:
                casilla.setPos(86, 237);
                casilla.setColor(Color.white);
                horizon.add(60);
                break;
            case 61:
                casilla.setPos(61, 237);
                casilla.setColor(Color.white);
                horizon.add(61);
                break;
            case 62:
                casilla.setPos(36, 237);
                casilla.setColor(Color.white);
                horizon.add(62);
                break;
            case 63:
                casilla.setPos(11, 237);
                casilla.setColor(Color.white);
                horizon.add(63);
                break;
            case 64:
                casilla.setPos(11, 302);
                casilla.setColor(Color.gray);
                horizon.add(64);
                break;
            case 65:
                casilla.setPos(11, 366);
                casilla.setColor(Color.white);
                horizon.add(65);
                break;
            case 66:
                casilla.setPos(36, 366);
                casilla.setColor(Color.white);
                horizon.add(66);
                break;
            case 67:
                casilla.setPos(61, 366);
                casilla.setColor(Color.white);
                horizon.add(67);
                break;
            case 68:
                casilla.setPos(86, 366);
                casilla.setColor(Color.white);
                horizon.add(68);
                break;
        }
    }
    
    private void orgEscalera(){
        ArrayList<Seguro> verde = escaleras.get(equipos.get(Color.green));
        ArrayList<Seguro> azul = escaleras.get(equipos.get(Color.blue));
        int var = 607;
        int var1 = 35;
        for(int i = 0; i < verde.size(); i++){
            verde.get(i).setPos(273, var - 25);
            verde.get(i).setColor(Color.green);
            var = var - 25;
        }
        
        for(int i = 0; i < azul.size(); i++){
            azul.get(i).setPos(273, var1 + 25);
            azul.get(i).setColor(Color.blue);
            var1 = var1 + 25;
        }
    }
}