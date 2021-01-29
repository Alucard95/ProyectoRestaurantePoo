package modelo;

import controlador.Helper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.Alert;

public class Mesa implements Serializable,Comparable<Empleado>
{
    //Atributos de clase
    private int numero;
    private int capacidad;
    private double coordenadaX;
    private double coordenadaY;
    private Disponibilidad estadoDisponible;
    private Mesero mesero;
    private String estado;
    
    //Constructor
    public Mesa(int numero, int capacidad, double coordenadaX, double coordenadaY, Disponibilidad estadoDisponible, Mesero mesero, String estado) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.estadoDisponible = estadoDisponible;
        this.mesero = mesero;
        this.estado = estado;
    }
    
    public Mesa(int numero, int capacidad, double coordenadaX, double coordenadaY, Disponibilidad estadoDisponible, String estado) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.estadoDisponible = estadoDisponible;
        this.mesero = new Mesero();
        this.estado = estado;
    }
    
    //Getter and Setter
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public Disponibilidad getEstadoDisponible() {
        return estadoDisponible;
    }

    public void setEstadoDisponible(Disponibilidad estadoDisponible) {
        this.estadoDisponible = estadoDisponible;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    //Metodo de Clae
    public static void serializarMesa(ArrayList<Mesa> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
        
    public static ArrayList<Mesa> desserializarMesa(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<Mesa>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarMesa(Mesa mesa) throws Exception{
        ArrayList<Mesa> lista= Mesa.desserializarMesa("Mesa.ser");
        lista.add(mesa);
        Mesa.serializarMesa(lista, "Mesa.ser");
        Helper.mostrarMensaje(new Alert(Alert.AlertType.INFORMATION),"Creación de Mesa",null,"Se ha registrado correctamente la mesa!");
    } 

    @Override
    public int compareTo(Empleado o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void actualizarMesa(Mesa mesa) 
    {
        ArrayList<Mesa> lista;
        ArrayList<Mesa> listaTmp = new ArrayList<>();
        try
        {
            lista = Mesa.desserializarMesa("Mesa.ser");            
        }
        catch(Exception e)
        {            
            lista = new ArrayList<>();        
        }
        
        if(lista.isEmpty())
            lista.add(mesa);
        else
        {
            //eliminar todos las mesas 
            for(Mesa mesaC : lista)
            {
                if(mesaC.getNumero() != mesa.getNumero())
                    listaTmp.add(mesaC);
            }
            listaTmp.add(mesa);
            lista = listaTmp;
        }                        
        Mesa.serializarMesa(lista, "Mesa.ser");        
    }
    
    public static void eliminarMesa(Mesa mesa) throws Exception{        
        int numeroMesa = mesa.getNumero();
        ArrayList<Mesa> lista= Mesa.desserializarMesa("Mesa.ser");                                        
        Iterator itr = lista.iterator(); 
        while (itr.hasNext()) 
        { 
            Mesa mes = (Mesa)itr.next();  
            int numero  = mes.getNumero();
            if(numero == numeroMesa)                                    
                itr.remove(); 
        }                                 
        Mesa.serializarMesa(lista, "Mesa.ser");        
    }   
}
