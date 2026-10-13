# TecnoStore

Sistema de consola en Java para la gestión de ventas, inventario y clientes de una tienda minorista de teléfonos celulares. Desarrollado con principios de Programación Orientada a Objetos, persistencia con JDBC y MySQL.

---

## Descripción del proyecto

TecnoStore permite automatizar el control de ventas, inventario y clientes que antes se manejaban en hojas de cálculo. El sistema tiene tres roles de usuario con accesos diferenciados:

- **Vendedor**: registra ventas con carrito de compras, calcula el total con IVA del 19% y actualiza el stock automáticamente.
- **Bodega**: gestiona el catálogo de celulares, marcas, gamas y sistemas operativos.
- **Administrador**: gestiona empleados, consulta la auditoría del sistema, visualiza reportes de análisis y genera el archivo de reporte de ventas.

---

## Estructura de clases

```
src/main/java/
│
├── com/mycompany/tecnostore/
│   └── TecnoStore.java              # Clase principal, punto de entrada
│
├── modelo/
│   ├── entidades/
│   │   ├── Persona.java             # Clase base (herencia)
│   │   ├── Cliente.java             # Extiende Persona
│   │   ├── Empleado.java            # Extiende Persona, agrega Rol y credenciales
│   │   ├── Rol.java
│   │   ├── Celular.java             # Composición: Marca, SistemaOperativo, Gama
│   │   ├── Marca.java
│   │   ├── Gama.java
│   │   ├── SistemaOperativo.java
│   │   ├── Venta.java               # Composición: Cliente, Empleado, List<DetalleVenta>
│   │   ├── DetalleVenta.java
│   │   └── Auditoria.java
│   │
│   └── validaciones/
│       ├── validacionInterface.java # Interfaz genérica (patrón Strategy)
│       ├── validarTexto.java
│       ├── validarEntero.java
│       ├── validarDecimal.java
│       ├── validarCorreo.java
│       ├── validarClave.java
│       ├── validarUsuario.java
│       └── validarRango.java
│
├── dao/
│   ├── Conexion.java                # Patrón Singleton
│   ├── interfaceDAO/
│   │   ├── InterfaceAgregar.java
│   │   ├── InterfaceActualizar.java
│   │   ├── InterfaceEliminar.java
│   │   ├── InterfaceBuscarPorId.java
│   │   └── InterfaceListar.java
│   └── database/
│       ├── PersonaDao.java
│       ├── ClienteDao.java
│       ├── EmpleadoDao.java
│       ├── CelularDao.java
│       ├── MarcaDao.java
│       ├── GamaDao.java
│       ├── SistemaOperativoDao.java
│       ├── VentaDao.java
│       ├── DetallesVentaDao.java
│       ├── RolDao.java
│       └── AuditoriaDao.java
│
├── controlador/
│   ├── GestionGeneral.java          # Login y dispatch por rol
│   ├── GestionVendedor.java         # Flujo de ventas con carrito
│   ├── GestionBodega.java           # CRUD de catálogo
│   └── GestionAdmin.java            # Empleados, auditoría y reportes
│
├── vista/
│   └── Ingreso.java                 # Todos los menús y entrada de datos
│
└── util/
    ├── ArchivoUtils.java            # Generación del archivo reporte_ventas.txt
    └── ReporteUtils.java            # Análisis con Stream API
```

---

## Patrones de diseño aplicados

- **Singleton**: `Conexion.java` — una sola instancia de conexión a la base de datos.
- **DAO (Data Access Object)**: toda la capa `dao.database` con interfaces genéricas para separar la lógica de negocio del acceso a datos.
- **Strategy**: `validacionInterface<T>` con múltiples implementaciones intercambiables de validación.

---

## Ejemplo de ejecución

Al iniciar el sistema se solicita usuario y contraseña. Según el rol asignado se muestra el menú correspondiente.

**Menú Vendedor:**
```
1. Nueva venta
2. Salir
```

**Menú Bodega:**
```
1. Celulares
2. Marcas
3. Gamas
4. Sistemas Operativos
5. Salir
```

**Menú Administrador:**
```
1. Empleados
2. Ver auditoria
3. Reportes de analisis
4. Generar reporte de ventas
5. Salir
```

El flujo de una venta:
1. El vendedor ingresa la identificación del cliente (lo registra si no existe).
2. Selecciona celulares del catálogo y los agrega al carrito.
3. El sistema calcula el total con IVA del 19%.
4. Al confirmar, se registra la venta en la BD y se actualiza el stock.

---

## Conexión MySQL

### Requisitos
- MySQL 8.x corriendo en `localhost:3306`
- Java 17
- Maven

### Configuración de la base de datos

1. Abrir MySQL Workbench o cualquier cliente MySQL.
2. Ejecutar el script `database.sql` para crear el esquema y las tablas.
3. Ejecutar `inserts_test.sql` para cargar los datos de prueba (roles, empleados, celulares).

### Credenciales de conexión

El archivo `src/main/java/dao/Conexion.java` contiene la cadena de conexión:

```java
DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/tecnostore", "root", "Tristan2105"
);
```

Cambiar `"root"` y `"Tristan2105"` por el usuario y contraseña de MySQL local si es diferente.


---

## Entregables

- `src/` — código fuente completo en Java
- `database.sql` — script de creación de tablas
- `inserts_test.sql` — datos de prueba
- `reporte_ventas.txt` — generado automáticamente al usar la opción "Generar reporte de ventas" desde el menú Admin
