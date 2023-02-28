package Presentacion;
import Dominio.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

import java.util.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
public class ParchisGUI extends JFrame{
    private static final Dimension PREFERRED_DIMENSION = new Dimension(500,300);

    private JPanel inicio, escoger, contenedor, opciones, labels, buttons, tFichas;
    private JComboBox tipoTablero, tipoFicha, dadoEscogido, opFicha;
    private JButton comenzar, house, play, dado1, dado2, cerrar, lanzar, finTurno, continuar;
    private Board juego;
    private JLabel modo, dice1, dice2, fichas, turno, valor1, valor2, numF, tipoDado, enCarcel, winners, nombres;
    private JFrame board, seleccion, names;
    private JCheckBox comodin1, comodin2;
    private PaintBoard parchis;
    
    private JTextField nombreF, nombreS;
    
    static JMenuBar menu;
    static JMenu opc;
    static JMenuItem abrir, guardar, terminar;

    private JFileChooser filechoose;
    
    private int numFicha;
    private int saltarinas;
    private int[] Dice;
    private String n1 = "";
    private String n2 = "";

    private ParchisGUI(){
        prepareElements();
        prepareActions();
    }

    private void prepareElements(){
      setTitle("Seleccion de caracteristicas de juego");
      setLayout(null);
      Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
      int height = pantalla.height;
      int width = pantalla.width;
      setSize(height/3, width/3);     
    
      setLocationRelativeTo(null);        
      setSize(PREFERRED_DIMENSION);
      prepareOptions();
      prepareButtons();
    }
    
    private void prepareButtons(){
        play = new JButton("Play");
        play.setEnabled(false);
        lanzar = new JButton("Lanzar dados");
        dado1 = new JButton("Jugar con dado 1");
        dado2 = new JButton("Jugar con dado 2");
        cerrar = new JButton("Cerrar");
        finTurno = new JButton("Fin Turno");
        continuar = new JButton("Confirmar");
    }
    
    private void prepareElementosMenu(){
        menu = new JMenuBar();
        opc = new JMenu("Menu");
        abrir = new JMenuItem("Abrir");
        guardar = new JMenuItem("Salvar");
        terminar = new JMenuItem("Terminar Juego");
        opc.add(abrir);
        opc.add(new JSeparator());
        opc.add(guardar);
        opc.add(new JSeparator());
        opc.add(terminar);
    }

    private void prepareOptions(){
      inicio = new JPanel();
      comenzar = new JButton("Comenzar a jugar");
      modo = new JLabel("Modo de juego:");
      escoger = new JPanel();
      escoger.setLayout(new GridLayout(1, 2, 3, 3));
      inicio.setLayout(new GridLayout(5, 1, 3, 3));
      escoger.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), new TitledBorder("Seleccion modo de juego")));
      inicio.setBounds(10, 10, 470, 250);
      tipoTablero = new JComboBox();
      tipoTablero.addItem("Jugador vs Jugador");
      tipoTablero.addItem("Jugador vs Maquina");
      escoger.add(modo);
      escoger.add(tipoTablero);
      
      tFichas = new JPanel();
      tFichas.setLayout(new GridLayout(1, 2, 3, 3));
      tFichas.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), new TitledBorder("Seleccion numero de fichas especiales")));
      numF = new JLabel("Numero de fichas saltarinas:");
      tipoFicha = new JComboBox();
      tipoFicha.addItem("0");
      tipoFicha.addItem("1");
      tipoFicha.addItem("2");
      tipoFicha.addItem("3");
      tipoFicha.addItem("4");
      tFichas.add(numF);
      tFichas.add(tipoFicha);
      
      JPanel comodines = new JPanel();
      comodines.setLayout(new GridLayout(1, 2, 3, 3));
      comodin1 = new JCheckBox("Avanzar 5");
      comodin2 = new JCheckBox("Ir a prision");
      comodines.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), new TitledBorder("Seleccion de comodines")));
      comodines.add(comodin1);
      comodines.add(comodin2);
      
      JPanel dados = new JPanel();
      dados.setLayout(new GridLayout(1, 2, 3, 3));
      dados.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), new TitledBorder("Seleccion de tipo de dado")));
      JLabel dadoTipo = new JLabel("Tipo de dado:");
      dadoEscogido = new JComboBox();
      dadoEscogido.addItem("Dado Normal");
      dadoEscogido.addItem("Dado Octaedro Magico");
      dados.add(dadoTipo);
      dados.add(dadoEscogido);
      
      inicio.add(escoger);
      inicio.add(dados);
      inicio.add(tFichas);
      inicio.add(comodines);
      inicio.add(comenzar);
      inicio.setVisible(true);
      getContentPane().add(inicio);
    }
    
    private void prepareDice(){
        String varDado = dadoEscogido.getSelectedItem().toString();
        juego.setTypeDado(varDado);
        tipoDado.setText("Esta jugando con dado: "+ juego.getTypeDado()); 
    }
    
    private void prepareBoard(){
        juego = new Board(saltarinas);
        juego.addNames(n1);
        juego.addNames(n2);
        juego.colorName();
        board = new JFrame();
        board.setDefaultCloseOperation(EXIT_ON_CLOSE);
        board.setTitle("Parchis");
        board.setVisible(true);
        board.setLayout(new BorderLayout());
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        board.setSize(1280, 750);        
    
        board.setLocationRelativeTo(null);        

        contenedor = new JPanel();
        contenedor.setLayout(new GridLayout(1, 2, 3, 3));
        parchis = new PaintBoard(this);
        //board.add(parchis, BorderLayout.CENTER);
        parchis.repaint();
        
        labels = new JPanel();
        labels.setLayout(new GridLayout(7, 1, 1, 1));
        labels.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), new TitledBorder("Informacion")));
        nombres = new JLabel("Esta jugando: "+ juego.getName());
        nombres.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        labels.add(nombres);
        turno = new JLabel("Es el turno de: "+ juego.getPlayer());
        turno.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        labels.add(turno);
        tipoDado = new JLabel("Esta jugando con el dado: "+ juego.getTypeDado());
        tipoDado.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        labels.add(tipoDado);
        enCarcel = new JLabel("Fichas en la carcel: "+ juego.getEquipo().inPrison().size());
        enCarcel.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        labels.add(enCarcel);
        winners = new JLabel("Fichas coronadas: " + juego.getEquipo().getWinners());
        winners.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        labels.add(winners);
        int[] Dice = juego.getDice();
        valor1 = new JLabel("Valor dado 1: " + Dice[0]);
        valor1.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        valor2 = new JLabel("Valor dado 2: "+ Dice[1]);
        valor2.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
        labels.add(valor1);
        labels.add(valor2);
        //board.add(labels, BorderLayout.EAST);
        
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        buttons.add(play);
        buttons.add(lanzar);
        board.add(buttons, BorderLayout.SOUTH);
        
        contenedor.add(parchis);
        contenedor.add(labels);
        prepareElementosMenu();
        prepareActionsMenu();
        board.add(contenedor, BorderLayout.CENTER);
        menu.add(opc);
        board.setJMenuBar(menu);
    }
    
    private void prepareNames(){
        names = new JFrame();
        names.setDefaultCloseOperation(EXIT_ON_CLOSE);
        names.setSize(PREFERRED_DIMENSION); 
        names.setVisible(true);
        names.setTitle("Nombres de los jugadores");
        
        names.setLocationRelativeTo(null);        
        
        JPanel nombres = new JPanel();
        nombres.setLayout(new GridLayout(3, 1, 3, 3));
        
        JPanel nombre1 = new JPanel();
        nombre1.setLayout(new GridLayout(1, 2, 3, 3));
        JLabel labelN1 = new JLabel("Nombre jugador 1: ");
        nombreF = new JTextField(12);
        nombre1.add(labelN1);
        nombre1.add(nombreF);
        
        JPanel nombre2 = new JPanel();
        nombre2.setLayout(new GridLayout(1, 2, 3, 3));
        JLabel labelN2 = new JLabel("Nombre jugador 2: ");
        nombreS = new JTextField(12);
        nombre2.add(labelN2);
        nombre2.add(nombreS);
                
        nombres.add(nombre1);
        nombres.add(nombre2);
        nombres.add(continuar);
        
        names.getContentPane().add(nombres);
    }
    
    public Board getBoard(){
        return juego;
    }
    
    private void prepareActionsMenu(){
        abrir.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent ev){
              opcionAbrir();
          }
        });

        guardar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                opcionGuardar();
            }
          });
          
        terminar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                opcionSalir();
            }
          });
    }
    
    private void prepareActions(){
        
        comenzar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                saltarinas = Integer.parseInt(tipoFicha.getSelectedItem().toString());
                prepareNames();
                setVisible(false);
                dispose();
            }
        });
        
        continuar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                n1 = nombreF.getText().toString();
                n2 = nombreS.getText().toString();
                prepareBoard();
                prepareDice();
                names.setVisible(false);
                names.dispose();
            }
        });
        
        play.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                buttonPlay();
                lanzar.setEnabled(true);
                play.setEnabled(false);
            }
        });
        
        dado1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                numFicha = Integer.parseInt(opFicha.getSelectedItem().toString());
                String ganador = juego.getPlayer();
                try{
                    juego.choose(numFicha);
                    juego.fichaDado();
                    juego.playFicha1(numFicha);
                    dado1.setEnabled(false);
                } catch (ParchisException e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    Log.record(e);
                    if (e.getMessage().equals(ParchisException.IN_PRISON)){
                        dado1.setEnabled(true);
                    } else if (e.getMessage().equals(ParchisException.CANT_MOVE)){
                        dado1.setEnabled(true);
                    } else if (e.getMessage().equals(ParchisException.IS_WINNER)){
                        JOptionPane.showMessageDialog(null, "El ganador es " + ganador + "!");
                    }
                }
                parchis.removeAll();
                parchis.repaint();
            }
        });
        
        dado2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                numFicha = Integer.parseInt(opFicha.getSelectedItem().toString());
                String ganador = juego.getPlayer();
                try{
                    juego.choose(numFicha);
                    juego.fichaDado();
                    juego.playFicha2(numFicha);
                    dado2.setEnabled(false);
                } catch (ParchisException e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    Log.record(e);
                    if (e.getMessage().equals(ParchisException.IN_PRISON)){
                        dado2.setEnabled(true);
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    } else if (e.getMessage().equals(ParchisException.CANT_MOVE)){
                        dado2.setEnabled(true);
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    } else if (e.getMessage().equals(ParchisException.IS_WINNER)){
                        JOptionPane.showMessageDialog(null, "El ganador es " + ganador + "!");
                    }
                }
                parchis.removeAll();
                parchis.repaint();
            }
        });
        
        cerrar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                dado1.setEnabled(true);
                dado2.setEnabled(true);
                seleccion.setVisible(false);
                seleccion.dispose();
                turno.setText("Es el turno de: "+ juego.getPlayer());
                enCarcel.setText("Fichas en la carcel: "+ juego.getEquipo().inPrison().size());
                winners.setText("Fichas coronadas: " + juego.getEquipo().getWinners());
                nombres.setText("Esta jugando: "+ juego.getName());
                parchis.repaint();
            }
        });
        
        lanzar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                buttonLanzar();
            }
        });
        
        finTurno.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                juego.turn();
            }
        });
    }
    
    private void opcionAbrir(){
        filechoose = new JFileChooser();
        filechoose.setVisible(true);
        int confirm = filechoose.showOpenDialog(this);
        try{
            if (confirm == filechoose.APPROVE_OPTION){
                Board juegoAbre = juego.abra(filechoose.getSelectedFile());
                juego = juegoAbre;
                parchis.repaint();
            }
        } catch(ParchisException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            Log.record(e);
        }
    }

    private void opcionGuardar(){
        filechoose = new JFileChooser();
        filechoose.setVisible(true);
        int confirm = filechoose.showSaveDialog(this);
        try{
            if (confirm == filechoose.APPROVE_OPTION){
                juego.guarde(filechoose.getSelectedFile());
            }
        } catch(ParchisException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            Log.record(e);
        }
    }
    
    private void opcionSalir(){
        if (JOptionPane.showConfirmDialog(rootPane, "Desea salir de la aplicacion", 
                "Confirmacion", JOptionPane.ERROR_MESSAGE) == JOptionPane.ERROR_MESSAGE){
            System.exit(0);
        }
    }
    
    private void buttonLanzar(){
        juego.throwDice();
        Dice = juego.getDice();
        try{
            juego.Dice();
            play.setEnabled(true);
            lanzar.setEnabled(false); 
        } catch (ParchisException e){
            Log.record(e);
            if (e.getMessage().equals(ParchisException.ALL_IN_HOME)){
                valor1.setText("Valor dado 1: "+ Dice[0]);   
                valor2.setText("Valor dado 2: "+ Dice[1]); 
                JOptionPane.showMessageDialog(null, e);
                juego.turn();
                play.setEnabled(false);
                lanzar.setEnabled(true);
                turno.setText("Es el turno de: "+ juego.getPlayer());
                enCarcel.setText("Fichas en la carcel: "+ juego.getEquipo().inPrison().size());
                winners.setText("Fichas coronadas: " + juego.getEquipo().getWinners());
                nombres.setText("Esta jugando: "+ juego.getName());
            } else if (e.getMessage().equals(ParchisException.FIVES_NO_MOVE)){
                valor1.setText("Valor dado 1: "+ Dice[0]);   
                valor2.setText("Valor dado 2: "+ Dice[1]);
                juego.turn();
                turno.setText("Es el turno de: "+ juego.getPlayer());
                enCarcel.setText("Fichas en la carcel: "+ juego.getEquipo().inPrison().size());
                winners.setText("Fichas coronadas: " + juego.getEquipo().getWinners());
                nombres.setText("Esta jugando: "+ juego.getName());
                JOptionPane.showMessageDialog(null, e);
            }
        }
        valor1.setText("Valor dado 1: "+ Dice[0]);   
        valor2.setText("Valor dado 2: "+ Dice[1]); 
        turno.setText("Es el turno de: "+ juego.getPlayer());
        enCarcel.setText("Fichas en la carcel: "+ juego.getEquipo().inPrison().size());
        winners.setText("Fichas coronadas: " + juego.getEquipo().getWinners());
        nombres.setText("Esta jugando: "+ juego.getName());
        parchis.repaint();
    }
    
    private void buttonPlay(){
        seleccion = new JFrame();
        parchis.repaint();
        int[] Dice = juego.getDice();
        seleccion.setTitle("Seleccion de opciones");
        seleccion.setSize(400, 400);
        seleccion.setLocationRelativeTo(null);
        seleccion.setVisible(true);
        opciones = new JPanel();
        opciones.setLayout(new GridLayout(6, 1, 3, 3));
        dice1 = new JLabel("Valor dado 1: "+ Dice[0]);
        dice2 = new JLabel("Valor dado 2: "+ Dice[1]);
        opciones.add(dice1);
        opciones.add(dice2);
        opFicha = new JComboBox();
        opFicha.addItem("1");
        opFicha.addItem("2");
        opFicha.addItem("3");
        opFicha.addItem("4");
        fichas = new JLabel("Seleccione la ficha con la que quiere jugar:");
        opciones.add(fichas);
        opciones.add(opFicha);
        opciones.add(dado1);
        opciones.add(dado2);
        opciones.add(cerrar);
        opciones.add(finTurno);
        seleccion.getContentPane().add(opciones);
    }

    public static void main(String args[]){
        ParchisGUI gui=new ParchisGUI();
        gui.setVisible(true);
    }
}

class PaintBoard extends JPanel{
    public static final Color LIGHT_BLUE = new Color(23, 145, 253);
    public static final Color VERY_DARK_BLUE = new Color(0, 0, 153);
    public static final Color DARK_GREEN = new Color(0, 102, 0);
    public static final Color VERY_GREEN = new Color(0, 153, 0);
    private ParchisGUI gui;
    public static final Color BROWN = new Color(108, 79, 58);
    
    public PaintBoard(ParchisGUI gui){
        this.gui = gui;
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(610, 680);
        setBorder(new CompoundBorder(new EmptyBorder(0,0,0,0), new TitledBorder("Juego")));
    }
    @Override
    public void paintComponent(Graphics g){
        Board parchis = gui.getBoard();
        ArrayList<Integer> horizon = parchis.getHorizon();
        super.paintComponent(g);
        g.setColor(BROWN);
        g.fillRoundRect(10, 35, 590, 595, 10, 10);
        for (int f = 1; f <= parchis.getCasillas().size(); f++){
            g.setColor(parchis.getCasilla(f).getColor());
            if (horizon.contains(f)){
                ArrayList<Ficha> fichas = parchis.getCasilla(f).getFicha();
                int valor = 0;
                if (fichas.size() > 0){
                    g.fillRect(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY(), 23, 60);
                    for (Ficha p:fichas){
                        if (p.getColor().equals(Color.blue)){
                            if (p.getSalta()){
                                g.setColor(LIGHT_BLUE);
                            } else{
                                g.setColor(VERY_DARK_BLUE);
                            }
                        } else{
                            if (p.getSalta()){
                                g.setColor(VERY_GREEN);
                            } else{
                                g.setColor(DARK_GREEN);
                            }
                        }
                        g.fillOval(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY() + valor, 20, 20);
                        valor+= 30;
                    }
                } else{
                    if (parchis.getCasilla(f).getComodin() != null){
                        g.setColor(Color.magenta);
                        g.fillRect(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY(), 23, 60);
                    } else{
                        g.setColor(parchis.getCasilla(f).getColor());
                        g.fillRect(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY(), 23, 60);
                    }
                }
            } else{
                ArrayList<Ficha> fichas = parchis.getCasilla(f).getFicha();
                int valor = 0;
                if (fichas.size() > 0){
                    g.fillRect(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY(), 60, 23);
                    for (Ficha p:fichas){
                        if (p.getColor().equals(Color.blue)){
                            if (p.getSalta()){
                                g.setColor(LIGHT_BLUE);
                            } else{
                                g.setColor(VERY_DARK_BLUE);
                            }
                        } else{
                            if (p.getSalta()){
                                g.setColor(VERY_GREEN);
                            } else{
                                g.setColor(DARK_GREEN);
                            }
                        }
                        g.fillOval(parchis.getCasilla(f).getX()+ valor, parchis.getCasilla(f).getY(), 20, 20);
                        valor+= 30;
                    }
                } else{
                    if (parchis.getCasilla(f).getComodin() != null){
                        g.setColor(Color.magenta);
                        g.fillRect(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY(), 60, 23);
                    } else{
                        g.setColor(parchis.getCasilla(f).getColor());
                        g.fillRect(parchis.getCasilla(f).getX(), parchis.getCasilla(f).getY(), 60, 23);
                    }                
                }
            }
        }
        ArrayList<Seguro> verde = parchis.getEscalera(Color.green);
        ArrayList<Seguro> azul = parchis.getEscalera(Color.blue);
        for (Seguro e: verde){
            ArrayList<Ficha> fichas = e.getFicha();
            if (fichas.size() > 0){
                g.setColor(e.getColor());
                g.fillRect(e.getX(), e.getY(), 60, 23);
                for (Ficha p:fichas){
                    if (p.getColor().equals(Color.green)){
                        if (p.getSalta()){
                            g.setColor(VERY_GREEN);
                        } else{
                            g.setColor(DARK_GREEN);
                        }
                    }
                    g.fillOval(e.getX(), e.getY(), 20, 20);
                }
            } else {
                g.setColor(e.getColor());
                g.fillRect(e.getX(), e.getY(), 60, 23);
            }
        }
        for (Seguro e: azul){
            ArrayList<Ficha> fichas = e.getFicha();
            if (fichas.size() > 0){
                g.setColor(e.getColor());
                g.fillRect(e.getX(), e.getY(), 60, 23);
                for (Ficha p:fichas){
                    if (p.getColor().equals(Color.blue)){
                        if (p.getSalta()){
                            g.setColor(LIGHT_BLUE);
                        } else{
                            g.setColor(VERY_DARK_BLUE);
                        }
                    }
                    g.fillOval(e.getX(), e.getY(), 20, 20);
                }
            } else{
                g.setColor(e.getColor());
                g.fillRect(e.getX(), e.getY(), 60, 23);
            }
        }
        int var = 550;
        for (int i = 0; i < 8; i++){
            g.setColor(Color.yellow);
            g.fillRect(var, 302, 23, 60);
            var -= 25;
        }
        var = 36;
        for (int i = 0; i < 8; i++){
            g.setColor(Color.red);
            g.fillRect(var, 302, 23, 60);
            var += 25;
        }
        ArrayList<Ficha> fichas = parchis.getEquipo(Color.green).getHome().getFicha();
        g.setColor(Color.green);
        g.fillRoundRect(parchis.getEquipo(Color.green).getHome().getX(), parchis.getEquipo(Color.green).getHome().getY(), 200, 200, 10, 10);
        g.setColor(Color.black);
        int var2 = 40;
        if (fichas.size() > 0){
            for (Ficha f: fichas){
                if (f.getColor().equals(Color.green)){
                    if (f.getSalta()){
                        g.setColor(VERY_GREEN);
                    } else{
                        g.setColor(DARK_GREEN);
                    }
                }
                g.fillOval(parchis.getEquipo(Color.green).getHome().getX() + var2, parchis.getEquipo(Color.green).getHome().getY() + 100, 20, 20);
                var2 += 30;
            }
        }
        ArrayList<Ficha> fichas1 = parchis.getEquipo(Color.blue).getHome().getFicha();
        g.setColor(Color.blue);
        g.fillRoundRect(parchis.getEquipo(Color.blue).getHome().getX(), parchis.getEquipo(Color.blue).getHome().getY(), 200, 200, 10, 10);
        g.setColor(Color.black);
        int var1 = 30;
        if (fichas1.size() > 0){
            for (Ficha f: fichas1){
                if (f.getColor().equals(Color.blue)){
                    if (f.getSalta()){
                        g.setColor(LIGHT_BLUE);
                    } else{
                        g.setColor(VERY_DARK_BLUE);
                    }
                }
                g.fillOval(parchis.getEquipo(Color.blue).getHome().getX() + var1, parchis.getEquipo(Color.blue).getHome().getY() + 100, 20, 20);
                var1 += 30;
            }
        }
        g.setColor(Color.red);
        g.fillRoundRect(10, 430, 200, 200, 10, 10);
        g.setColor(Color.yellow);
        g.fillRoundRect(399, 35, 200, 200, 10, 10);
    }
}