/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import modelo.Cliente;

/**
 *
 * @author bleac
 */
public class Cita implements Serializable {
    String nombre;
    int nMesa;

    public Cita(String n, int nMesa) {
        this.nombre = n;
        this.nMesa = nMesa;
    }
    
    public static void serializarCita(ArrayList<Cita> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
    
    
    public static ArrayList<Cita> desserializarCita(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<Cita>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarCita(Cita cita) throws Exception{
        ArrayList<Cita> lista= Cita.desserializarCita("Cita.ser");
        lista.add(cita);
        System.out.println("Cita =>"+cita.getNombre()+","+cita.getnMesa());
        Cita.serializarCita(lista, "Cita.ser");
        Helper.mostrarMensaje(new Alert(Alert.AlertType.INFORMATION),"Creación de cita",null,"Se ha registrado correctamente la cita!");
    }

    public String getNombre() {
        return nombre;
    }

    public int getnMesa() {
        return nMesa;
    }
    
    
    
    
    
    
    
           
    
}
