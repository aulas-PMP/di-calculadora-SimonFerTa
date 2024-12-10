import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print(
            "Elige el modo para abrir la calculadora:\n" +
            "1.- Sólo ratón.\n" +
            "2.- Sólo teclado\n" +
            "3.- Modo libre (ambos)\n" +
            "Opción: "
        );
        int opcion = sc.nextInt()-1;
        while (opcion < 0 || opcion > 2) {
            System.out.print("Opción: ");
            opcion = sc.nextInt()-1;
        }
        sc.close();

        Calculadora ccd = new Calculadora(opcion);
        ccd.setVisible(true);
    }
}
