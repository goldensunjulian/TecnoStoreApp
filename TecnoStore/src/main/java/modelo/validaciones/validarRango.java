package modelo.validaciones;

import java.util.Scanner;

public class validarRango {

    public int validacion(String mensaje, int min, int max) {
        int opcion = 0;
        boolean valido = false;

        do {
            System.out.print(mensaje);
            try {
                opcion = Integer.parseInt(new Scanner(System.in).nextLine().trim());
                if (opcion >= min && opcion <= max) {
                    valido = true;
                } else {
                    System.out.println("Error, ingrese una opción entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, ingrese un número válido.");
            }

        } while (!valido);

        return opcion;
    }
}
