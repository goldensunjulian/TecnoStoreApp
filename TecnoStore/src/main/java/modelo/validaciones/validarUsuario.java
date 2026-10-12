package modelo.validaciones;

import java.util.Scanner;

public class validarUsuario implements validacionInterface<String> {

    private static final int MIN = 4;
    private static final int MAX = 45;

    @Override
    public String validacion(String mensaje) {
        String username = "";
        Scanner x = new Scanner(System.in);

        do {
            System.out.print(mensaje);
            username = x.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Error, el usuario no puede estar vacío.");
            } else if (username.contains(" ")) {
                System.out.println("Error, el usuario no puede contener espacios.");
                username = "";
            } else if (username.length() < MIN) {
                System.out.println("Error, el usuario debe tener mínimo " + MIN + " caracteres.");
                username = "";
            } else if (username.length() > MAX) {
                System.out.println("Error, el usuario no puede superar " + MAX + " caracteres.");
                username = "";
            }

        } while (username.isEmpty());

        return username;
    }
}
