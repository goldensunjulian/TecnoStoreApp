package modelo.validaciones;

import java.util.Scanner;

public class validarClave implements validacionInterface<String> {

    private static final int MIN = 6;
    private static final int MAX = 45; // varchar(45) en la DB

    @Override
    public String validacion(String mensaje) {
        String clave = "";
        Scanner x = new Scanner(System.in);

        do {
            System.out.print(mensaje);
            clave = x.nextLine().trim();

            if (clave.isEmpty()) {
                System.out.println("Error, la contraseña no puede estar vacía.");
            } else if (clave.length() < MIN) {
                System.out.println("Error, la contraseña debe tener mínimo " + MIN + " caracteres.");
                clave = "";
            } else if (clave.length() > MAX) {
                System.out.println("Error, la contraseña no puede superar " + MAX + " caracteres.");
                clave = "";
            }

        } while (clave.isEmpty());

        return clave;
    }
}
