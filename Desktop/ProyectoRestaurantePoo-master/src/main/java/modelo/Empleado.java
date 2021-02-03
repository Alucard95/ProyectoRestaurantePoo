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

public class Empleado implements Serializable,Comparable<Empleado>{
    
    //Atributos de Clase
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasenia;
    private String email;
    private TipoEmpleado tipo;

    //Constructor
    public Empleado(String nombre, String apellido, String usuario, String contrasenia,String email,TipoEmpleado tipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
            
    public TipoEmpleado getTipoEmpleado() {
        return tipo;
    }

    public void setTipoEmpleado(TipoEmpleado tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj.getClass()!=this.getClass())
            return false;
        Empleado other = (Empleado)obj;
        return this.usuario==other.usuario;
    }
    
    public static void serializarEmpleado(ArrayList<Empleado> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
    
    //Metodos de Clase
    public static ArrayList<Empleado> desserializarEmpleado(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<Empleado>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarEmpleado(Empleado empleado) throws Exception{
        ArrayList<Empleado> lista= Empleado.desserializarEmpleado("Empleado.ser");
        lista.add(empleado);
        Empleado.serializarEmpleado(lista, "Empleado.ser");
        Helper.mostrarMensaje(new Alert(Alert.AlertType.INFORMATION),"Creación de cuenta",null,"Se ha registrado correctamente el empleado!");
    }

    @Override
    public int compareTo(Empleado o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

