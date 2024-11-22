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

public class Calculadora extends JFrame {

    private final int ancho;
    private final int alto;
    
    public Calculadora() {

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

    private class Principal extends JPanel{

        private String num1;
        private String num2;
        private String operacion;
        private String result;
        private final JLabel resultado;
        private final JLabel introduciendo;

        Principal () {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            resultado = new JLabel();
            resultado.setSize(ancho, alto/2);
            resultado.setMinimumSize(new Dimension(ancho, alto/2));
            resultado.setPreferredSize(new Dimension(ancho, alto/4));
            resultado.setFont(new Font("Serif", Font.BOLD, 22));
            resultado.setVisible(true);

            introduciendo = new JLabel();
            introduciendo.setFont(new Font("Serif", Font.ITALIC, 16));
            introduciendo.setVisible(true);

            BotonesPantalla bp = new BotonesPantalla();
            Teclado tec = new Teclado();

            add(resultado);
            add(introduciendo);
            add(bp);
            addKeyListener(tec);
        }

        private class BotonesPantalla extends JPanel implements ActionListener{

            private BotonesPantalla() {
                num1 = "";
                num2 = "";
                operacion = "";
                result = "";

                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

                JPanel numericos = new JPanel();
                JPanel operadores = new JPanel();

                numericos.setLayout(new GridLayout(4,3));
                operadores.setLayout(new GridLayout(4,2));


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

                add(numericos);
                add(operadores);
            }

            private Calculadora.Principal.BotonesPantalla extracted() {
                return this;
            }

            //TODO Cambiar para mostrar resultado e introduciendo.
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                
                switch (command) {
                    case "0","1","2","3","4","5","6","7","8","9" -> {
                        if (operacion.isBlank()) {
                            num1 += command;
                            resultado.setText(num1);
                        } else {
                            num2 += command;
                            resultado.setText(num2);
                        }
                    }
                    case "," -> {
                        
                        if (operacion.isBlank()) {
                            if (num1.isBlank()) {
                                num1 = "0.";
                            } else {
                                num1 += ".";
                            }
                            resultado.setText(num1);
                        } else {
                            if (num2.isBlank()) {
                                num2 = "0.";
                            } else {
                                num2 += ".";
                            }
                            resultado.setText(num2);
                        }
                    }
                    case "+", "-", "*", "/" -> {
                        if (operacion.isBlank()) {
                            if (num1.isBlank()) {
                                num1 = result;
                            }
                            operacion = command;
                        } else {
                            calcular();
                        }
                    }
                    case "=" -> {
                        if (!operacion.isBlank()) {
                            calcular();
                        } else {
                            if (!num1.isBlank()) {
                                result = num1;
                                resultado.setText(result);
                                num1 = "";
                            }
                        }
                    }
                    case "C" -> {
                        num1 = num2 = operacion = result = "";
                        resultado.setText(num1);
                    }
                    case "<-" -> {
                        if (operacion.isBlank()) {
                            if (num1.length() > 0) {
                                num1 = num1.substring(0, num1.length()-1);
                                resultado.setText(num1);
                            }
                        } else {
                            if (num2.length() > 0) {
                                num2 = num2.substring(0, num2.length()-1);
                                resultado.setText(num2);
                            }
                        }
                    }
                }
            }

            private void calcular() {
                double res;

                if (num1.isBlank())
                    num1 = "0";
                if (num2.isBlank())
                    num2 = "0";

                if (operacion.equals("+"))
                    res = Double.parseDouble(num1) + Double.parseDouble(num2);
                else if (operacion.equals("-"))
                    res = Double.parseDouble(num1) - Double.parseDouble(num2);
                else if (operacion.equals ("*"))
                    res = Double.parseDouble(num1) * Double.parseDouble(num2);
                else if (!num2.equals("0"))
                    res = Double.parseDouble(num1) / Double.parseDouble(num2);
                else
                    res = 0;

                if ((double)((int)res) == res)
                    result = Integer.toString((int)res);
                else
                    result = Double.toString(res);
                
                resultado.setText(result);
                System.out.println(num1 + " " + operacion + " " + num2 + " = " + result);

                num1 = num2 = operacion = "";
            }
        }
        private class Teclado extends KeyAdapter {
            
            @Override
            public void keyTyped(KeyEvent e) {
                int letter = e.getKeyChar();
                System.out.print((char)letter);
            }
        }
    }
}
