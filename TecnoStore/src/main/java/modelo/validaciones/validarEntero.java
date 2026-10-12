package modelo.validaciones;

import java.util.Scanner;

public class validarEntero implements validacionInterface<Long>{

    @Override
    public Long validacion(String mensaje) {
        long numero = 0;
        boolean valido = false;

        do {
            System.out.println(mensaje);
            try {
                numero = Long.parseLong(new Scanner(System.in).nextLine().trim());
                if (numero <= 0) {
                    System.out.println("El numero ingresado no es valido. Se espera un entero mayor a 0");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, se espera un numero entero");
            }
        } while (!valido);

        return numero;
    }
}
