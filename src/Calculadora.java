import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Calculadora extends JFrame implements WindowListener, ComponentListener {

    private int ancho;
    private int alto;
    private int mode = MODE.FREE_MODE; //El modo de input (0 = mouse only, 1 = keyboard only, 2 = free mode)

    private String num1;
    private String num2;
    private String operacion;
    private String result;
    private JLabel resultado;
    private JLabel introduciendo;

    public void setMode(int mode) {
        this.mode = mode;
    }
    
    public Calculadora() {
        this.mode = MODE.FREE_MODE;
        init();
    }

    public Calculadora(int mode) { 
        this.mode = mode;
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        alto = 600;
        ancho = tamanoPantalla.width/2;

        setSize(ancho, alto);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(ancho, alto));
        setTitle("Calculadora - Simón Fernández Tacón");

        JPanel principal = new Principal();
        setContentPane(principal);

        addWindowListener(this);
        addComponentListener(this);
    }

    private class Principal extends JPanel {

        public Principal() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.DARK_GRAY);

            class ModeLine extends JPanel {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setFont(new Font("Serif", Font.ITALIC, 14));
                    setForeground(Color.WHITE);
                    setAlignmentX(SwingConstants.CENTER);
                    g.drawString("Modo actual: " + MODE.stringValue(mode), 0, this.getSize().height);
                }
            }
            JPanel modeLine = new ModeLine();
            modeLine.setBackground(Color.DARK_GRAY);

            resultado = new JLabel();
            resultado.setFont(new Font("Serif", Font.BOLD, 22));
            resultado.setForeground(Color.WHITE);
            resultado.setMinimumSize(new Dimension(ancho, alto/6));
            resultado.setPreferredSize(new Dimension(ancho, alto/6));
            resultado.setVisible(true);

            introduciendo = new JLabel();
            introduciendo.setFont(new Font("Serif", Font.ITALIC, 16));
            introduciendo.setForeground(Color.WHITE);
            introduciendo.setMinimumSize(new Dimension(ancho, alto/8));
            introduciendo.setPreferredSize(new Dimension(ancho, alto/8));
            introduciendo.setVisible(true);

            BotonesPantalla bp = new BotonesPantalla();
            JSeparator sp = new JSeparator(SwingConstants.HORIZONTAL);
            sp.setBackground(Color.WHITE);
            sp.setForeground(Color.WHITE);

            add(modeLine);
            add(resultado);
            add(sp);
            add(introduciendo);
            add(bp);
        }

        private class BotonesPantalla extends JPanel implements ActionListener{

            private BotonesPantalla() {
                num1 = "";
                num2 = "";
                operacion = "";
                result = "";

                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                setBackground(Color.DARK_GRAY);
                addKeyListener(new Teclado());
                setFocusable(true);
                requestFocus();

                JPanel numericos = new JPanel();
                numericos.setPreferredSize(new Dimension(ancho/3*2, alto));
                numericos.setLayout(new GridLayout(4,3));
                numericos.setBackground(Color.DARK_GRAY);

                JPanel operadores = new JPanel();
                operadores.setPreferredSize(new Dimension(ancho/4, alto));
                operadores.setLayout(new GridLayout(4,2));
                operadores.setBackground(Color.DARK_GRAY);

                JButton btn0 = createButton("0");
                JButton btn1 = createButton("1");
                JButton btn2 = createButton("2");
                JButton btn3 = createButton("3");
                JButton btn4 = createButton("4");
                JButton btn5 = createButton("5");
                JButton btn6 = createButton("6");
                JButton btn7 = createButton("7");
                JButton btn8 = createButton("8");
                JButton btn9 = createButton("9");
                
                JButton btncoma = createButton(",");
                JButton btnsum = createButton("+");
                JButton btnres = createButton("-");
                JButton btnmult = createButton("*");
                JButton btndiv = createButton("/");
                JButton btncalc = createButton("=");
                JButton btnerase = createButton("<-");
                JButton btnclear = createButton("C");

                numericos.add(btn7);
                numericos.add(btn8);
                numericos.add(btn9);
                numericos.add(btn4);
                numericos.add(btn5);
                numericos.add(btn6);
                numericos.add(btn1);
                numericos.add(btn2);
                numericos.add(btn3);
                numericos.add(new JLabel());
                numericos.add(btn0);
                numericos.add(new JLabel());

                operadores.add(btnerase);
                operadores.add(btnclear);
                operadores.add(btnsum);
                operadores.add(btnmult);
                operadores.add(btnres);
                operadores.add(btndiv);
                operadores.add(btncoma);
                operadores.add(btncalc);

                numericos.setVisible(true);
                operadores.setVisible(true);

                JPanel separator = new JPanel();
                separator.setPreferredSize(new Dimension(ancho/12, alto));
                separator.setBackground(Color.DARK_GRAY);

                add(numericos);
                add(separator);
                add(operadores);

            }

            private JButton createButton(String command) {

                JButton newButton = new JButton(command);
                newButton.setFocusable(false);
                newButton.addActionListener(this);
                newButton.setBackground(Color.LIGHT_GRAY);
                return newButton;
                
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (mode != 1) {
                    String command = e.getActionCommand();
                    
                    switch (command) {
                        case "0","1","2","3","4","5","6","7","8","9":
                            addNumber(command);
                            break;
                        case ",":
                            addComma();
                            break;
                        case "+", "-", "*", "/":
                            addCommand(command);
                            break;
                        case "=":
                            calculate();
                            break;
                        case "C":
                            clear();
                            break;
                        case "<-":
                            erase();
                            break;
                    }
                }
            }

            private class Teclado extends KeyAdapter {

                @Override
                public void keyPressed(KeyEvent e) {
                    if (mode != 0) {
                        int command = e.getKeyCode();
                        String valor = String.valueOf(e.getKeyChar());

                        switch (command) {
                            case 96,97,98,99,100,101,102,103,104,105: //Numeros
                                addNumber(valor);
                                break;
                            case 46,44,110: //Coma
                                addComma();
                                break;
                            case 106,107,109,111: //Operadores 
                                addCommand(valor);
                                break;
                            case 10: //Igual
                                calculate();
                                break;
                            case 127: //Clear
                                clear();
                                break;
                            case 8: //Borrado
                                erase();
                                break;
                        }
                    }   
                }
            }

            private void addNumber(String num) {
                if (operacion.isBlank()) {
                    num1 += num;
                    introduciendo.setText(num1.replace('.', ','));
                } else {
                    num2 += num;
                    introduciendo.setText(introduciendo.getText() + num);
                }
            }

            private void addComma() {
                if (operacion.isBlank()) {
                    if (num1.isBlank()) {
                        num1 = "0.";
                    } else {
                        num1 += ".";
                    }
                    introduciendo.setText(num1.replace('.', ','));
                } else {
                    if (num2.isBlank()) {
                        num2 = "0.";
                        introduciendo.setText(introduciendo.getText() + num2.replace('.', ','));
                    } else {
                        num2 += ".";
                        introduciendo.setText(introduciendo.getText() + ",");
                    }
                    
                }
            }

            private void addCommand(String command) {
                if (operacion.isBlank()) {
                    if (num1.isBlank()) {
                        num1 = result;
                        introduciendo.setText(num1.replace('.', ','));
                    }
                    operacion = command;
                    introduciendo.setText(introduciendo.getText() + " " + command + " ");
                } else {
                    if (num2.isBlank()) {
                        if (command.equals(operacion)) {
                            num2 = num1;
                            introduciendo.setText(introduciendo.getText() + num2);
                            calcular();
                        } else {
                            operacion = command;
                            introduciendo.setText(introduciendo.getText().substring(0,introduciendo.getText().length()-2) + operacion + " ");
                        }
                    } else {
                        calcular();
                        num1 = result;
                        operacion = command;
                        introduciendo.setText(result + " " + command + " ");
                    }
                }
            }

            private void clear() {
                num1 = num2 = operacion = result = "";
                        resultado.setText(num1);
                        introduciendo.setText(num1);
            }

            private void calculate() {
                if (!operacion.isBlank()) {
                    if (num2.isBlank()) {
                        num2 = num1;
                        introduciendo.setText(introduciendo.getText() + num2);
                    }
                    calcular();
                } else {
                    if (!num1.isBlank()) {
                        try {
                            Double.parseDouble(num1);
                            result = num1;
                            resultado.setText(result);
                            num1 = "";
                        } catch (NumberFormatException e) {
                            resultado.setText("Err.");
                            introduciendo.setText("");
                            num1 = num2 = result = operacion = "";
                        }
                    }
                }
            }

            private void erase() {
                if (operacion.isBlank()) {
                    if (num1.length() > 0) {
                        num1 = num1.substring(0, num1.length()-1);
                        introduciendo.setText(introduciendo.getText().substring(0, introduciendo.getText().length()-1));
                    }
                } else {
                    if (num2.length() > 0) {
                        num2 = num2.substring(0, num2.length()-1);
                        introduciendo.setText(introduciendo.getText().substring(0, introduciendo.getText().length()-1));
                    }
                }
            }

            private void calcular() {
                double res;
    
                if (num1.isBlank())
                    num1 = "0";
                if (num2.isBlank())
                    num2 = "0";
    
                try {
                    if (operacion.equals("+"))
                        res = Double.parseDouble(num1) + Double.parseDouble(num2);
                    else if (operacion.equals("-"))
                        res = Double.parseDouble(num1) - Double.parseDouble(num2);
                    else if (operacion.equals ("*"))
                        res = Double.parseDouble(num1) * Double.parseDouble(num2);
                    else 
                        res = Double.parseDouble(num1) / Double.parseDouble(num2);
                    
                    if (res == Double.POSITIVE_INFINITY || res == Double.NEGATIVE_INFINITY)
                        throw new ArithmeticException();

                    if (res < 0) {
                        resultado.setForeground(Color.RED);
                    } else {
                        resultado.setForeground(Color.WHITE);
                    }
        
                    if ((double)((int)res) == res)
                        result = String.valueOf((int)res);
                    else
                        result = String.valueOf(res);
                    
                    resultado.setText(result.replace(".", ","));
                    System.out.println(num1 + " " + operacion + " " + num2 + " = " + result);
                } catch (ArithmeticException | NumberFormatException e) {
                    resultado.setText("Err.");
                    introduciendo.setText("");
                } finally {
                    num1 = num2 = operacion = "";
                }
            }
        }
    }

    // WindowListener
    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {
        num1 = num2 = operacion = result = "";
        resultado.setText("");
        introduciendo.setText("");
    }

    // ComponentListener
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {
        setLocationRelativeTo(null);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (getExtendedState() != Frame.MAXIMIZED_BOTH) {
            setSize(ancho, alto);
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {}

    
}

class MODE {
    public static final int MOUSE_ONLY = 0;
    public static final int KEYBOARD_ONLY = 1;
    public static final int FREE_MODE = 2;

    public static final String stringValue(int value) {
        switch (value) {
            case 0: return "MOUSE_ONLY";
            case 1: return "KEYBOARD_ONLY";
            case 2: return "FREE_MODE";
            default: return null;
        }
    }
}