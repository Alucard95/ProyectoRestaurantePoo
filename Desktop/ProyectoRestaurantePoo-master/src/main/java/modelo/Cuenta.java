package modelo;

import controlador.Helper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class Cuenta implements Serializable,Comparable<Cuenta>{
    
    //Atributos de Clase
    private int numero;
    private Mesero mesero;
    private ArrayList<CabeceraPedido> pedidos;
    
    //Constructor
    public Cuenta(int numero, Mesero mesero, ArrayList<CabeceraPedido> pedidos) {
        this.numero = numero;
        this.mesero = mesero;
        this.pedidos = pedidos;
    }
    
    public Cuenta(int numero, Mesero mesero) {
        this.numero = numero;
        this.mesero = mesero;
        this.pedidos = new ArrayList<>();
    }
    
    //Getter and Setter
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public ArrayList<CabeceraPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<CabeceraPedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public int compareTo(Cuenta o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Metodos de Clase
    public static void serializarCuenta(ArrayList<Cuenta> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
        
    public static ArrayList<Cuenta> desserializarCuenta(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<Cuenta>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarCuenta(Cuenta cuenta) throws Exception{
        ArrayList<Cuenta> lista= Cuenta.desserializarCuenta("Cuenta.ser");
        lista.add(cuenta);
        Cuenta.serializarCuenta(lista, "Cuenta.ser");
        Helper.mostrarMensaje(new Alert(Alert.AlertType.INFORMATION),"Creación de Cuenta",null,"Se ha registrado correctamente la cuenta!");
    }   
    
    
}
