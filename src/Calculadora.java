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
import javax.swing.SwingConstants;

public class Calculadora extends JFrame {

    int ancho;
    int alto;
    
    public Calculadora() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        alto = tamanoPantalla.height/2;
        ancho = tamanoPantalla.width/2;

        setSize(ancho, alto);
        setLocation(ancho, alto);
        setMinimumSize(new Dimension(ancho, alto));
        setTitle("Calculadora");

        JPanel principal = new Principal();
        setContentPane(principal);
    }

    private class Principal extends JPanel{

        private String num1;
        private String num2;
        private String operacion;
        private String result;
        private JLabel resultado;

        Principal () {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JPanel pantalla = new JPanel();
            pantalla.setAlignmentX(SwingConstants.RIGHT);
            resultado = new JLabel();
            resultado.setSize(ancho, alto/2);
            resultado.setMinimumSize(new Dimension(ancho, alto/2));
            resultado.setFont(new Font("Serif", Font.BOLD, 22));
            resultado.setHorizontalAlignment(SwingConstants.RIGHT);
            resultado.setVisible(true);
            pantalla.add(resultado);

            BotonesPantalla bp = new BotonesPantalla();
            Teclado tec = new Teclado();

            this.add(pantalla);
            this.add(bp);
            this.addKeyListener(tec);
        }

        private class BotonesPantalla extends JPanel implements ActionListener{

            private BotonesPantalla() {
                num1 = "";
                num2 = "";
                operacion = "";
                result = "";

                setLayout(new GridLayout(5,4));

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

                btn0.addActionListener(this);
                btn1.addActionListener(this);
                btn2.addActionListener(this);
                btn3.addActionListener(this);
                btn4.addActionListener(this);
                btn5.addActionListener(this);
                btn6.addActionListener(this);
                btn7.addActionListener(this);
                btn8.addActionListener(this);
                btn9.addActionListener(this);
                btncoma.addActionListener(this);
                btnsum.addActionListener(this);
                btnres.addActionListener(this);
                btnmult.addActionListener(this);
                btndiv.addActionListener(this);
                btncalc.addActionListener(this);
                btnerase.addActionListener(this);
                btnclear.addActionListener(this);

                this.add(new JLabel());
                this.add(new JLabel());
                this.add(btnerase);
                this.add(btnclear);
                this.add(btn7);
                this.add(btn8);
                this.add(btn9);
                this.add(btnsum);
                this.add(btn4);
                this.add(btn5);
                this.add(btn6);
                this.add(btnres);
                this.add(btn1);
                this.add(btn2);
                this.add(btn3);
                this.add(btnmult);
                this.add(btn0);
                this.add(btncoma);
                this.add(btncalc);
                this.add(btndiv);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                
                switch (command) {
                    case "0","1","2","3","4","5","6","7","8","9":
                        if (operacion.isBlank()) {
                            num1 += command;
                            resultado.setText(num1);
                        } else {
                            num2 += command;
                            resultado.setText(num2);
                        }
                        break;
                    case ",":
                        if (operacion == "") {
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
                        break;
                    case "+", "-", "*", "/":
                        if (operacion.isBlank()) {
                            if (num1.isBlank()) {
                                num1 = result;
                            }
                            operacion = command;
                        } else {
                            calcular();
                        }
                        break;
                    case "=":
                        if (!operacion.isBlank()) {
                            calcular();
                        } else {
                            result = num1;
                        }
                        break;
                    case "C":
                        num1 = num2 = operacion = result = "";
                        resultado.setText(num1);
                        break;
                    case "<-":
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
                        break;
                }
            }

            private void calcular() {
                double res;

                if (num1.equals(""))
                    num1 = "0";
                if (num2.equals(""))
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
