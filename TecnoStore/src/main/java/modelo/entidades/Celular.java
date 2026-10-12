package modelo.entidades;

public class Celular {
    Long id;
    Marca marca;
    String modelo;
    Double precio;
    Long stock;
    SistemaOperativo so;
    Gama gama;

    public Celular(Long id, Marca marca, String modelo, Double precio, Long stock, SistemaOperativo so, Gama gama) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.so = so;
        this.gama = gama;
    }

    public Celular(Marca marca, String modelo, Double precio, Long stock, SistemaOperativo so, Gama gama) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.so = so;
        this.gama = gama;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public SistemaOperativo getSo() {
        return so;
    }

    public void setSo(SistemaOperativo so) {
        this.so = so;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }
    
    
}
