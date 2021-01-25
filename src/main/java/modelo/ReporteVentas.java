package modelo;

import controlador.Helper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.Alert;

public class ReporteVentas implements Serializable,Comparable<ReporteVentas>
{
    //Atributo de Clase
    private Date fechaPedido;
    private int numeroMesa;
    private String mesero;    
    private int numeroCuenta;
    private String cliente;
    private double total;    
    
    //Constructor
    public ReporteVentas(Date fecha, int numCuenta, int numMesa,String mesero,String cliente, double total) {
        this.fechaPedido = fecha;
        this.numeroCuenta = numCuenta;
        this.numeroMesa = numMesa;
        this.mesero = mesero;        
        this.cliente = cliente;
        this.total = total;
    }
    
    public ReporteVentas(int numeroMesa, String mesero, int numeroCuenta, String cliente, double total) {
        this.numeroMesa = numeroMesa;
        this.mesero = mesero;
        this.numeroCuenta = numeroCuenta;
        this.cliente = cliente;
        this.total = total;
    }
    
    //Getter and Setter
    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }   

    @Override
    public int compareTo(ReporteVentas o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Metodos de Clase
    public static void serializarReporte(ArrayList<ReporteVentas> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
        
    public static ArrayList<ReporteVentas> desserializarReporte(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<ReporteVentas>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarReporte(ReporteVentas reporte) throws Exception{
        ArrayList<ReporteVentas> lista= ReporteVentas.desserializarReporte("Reporte.ser");
        lista.add(reporte);
        ReporteVentas.serializarReporte(lista, "Reporte.ser");
        Helper.showMessage(new Alert(Alert.AlertType.INFORMATION),"Creación de Producto",null,"Se ha registrado correctamente el producto!");
    }  
}
