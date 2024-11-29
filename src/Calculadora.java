import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Calculadora extends JFrame {

    private int ancho;
    private int alto;
    private int mode = MODE.FREE_MODE; //El modo de input (0 = mouse only, 1 = keyboard only, 2 = free mode)

    public void setMode(int mode) {
        this.mode = mode;
    }

    // -TODOLIST-
    //TODO Mantener proporciones al maximizar la pantalla.
    //TODO Implementar cambios de modo.
    //TODO Borrar todos los datos al minimizar la ventana.
    
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
    }

    private class Principal extends JPanel {

        private String num1;
        private String num2;
        private String operacion;
        private String result;
        private JLabel resultado;
        private JLabel introduciendo;

        public Principal() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.DARK_GRAY);

            JPanel modeLine = new JPanel();
            JLabel actualMode = new JLabel();
            modeLine.setBackground(Color.DARK_GRAY);
            actualMode.setFont(new Font("Serif", Font.ITALIC, 12));
            actualMode.setText("Modo actual: " + MODE.stringValue(mode));
            actualMode.setForeground(Color.WHITE);
            modeLine.add(actualMode);

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

                //TODO NewButton btnX = new NewButton(); - Dentro de NewButton: addActionListener(this) y el metodo acrionPerformed

                JButton btn0 = new JButton("0");
                JButton btn1 = new JButton("1");
                JButton btn2 = new JButton("2");
                JButton btn3 = new JButton("3");
                JButton btn4 = new JButton("4");
                JButton btn5 = new JButton("5");
                JButton btn6 = new JButton("6");
                JButton btn7 = new JButton("7");
                JButton btn8 = new JButton("8");
                JButton btn9 = new JButton("9");
                
                JButton btncoma = new JButton(",");
                JButton btnsum = new JButton("+");
                JButton btnres = new JButton("-");
                JButton btnmult = new JButton("*");
                JButton btndiv = new JButton("/");
                JButton btncalc = new JButton("=");
                JButton btnerase = new JButton("<-");
                JButton btnclear = new JButton("C");

                btn0.addActionListener(extracted());
                btn1.addActionListener(extracted());
                btn2.addActionListener(extracted());
                btn3.addActionListener(extracted());
                btn4.addActionListener(extracted());
                btn5.addActionListener(extracted());
                btn6.addActionListener(extracted());
                btn7.addActionListener(extracted());
                btn8.addActionListener(extracted());
                btn9.addActionListener(extracted());
                btncoma.addActionListener(extracted());
                btnsum.addActionListener(extracted());
                btnres.addActionListener(extracted());
                btnmult.addActionListener(extracted());
                btndiv.addActionListener(extracted());
                btncalc.addActionListener(extracted());
                btnerase.addActionListener(extracted());
                btnclear.addActionListener(extracted());

                btn0.setBackground(Color.LIGHT_GRAY);
                btn1.setBackground(Color.LIGHT_GRAY);
                btn2.setBackground(Color.LIGHT_GRAY);
                btn3.setBackground(Color.LIGHT_GRAY);
                btn4.setBackground(Color.LIGHT_GRAY);
                btn5.setBackground(Color.LIGHT_GRAY);
                btn6.setBackground(Color.LIGHT_GRAY);
                btn7.setBackground(Color.LIGHT_GRAY);
                btn8.setBackground(Color.LIGHT_GRAY);
                btn9.setBackground(Color.LIGHT_GRAY);
                btncoma.setBackground(Color.LIGHT_GRAY);
                btnsum.setBackground(Color.LIGHT_GRAY);
                btnres.setBackground(Color.LIGHT_GRAY);
                btnmult.setBackground(Color.LIGHT_GRAY);
                btndiv.setBackground(Color.LIGHT_GRAY);
                btncalc.setBackground(Color.LIGHT_GRAY);
                btnerase.setBackground(Color.LIGHT_GRAY);
                btnclear.setBackground(Color.LIGHT_GRAY);

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

            private class NewButton implements ActionListener {
                
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                this.requestFocus();
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
                            case 110: //Coma
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
                        introduciendo.setText(num1);
                    }
                    operacion = command;
                    introduciendo.setText(introduciendo.getText() + " " + command + " ");
                } else {
                    if (num2.isBlank()) {
                        num2 = num1;
                        introduciendo.setText(introduciendo.getText() + num2);
                        calcular();
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
                        result = num1;
                        resultado.setText(result);
                        num1 = "";
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
    
                if ((double)((int)res) == res)
                    result = String.valueOf((int)res);
                else
                    result = String.valueOf(res);
                
                resultado.setText(result);
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