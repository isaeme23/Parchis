package Dominio;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class POOBchisTest{
   
    private Board board;

    @Before
    public void setUp(){
        board = new Board(0);
        board.setTypeDado("Dado Normal");
    }
    
    @Test
    public void shouldGetOutOfJail(){
          board.setDice(5, 2);
          try{
              board.Dice();
          } catch(ParchisException e){}
          assertEquals(3, board.getEquipo().inPrison().size());
    }  
    
    @Test
    public void shouldtwoGetOutOfJail(){
          board.setDice(5, 5);
          try{
              board.Dice();
          } catch(ParchisException e){}
          assertEquals(2, board.getEquipo().inPrison().size());
    }  
    
    @Test
    public void shouldGetDoubleTurn(){
          board.setDice(5, 5);
          try{
              board.Dice();
          } catch(ParchisException e){}
         try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          Color color = board.getJugadorTurno();
          assertEquals(color, Color.blue);
    }
    
    @Test
    public void shouldNotGetOut(){
          board.setDice(1, 2);
          try{
              board.Dice();
          } catch(ParchisException e){}
          assertEquals(4, board.getEquipo().inPrison().size());
    } 
    
    @Test
    public void shouldBeAbleToPlay(){
          board.setDice(5, 2);
          try{
              board.Dice();
          } catch(ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          board.turn();
          assertEquals(54, board.getEquipo().getFicha(0).getNumber());
    }
    
    @Test
    public void shouldBeAbleToEat(){
          Casilla casilla = board.getCasilla(20);
          board.getEquipo().getFicha(0).setCasilla(casilla);
          casilla.setFicha(board.getEquipo().getFicha(0));
          board.turn();
          board.setDice(5, 2);
          try{
              board.Dice();
          } catch(ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          assertEquals(4, board.getEquipo().inPrison().size());
    }
    
    @Test
    public void shouldBeAbleToBlock(){
          board.turn();
          board.setDice(5,5);
          try{
              board.Dice();
          } catch(ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          board.setDice(2,2);
          try{
              board.Dice();
          } catch(ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          assertEquals(false, board.getCasilla(20).getBlocked());
    }
    
    @Test
    public void shouldNotBeAbleToMove(){
          board.turn();
          board.setDice(5,5);
              try{
              board.Dice();
          } catch (ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          board.setDice(2,2);
          try{
              board.Dice();
          } catch (ParchisException e){}
              try{
              board.Dice();
          } catch (ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          board.turn();
          Casilla casilla = board.getCasilla(18);
          board.getEquipo().getFicha(0).setCasilla(casilla);
          casilla.setFicha(board.getEquipo().getFicha(0));
          board.setDice(3, 1);
          try{
              board.Dice();
          } catch(ParchisException e){}
          try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
          assertNotEquals(board.getEquipo().getFicha(0).getNumber(), 22);
    }
    
    @Test
    public void shouldGetToStairs(){
        Casilla casilla = board.getCasilla(45);
        board.getEquipo().getFicha(0).setCasilla(casilla);
        casilla.setFicha(board.getEquipo().getFicha(0));
        board.getEquipo().getHome().removeFicha(board.getEquipo().getFicha(0));
        board.getEquipo().getFicha(0).setSteps(62);
        board.getEquipo().getFicha(0).setPrison(false);
        board.setDice(1, 3);
        try{
            board.Dice();
        } catch(ParchisException e){}
        try{
            board.playFicha1(1);
          } catch(ParchisException e){}
          try{
              board.playFicha2(1);
          } catch (ParchisException e){}
        board.turn();
        assertEquals(2, board.getEquipo().getFicha(0).getNumber());
    }
    
    @After
    public void tearDown(){
    }
}