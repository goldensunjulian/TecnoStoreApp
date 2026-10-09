package modelo.validaciones;

import java.util.Scanner;

public class validarEntero implements validacionInterface<Long>{

    @Override
    public Long validacion(String mensaje) {
        Scanner x = new Scanner(System.in);
        long numero = 0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            while (!x.hasNextLong()) {
                System.out.println("Error, se espera un numero entero");
                x.next();
            }
            numero = x.nextLong();
            if (numero <= 0) {
                System.out.println("El numero ingresado no es valido. Se espera un entero mayor a 0");
            } else {
                valido = true;
            }
        } while (valido == false);

        return numero;
    }
    
}
