package modelo;

import helper.Sistema;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.Alert;

public class Producto implements Serializable,Comparable<Producto>{
    
    //Atributos de clase
    private String codigo;
    private String nombre;
    private String descripcion;
    private String urlImagen;
    private double precio;
    private CategoriaProducto categoria;
    
    //Constructor
    public Producto() {
    }
    
    public Producto(String codigo, 
                    String nombre, 
                    String descripcion,
                    String urlImagen, 
                    double precio,
                    CategoriaProducto categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.precio = precio;
        this.categoria = categoria;
    }
    
    //Getter and Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getURLImagen() {
        return urlImagen;
    }

    public void setURLImagen(String url) {
        this.urlImagen = url;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }        
    
    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj.getClass()!=this.getClass())
            return false;
        Producto other = (Producto)obj;
        return this.codigo==other.codigo;
    }
    
    //Metodos de Clase
    public static void serializarProducto(ArrayList<Producto> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
        
    public static ArrayList<Producto> desserializarProducto(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<Producto>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
    
    public static void guardarProducto(Producto producto) throws Exception{
        ArrayList<Producto> lista= Producto.desserializarProducto("Producto.ser");
        lista.add(producto);
        Producto.serializarProducto(lista, "Producto.ser");
        Sistema.showMessage(new Alert(Alert.AlertType.INFORMATION),"Creación de Producto",null,"Se ha registrado correctamente el producto!");
    }    

    public static void actualizarProducto(Producto producto) 
    {
        ArrayList<Producto> lista;
        ArrayList<Producto> listaTmp = new ArrayList<>();
        try
        {
            lista = Producto.desserializarProducto("Producto.ser");            
        }
        catch(Exception e)
        {            
            lista = new ArrayList<>();        
        }
        
        if(lista.isEmpty())
            lista.add(producto);
        else
        {
            //eliminar todos los detalles 
            for(Producto prod : lista)
            {
                if(!prod.getCodigo().trim().equals(producto.getCodigo().trim()))
                    listaTmp.add(prod);
            }
            listaTmp.add(producto);
            lista = listaTmp;
        }                        
        Producto.serializarProducto(lista, "Producto.ser");        
    }
    
    public static void eliminarProducto(Producto producto) throws Exception{        
        String codigo = producto.getCodigo().trim();
        ArrayList<Producto> lista= Producto.desserializarProducto("Producto.ser");                                        
        Iterator itr = lista.iterator(); 
        while (itr.hasNext()) 
        { 
            Producto prod = (Producto)itr.next();  
            String cod  = prod.getCodigo().trim();
            if(cod.equals(codigo))                                    
                itr.remove(); 
        }                                 
        Producto.serializarProducto(lista, "Producto.ser");        
    }

    @Override
    public int compareTo(Producto o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}