package primitiva;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PrimiSwing19 extends JFrame{
   
    //Logica
    int reintegroUsuario = -1;
    int resultadoSorteo [] = new int [6];
    int resultadoReintegro = -1;


    //Interfaz
    JCheckBox [] casillasPrimi = new JCheckBox[49];
    JRadioButton [] casillasR = new JRadioButton[10];
    JButton botonSorteo, botonReplay;
    JDialog modalResultado;
    JPanel panel, panelCasillas, panelReintegro, panelBotones;
    JLabel nombreCheckbox, nombreReintegro, numerosUsuario, numerosSorteo, numeroAciertos, numeroFallos, reintegroSorteo, reintegroU;
    BufferedImage img;
    ImageIcon icon;
    JLabel confetti;
    String aciertoR, falloR; 
    ButtonGroup grupo = new ButtonGroup();
    final int NUMERO_CASILLAS = 49;
    
    
    
    PrimiSwing19(){
        super("Juego de la Primitiva");
        panel = new JPanel();
        panelCasillas = new JPanel(); 
        panelReintegro = new JPanel();  
        panelBotones = new JPanel();       
        botonSorteo = new JButton("Sorteo");
        botonSorteo.setBounds(300, 30, 150, 40);
        botonReplay = new JButton("Replay");
        botonReplay.setBounds(550, 30, 150, 40);
        botonSorteo.setEnabled(false);
        nombreCheckbox = new JLabel("Números del Sorteo: ");
        nombreReintegro = new JLabel("Reintegro: ");
        numerosUsuario = new JLabel();
        numerosSorteo= new JLabel();
        numeroAciertos = new JLabel();
        reintegroSorteo = new JLabel();
        reintegroU = new JLabel();
        numeroFallos = new JLabel();
        
        nombreCheckbox.setFont( new Font("Serif",Font.BOLD,16));
        nombreReintegro.setFont( new Font("Serif",Font.BOLD,16));
        
        numerosSorteo.setFont( new Font("Serif",Font.PLAIN,22));
        numerosUsuario.setFont( new Font("Serif",Font.PLAIN,22));
        numeroAciertos.setFont( new Font("Serif",Font.BOLD,22));
        numeroFallos.setFont( new Font("Serif",Font.BOLD,22));
        reintegroSorteo.setFont( new Font("Serif",Font.PLAIN,22));
        reintegroU.setFont( new Font("Serif",Font.PLAIN,22));
        numeroFallos.setForeground(Color.RED);
        numeroAciertos.setForeground(Color.decode("#039e65"));
        
        modalResultado = new JDialog(this, "Resultados del Sorteo");
        modalResultado.setSize(500,500);
        
        //Al pulsar el botón sorteo, se realiza el sorteo y aparece el modal con el resultado
        botonSorteo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                modalResultado.setVisible(true);
                sortear();
            }        
        });
        //Al pulsar el botón replay, se desmarcan todas las casillas
        botonReplay.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reestablecerCasillas();
            }       
        });
        
        numerosSorteo.setBounds(50,70,500,30);        
        numerosUsuario.setBounds(50,130,500,30);       
        numeroAciertos.setBounds(100,250,500,30);
        numeroFallos.setBounds(500,250,500,30);       
        reintegroSorteo.setBounds(600,130,500,30);
        reintegroU.setBounds(600,70,500,30);
        
        
        //Creación de las casillas de la Primitiva
        
        //Comprueba que tipo de evento le llega ( 1 es checked y 2 es unchecked
        //y cuando llega a 6 deshabilita el resto de casillas y habilita el botón del sorteo
        for(int i = 0; i < NUMERO_CASILLAS; i++){
            JCheckBox casilla = new JCheckBox(i + 1 + "");
            casilla.setName(i + 1+ "");
            casilla.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange()==1){
                        if(contarCasillasMarcadas() ==6){
                            habilitarCasillas(false);
                            botonSorteo.setEnabled(true);
                        }
                    } else {
                        if(contarCasillasMarcadas() ==5){
                            habilitarCasillas(true);
                            botonSorteo.setEnabled(false);
                        }
                    }
                }
            });
            casillasPrimi[i] = casilla;  
        }       
        
        //Creación de las casillas del reintegro
        for(int i = 0; i < 10; i++){
            JRadioButton casillaR = new JRadioButton(i +"");
            casillaR.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reintegroUsuario = Integer.valueOf(e.getActionCommand());
//                    System.out.println(reintegroUsuario);
                }
        });
            grupo.add(casillaR);
            casillasR[i] = casillaR;
            
        } 
        
        modalResultado.getContentPane().setBackground(Color.LIGHT_GRAY);   
        modalResultado.getContentPane().setLayout(null);
        modalResultado.getContentPane().add(numerosUsuario);
        modalResultado.getContentPane().add(numerosSorteo);
        modalResultado.getContentPane().add(numeroAciertos);
        modalResultado.getContentPane().add(reintegroSorteo);
        modalResultado.getContentPane().add(reintegroU);
        modalResultado.getContentPane().add(numeroFallos);
        
        panel.setBackground(Color.LIGHT_GRAY);
        panelCasillas.setBounds(100, 70,300, 250);       
        panelReintegro.setBounds(500, 70, 400, 50);
        panelBotones.setBounds(0, 400, 1000, 150);
        panelBotones.setBackground(Color.GRAY);
        
        nombreCheckbox.setBounds(110,40,500,20);
        panel.add(nombreCheckbox);       
        nombreReintegro.setBounds(520,40,500,20);
        panel.add(nombreReintegro);
        
        try {
            img = ImageIO.read(new File("./Resources/confetti.png"));
            icon = new ImageIcon(img);
            confetti = new JLabel(icon);
            confetti.setBounds(140, 300, 80,80);
            confetti.setVisible(false);
            modalResultado.add(confetti);
            } catch (IOException ex) {
                Logger.getLogger(PrimiSwing19.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        panelBotones.add(botonSorteo);
        panelBotones.add(botonReplay);
        panelBotones.setLayout(null);
        
        panel.add(panelReintegro); 
        panel.add(panelCasillas);
        panel.add(panelBotones);
        
        poblarCasillas();
        getContentPane().add(panel);
        modalResultado.setSize(1000,500);
        modalResultado.setLocation(400, 150);
        panel.setLayout(null);
        setVisible(true);
        setLocation(300, 150);
        setSize(1000, 550);
                
        WindowListener wl = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); 
            }
        };
        addWindowListener(wl);
     }
    
    
    
    //Logica
    
    //Comprobación de los números
    public int comprobar(int selecciondelUsuario[], int resultadosSorteo[]){
        int aciertos = 0;
        for(int i = 0; i < resultadosSorteo.length;i++){
            if (existeEnArray(selecciondelUsuario,resultadosSorteo[i])){
                aciertos++;
            }
        }   
        return aciertos;   
    }
    
    //Para reestablecer los valores del sorteo
    public void reestablecerModelo(){        
        resultadoSorteo = new int [6];
        resultadoReintegro = -1;   
    }
    
    public void sortear(){
        //Si el usuario no elige un reintegro
        if(reintegroUsuario <0){
            reintegroUsuario = elegirReintegro();
            marcarCasillaReintegro(reintegroUsuario);
        }
        //Si el usuario si elige un reintegro
        resultadoSorteo = elegirNumeros();
        resultadoReintegro = elegirReintegro();  
        pintarResultado();      
    }
    
    //Elección de los números del sorteo
    public int[] elegirNumeros(){
        int [] sorteo = new int [6];
        for(int i = 0; i < 6; i++){
            int numeroAleatorio = (int)(Math.random() *49) + 1;
            if(existeEnArray(sorteo,numeroAleatorio)){
                i--;
//                System.out.println("REPETIDO!!!");
            } else {
                 sorteo[i] = numeroAleatorio;
            }           
        }                      
        return sorteo;
    }
    
    //Elección del reintegro del sorteo
    public int elegirReintegro(){
        int numeroAleatorio = (int)(Math.random() *9);
        return numeroAleatorio;
    }
 
    //Método para comprobar que no se repite ningún número de los elegidos para el sorteo
    public boolean existeEnArray(int [] seleccionados, int aspirante){
        boolean encontrado = false;
        for (int i = 0; i < seleccionados.length; i++){
            if (seleccionados[i] == aspirante){
                encontrado = true;
            }
        }
        return encontrado;  
    }
 
    
    
    //Interfaz
    
    //Pintar las casillas en el panel
    public void poblarCasillas(){
        for(int i =0; i < casillasPrimi.length; i++){
            panelCasillas.add(casillasPrimi[i]);
        }
        for(int i = 0; i < casillasR.length; i++){
            panelReintegro.add(casillasR[i]);
        }
    }
    
    //Método para limpiar las casillas
    public void reestablecerCasillas(){
        for(int i = 0; i < casillasPrimi.length;i++){
            casillasPrimi[i].setSelected(false);
        }
      grupo.clearSelection();
    }
    
    //Método que habilita (o deshabilita) las casillas
    public void habilitarCasillas(boolean habilitada){
         for(int i = 0; i < casillasPrimi.length; i++){
             if(!casillasPrimi[i].isSelected()){
                 casillasPrimi[i].setEnabled(habilitada);
             }
         }
    }
    
    //Método para contar cuantas casillas están marcadas
    public int contarCasillasMarcadas(){
        int total = 0;
        for(int i = 0; i < casillasPrimi.length; i++){
            if(casillasPrimi[i].isSelected()){
                total++;
            } 
        }
        return total;
    }
    
    //Método para saber que casillas ha marcado el usuario
    public int [] leerSeleccionUsuario(){
        int resultado[] = new int[6];
        int index = 0;
        for(int i = 0; i < casillasPrimi.length;i++){
            if(casillasPrimi[i].isSelected()){
                resultado[index] = Integer.valueOf(casillasPrimi[i].getName());
                index++;
            }
        }       
        return resultado;
    }
    
    //Método que marca el reintegro que se elige aleatoriamente si el usuario no elige uno
    public void marcarCasillaReintegro(int index){
        casillasR[index].setSelected(true);
    }
    
    
    //Método para mostrar los resultados al usuario y pintar el icono si es necesario
    public void pintarResultado(){
        if (resultadoReintegro == reintegroUsuario){
            aciertoR = "1";
            falloR = "0";
        } else {
            aciertoR = "0";
            falloR = "1";
        }
        reintegroSorteo.setText("Reintegro: " + resultadoReintegro);
        reintegroU.setText("Reintegro: " + reintegroUsuario);
        numerosUsuario.setText("Números elegidos: " + Arrays.toString(leerSeleccionUsuario()).replace("[", " ").replace("]", "."));
        numeroFallos.setText("Fallos: " + (6 - comprobar(leerSeleccionUsuario(), resultadoSorteo)) + " - " + falloR);
        numerosSorteo.setText("Números del sorteo: " + Arrays.toString( resultadoSorteo).replace("[", " ").replace("]", "."));
        numeroAciertos.setText("Aciertos: " + comprobar(leerSeleccionUsuario(), resultadoSorteo) + " - " + aciertoR);
        if(comprobar(leerSeleccionUsuario(), resultadoSorteo) == 0 && aciertoR == "0"){    
            confetti.setVisible(false);
        } else {
            confetti.setVisible(true);
        }
    }
    
    
}
