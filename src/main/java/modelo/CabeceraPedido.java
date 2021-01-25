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

public class CabeceraPedido implements Serializable,Comparable<CabeceraPedido>
{
    //Atributos de Clase
    private Date fecha;
    private int numeroMesa;
    private Mesero mesero;
    private Cliente cliente;
    private double subtotal;
    private double iva;
    private double total;
    private boolean cerrado;
    
    //Constructor
    public CabeceraPedido() {
    }
    
    public CabeceraPedido(Date fecha, int numeroMesa, Mesero mesero, Cliente cliente, double subtotal, double iva, double total,boolean cerrado) {
        this.fecha = fecha;
        this.numeroMesa = numeroMesa;
        this.mesero = mesero;
        this.cliente = cliente;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.cerrado = cerrado;
    }
    
    //Getter and Setter
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }       
    
    public static void serializarCabeceraPedido(ArrayList<CabeceraPedido> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
    
    //Metodos de Clase
    public static ArrayList<CabeceraPedido> desserializarCabeceraPedido(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<CabeceraPedido>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarCabeceraPedido(CabeceraPedido cabecera) throws Exception{
        ArrayList<CabeceraPedido> lista= CabeceraPedido.desserializarCabeceraPedido("Cabecera.ser");
        lista.add(cabecera);
        System.out.println("Cabecera =>"+cabecera.getNumeroMesa()+","+cabecera.getMesero().getUsuario()+", "+cabecera.getCliente().getNombre());
        CabeceraPedido.serializarCabeceraPedido(lista, "Cabecera.ser");
        Helper.showMessage(new Alert(Alert.AlertType.INFORMATION),"Creación de cuenta",null,"Se ha registrado correctamente el pedido!");
    }
   
    @Override
    public int compareTo(CabeceraPedido o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
