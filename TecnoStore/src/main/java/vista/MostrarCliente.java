package vista;

import java.util.List;
import modelo.entidades.Cliente;

public class MostrarCliente {

    public void lista(List<Cliente> clientes) {
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }
}
