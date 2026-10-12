package modelo.validaciones;

import java.util.Scanner;

public class validarDecimal implements validacionInterface<Double>{

    @Override
    public Double validacion(String mensaje) {
        Double numero = 0.0;
        boolean valido = false;

        do {
            System.out.println(mensaje);
            try {
                numero = Double.parseDouble(new Scanner(System.in).nextLine().trim());
                if (numero <= 0) {
                    System.out.println("El numero ingresado no es valido. Se espera un numero mayor a 0");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, se espera un numero");
            }
        } while (!valido);

        return numero;
    }
}
