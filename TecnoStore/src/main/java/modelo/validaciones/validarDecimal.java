package modelo.validaciones;
import java.util.Scanner;
public class validarDecimal implements validacionInterface<Double>{
        @Override
        public Double validacion(String mensaje) {
        Scanner x = new Scanner(System.in);
        Double numero = 0.0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            while (!x.hasNextDouble()) {
                System.out.println("Error, se espera un numero");
                x.next();
            }
            numero = x.nextDouble();
            if (numero <= 0) {
                System.out.println("El numero ingresado no es valido. Se espera un numero mayor a 0");
            } else {
                valido = true;
            }
        } while (valido == false);

        return numero;
    }
}
