package controlador;

import dao.database.AuditoriaDao;
import dao.database.CelularDao;
import dao.database.GamaDao;
import dao.database.MarcaDao;
import dao.database.SistemaOperativoDao;
import modelo.entidades.Auditoria;
import modelo.entidades.Celular;
import modelo.entidades.Empleado;
import modelo.entidades.Gama;
import modelo.entidades.Marca;
import modelo.entidades.SistemaOperativo;
import vista.Ingreso;
import vista.MostrarCelular;

public class GestionBodega {

    private final Ingreso ingreso             = new Ingreso();
    private final MostrarCelular mostrarCel   = new MostrarCelular();
    private final CelularDao celularDao       = new CelularDao();
    private final MarcaDao marcaDao           = new MarcaDao();
    private final GamaDao gamaDao             = new GamaDao();
    private final SistemaOperativoDao soDao   = new SistemaOperativoDao();
    private final AuditoriaDao auditoriaDao   = new AuditoriaDao();

    private final Empleado empleado;

    public GestionBodega(Empleado empleado) {
        this.empleado = empleado;
    }

    public void menu() {
        int op;
        do {
            op = ingreso.menuBodega();
            switch (op) {
                case 1 -> menuCelular();
                case 2 -> menuMarca();
                case 3 -> menuGama();
                case 4 -> menuSO();
                case 5 -> System.out.println("Cerrando sesion...");
            }
        } while (op != 5);
    }

    // --- celular ---

    private void menuCelular() {
        int op;
        do {
            op = ingreso.menuCrud();
            switch (op) {
                case 1 -> agregarCelular();
                case 2 -> mostrarCel.lista(celularDao.listarTodo());
                case 3 -> actualizarCelular();
                case 4 -> eliminarCelular();
                case 5 -> System.out.println("Volviendo...");
            }
        } while (op != 5);
    }

    private void agregarCelular() {
        mostrarCel.lista(celularDao.listarTodo());
        // escoger marca
        System.out.println("--- Marcas ---");
        marcaDao.listarTodo().forEach(System.out::println);
        Marca marca = marcaDao.buscarPorId(ingreso.pedirId());
        if (marca == null) { System.out.println("Marca no encontrada."); return; }

        // escoger SO
        System.out.println("--- Sistemas Operativos ---");
        soDao.listarTodo().forEach(System.out::println);
        SistemaOperativo so = soDao.buscarPorId(ingreso.pedirId());
        if (so == null) { System.out.println("Sistema operativo no encontrado."); return; }

        // escoger gama
        System.out.println("--- Gamas ---");
        gamaDao.listarTodo().forEach(System.out::println);
        Gama gama = gamaDao.buscarPorId(ingreso.pedirId());
        if (gama == null) { System.out.println("Gama no encontrada."); return; }

        Celular celular = ingreso.ingresarCelular(marca, so, gama);
        celularDao.agregar(celular);
        auditoriaDao.agregar(new Auditoria("Bodega agrego celular: " + celular.getModelo(), empleado));
        System.out.println("Celular agregado.");
    }

    private void actualizarCelular() {
        mostrarCel.lista(celularDao.listarTodo());
        Celular celular = celularDao.buscarPorId(ingreso.pedirIdCelular());
        if (celular == null) { System.out.println("Celular no encontrado."); return; }

        System.out.println("--- Marcas ---");
        marcaDao.listarTodo().forEach(System.out::println);
        Marca marca = marcaDao.buscarPorId(ingreso.pedirId());

        System.out.println("--- Sistemas Operativos ---");
        soDao.listarTodo().forEach(System.out::println);
        SistemaOperativo so = soDao.buscarPorId(ingreso.pedirId());

        System.out.println("--- Gamas ---");
        gamaDao.listarTodo().forEach(System.out::println);
        Gama gama = gamaDao.buscarPorId(ingreso.pedirId());

        Celular actualizado = ingreso.ingresarCelular(marca, so, gama);
        actualizado.setId(celular.getId());
        celularDao.actualizar(actualizado);
        auditoriaDao.agregar(new Auditoria("Bodega actualizo celular id: " + celular.getId(), empleado));
        System.out.println("Celular actualizado.");
    }

    private void eliminarCelular() {
        mostrarCel.lista(celularDao.listarTodo());
        Long id = ingreso.pedirIdCelular();
        celularDao.eliminar(id);
        auditoriaDao.agregar(new Auditoria("Bodega elimino celular id: " + id, empleado));
        System.out.println("Celular eliminado.");
    }

    // --- marca ---

    private void menuMarca() {
        int op;
        do {
            op = ingreso.menuCrud();
            switch (op) {
                case 1 -> {
                    Marca m = ingreso.ingresarMarca();
                    if (marcaDao.buscarPorNombre(m.getNombre()) != null) {
                        System.out.println("Esta marca ya existe.");
                    } else {
                        marcaDao.agregar(m);
                        auditoriaDao.agregar(new Auditoria("Bodega agrego marca: " + m.getNombre(), empleado));
                        System.out.println("Marca agregada.");
                    }
                }
                case 2 -> marcaDao.listarTodo().forEach(System.out::println);
                case 3 -> {
                    marcaDao.listarTodo().forEach(System.out::println);
                    Marca m = marcaDao.buscarPorId(ingreso.pedirId());
                    if (m == null) { System.out.println("No encontrada."); break; }
                    m.setNombre(ingreso.ingresarMarca().getNombre());
                    marcaDao.actualizar(m);
                    auditoriaDao.agregar(new Auditoria("Bodega actualizo marca id: " + m.getId(), empleado));
                    System.out.println("Marca actualizada.");
                }
                case 4 -> {
                    marcaDao.listarTodo().forEach(System.out::println);
                    Long id = ingreso.pedirId();
                    marcaDao.eliminar(id);
                    auditoriaDao.agregar(new Auditoria("Bodega elimino marca id: " + id, empleado));
                    System.out.println("Marca eliminada.");
                }
                case 5 -> System.out.println("Volviendo...");
            }
        } while (op != 5);
    }

    // --- gama ---

    private void menuGama() {
        int op;
        do {
            op = ingreso.menuCrud();
            switch (op) {
                case 1 -> {
                    Gama g = ingreso.ingresarGama();
                    if (gamaDao.buscarPorNombre(g.getNombre()) != null) {
                        System.out.println("Esta gama ya existe.");
                    } else {
                        gamaDao.agregar(g);
                        auditoriaDao.agregar(new Auditoria("Bodega agrego gama: " + g.getNombre(), empleado));
                        System.out.println("Gama agregada.");
                    }
                }
                case 2 -> gamaDao.listarTodo().forEach(System.out::println);
                case 3 -> {
                    gamaDao.listarTodo().forEach(System.out::println);
                    Gama g = gamaDao.buscarPorId(ingreso.pedirId());
                    if (g == null) { System.out.println("No encontrada."); break; }
                    g.setNombre(ingreso.ingresarGama().getNombre());
                    gamaDao.actualizar(g);
                    auditoriaDao.agregar(new Auditoria("Bodega actualizo gama id: " + g.getId(), empleado));
                    System.out.println("Gama actualizada.");
                }
                case 4 -> {
                    gamaDao.listarTodo().forEach(System.out::println);
                    Long id = ingreso.pedirId();
                    gamaDao.eliminar(id);
                    auditoriaDao.agregar(new Auditoria("Bodega elimino gama id: " + id, empleado));
                    System.out.println("Gama eliminada.");
                }
                case 5 -> System.out.println("Volviendo...");
            }
        } while (op != 5);
    }

    // --- sistema operativo ---

    private void menuSO() {
        int op;
        do {
            op = ingreso.menuCrud();
            switch (op) {
                case 1 -> {
                    SistemaOperativo so = ingreso.ingresarSO();
                    if (soDao.buscarPorNombre(so.getNombre()) != null) {
                        System.out.println("Este sistema operativo ya existe.");
                    } else {
                        soDao.agregar(so);
                        auditoriaDao.agregar(new Auditoria("Bodega agrego SO: " + so.getNombre(), empleado));
                        System.out.println("Sistema operativo agregado.");
                    }
                }
                case 2 -> soDao.listarTodo().forEach(System.out::println);
                case 3 -> {
                    soDao.listarTodo().forEach(System.out::println);
                    SistemaOperativo so = soDao.buscarPorId(ingreso.pedirId());
                    if (so == null) { System.out.println("No encontrado."); break; }
                    so.setNombre(ingreso.ingresarSO().getNombre());
                    soDao.actualizar(so);
                    auditoriaDao.agregar(new Auditoria("Bodega actualizo SO id: " + so.getId(), empleado));
                    System.out.println("Sistema operativo actualizado.");
                }
                case 4 -> {
                    soDao.listarTodo().forEach(System.out::println);
                    Long id = ingreso.pedirId();
                    soDao.eliminar(id);
                    auditoriaDao.agregar(new Auditoria("Bodega elimino SO id: " + id, empleado));
                    System.out.println("Sistema operativo eliminado.");
                }
                case 5 -> System.out.println("Volviendo...");
            }
        } while (op != 5);
    }
}
