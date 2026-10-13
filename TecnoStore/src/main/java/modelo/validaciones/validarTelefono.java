package modelo.validaciones;

import java.util.Scanner;

public class validarTelefono implements validacionInterface<String> {

    @Override
    public String validacion(String mensaje) {
        String telefono = "";
        boolean valido = false;

        do {
            System.out.print(mensaje);
            telefono = new Scanner(System.in).nextLine().trim();
            try {
                Long.parseLong(telefono);
                if (telefono.length() >= 7 && telefono.length() <= 15) {
                    valido = true;
                } else {
                    System.out.println("Error, el telefono debe tener entre 7 y 15 digitos.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, el telefono solo puede contener numeros.");
            }
        } while (!valido);

        return telefono;
    }
}
