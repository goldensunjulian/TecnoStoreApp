package modelo.validaciones;

import java.util.Scanner;

public class validarCorreo implements validacionInterface<String> {

    @Override
    public String validacion(String mensaje) {
        String correo = "";
        Scanner x = new Scanner(System.in);

        do {
            System.out.println(mensaje);
            correo = x.nextLine().trim();

            if (correo.isEmpty()) {
                System.out.println("Error, el correo no puede estar vacío.");
            } else if (!esValido(correo)) {
                System.out.println("Error, formato inválido. Ejemplo: usuario@dominio.com");
                correo = "";
            }

        } while (correo.isEmpty());

        return correo;
    }

    private boolean esValido(String correo) {
        int arroba = correo.indexOf("@");
        String dominio = arroba > 0 ? correo.substring(arroba + 1) : "";
        int punto = dominio.lastIndexOf(".");

        return !correo.contains(" ")
            && arroba > 0
            && arroba < correo.length() - 1
            && punto > 0
            && punto < dominio.length() - 1;
    }
}
