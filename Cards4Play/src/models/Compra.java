package models;

public class Compra {

    private String id;
    private String fecha;
    private Product producto;
    private Client cliente;

    public Compra(String id, String fecha, Product producto, Client cliente) {
        this.id = id;
        this.fecha = fecha;
        this.producto = producto;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Product getProducto() {
        return producto;
    }
    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public Client getCliente() {
        return cliente;
    }
    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }
}
