package controlador;

import javafx.beans.property.SimpleStringProperty;

public class TablaReporte 
{
    private SimpleStringProperty fecha;
    private SimpleStringProperty mesa;
    private SimpleStringProperty mesero;
    private SimpleStringProperty numCuenta;
    private SimpleStringProperty cliente;
    private SimpleStringProperty total;
    
    //Constructor
    public TablaReporte(String fecha,String mesa, String mesero, String numCuenta, String cliente, String total) {
        this.fecha     = new SimpleStringProperty(fecha);
        this.mesa      = new SimpleStringProperty(mesa);
        this.mesero    = new SimpleStringProperty(mesero);
        this.numCuenta = new SimpleStringProperty(numCuenta);
        this.cliente   = new SimpleStringProperty(cliente);
        this.total     = new SimpleStringProperty(total);
    }        

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(SimpleStringProperty fecha) {
        this.fecha = fecha;
    }

    public String getMesa() {
        return mesa.get();
    }

    public void setMesa(SimpleStringProperty mesa) {
        this.mesa = mesa;
    }
    
    public String getMesero() {
        return mesero.get();
    }

    public void setMesero(SimpleStringProperty mesero) {
        this.mesero = mesero;
    }

    public String getNumCuenta() {
        return numCuenta.get();
    }

    public void setNumCuenta(SimpleStringProperty numCuenta) {
        this.numCuenta = numCuenta;
    }                       

    
    public String getCliente() {
        return cliente.get();
    }

    public void setCliente(SimpleStringProperty cliente) {
        this.cliente = cliente;
    }
    
    public String getTotal() {
        return total.get();
    }

    public void setTotal(SimpleStringProperty total) {
        this.total = total;
    }
    
}
