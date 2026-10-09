package modelo.validaciones;
import java.util.Scanner;

public class validarTexto implements validacionInterface<String>{
    
    @Override
    public String validacion(String mensaje) {
        String texto = "";
        Scanner x = new Scanner(System.in);
        
        do {
            System.out.println(mensaje);
            texto = x.nextLine().trim(); 
            
            if (texto.isEmpty()) {
                System.out.println("Error, el campo no puede estar vacío.");
            }
            
        } while (texto.isEmpty());
        
        return texto;
    
    }
    
}
