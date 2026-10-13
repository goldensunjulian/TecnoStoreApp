package vista.MostrarInformacion;

import java.util.List;
import modelo.entidades.Celular;

public class MostrarCelular {

    public void lista(List<Celular> celulares) {
        for (Celular c : celulares) {
            System.out.println(c);
        }
    }
}
