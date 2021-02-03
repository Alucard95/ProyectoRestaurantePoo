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
import java.util.Iterator;
import javafx.scene.control.Alert;

public class DetallePedido implements Serializable,Comparable<DetallePedido>
{
    //Atributo de Clase
    private CabeceraPedido cabecera;
    private Producto producto;
    private int cantidad;
    private double subtotalLinea;
    private double ivaLinea;
    private double totalLinea;
    
    //Constructor
    public DetallePedido(CabeceraPedido cabecera, Producto producto, int cantidad, double subtotalLinea, double ivaLinea, double totalLinea) {
        this.cabecera = cabecera;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotalLinea = subtotalLinea;
        this.ivaLinea = ivaLinea;
        this.totalLinea = totalLinea;
    }
    
    //Getter and Setter
    public CabeceraPedido getCabecera() {
        return cabecera;
    }

    public void setCabecera(CabeceraPedido cabecera) {
        this.cabecera = cabecera;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotalLinea() {
        return subtotalLinea;
    }

    public void setSubtotalLinea(double subtotalLinea) {
        this.subtotalLinea = subtotalLinea;
    }

    public double getIvaLinea() {
        return ivaLinea;
    }

    public void setIvaLinea(double ivaLinea) {
        this.ivaLinea = ivaLinea;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }
    
    public static void serializarDetallePedido(ArrayList<DetallePedido> listap, String ruta){
        try(ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(ruta))){
            a.writeObject(listap);
        }catch(IOException e){}
    }
    
    //Metodos de Clase
        public static ArrayList<DetallePedido> desserializarDetallePedido(String ruta){
        try(ObjectInputStream a = new ObjectInputStream(new FileInputStream(ruta))){
            return (ArrayList<DetallePedido>) a.readObject();
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
        
     public static void guardarDetallePedido(DetallePedido detalle) throws Exception{
        ArrayList<DetallePedido> lista= DetallePedido.desserializarDetallePedido("Detalle.ser");
        lista.add(detalle);
        DetallePedido.serializarDetallePedido(lista, "Detalle.ser");
        Helper.mostrarMensaje(new Alert(Alert.AlertType.INFORMATION),"Creación de Producto",null,"Se ha registrado correctamente el producto!");
    }  
    
    public static void actualizarDetallePedido(DetallePedido detalle) 
    {
        ArrayList<DetallePedido> lista;
        ArrayList<DetallePedido> listaTmp = new ArrayList<>();
        try
        {
            lista = DetallePedido.desserializarDetallePedido("Detalle.ser");            
        }
        catch(Exception e)
        {            
            lista = new ArrayList<>();        
        }
        
        if(lista.isEmpty())
            lista.add(detalle);
        else
        {
            //eliminar todos los detalles 
            for(DetallePedido det : lista)
            {
                if(!det.equals(detalle))
                    listaTmp.add(det);
            }
            listaTmp.add(detalle);
            lista = listaTmp;
        }                
        
        System.out.println("Productos agregados");
        for(DetallePedido det : lista)
            System.out.println("producto=>"+det.producto.getNombre());
        DetallePedido.serializarDetallePedido(lista, "Detalle.ser");        
    }
    
    public static void eliminarDetallePedido(DetallePedido detalle,CabeceraPedido cabecera) throws Exception{        
        Date fecha = cabecera.getFecha();
        int numero = cabecera.getNumeroMesa();
        String mesero = cabecera.getMesero().getUsuario();
        String cliente = cabecera.getCliente().getNombre()+" - "+cabecera.getCliente().getApellido();
        System.out.println("Cabecera numMesa=>"+numero+", "+mesero+", "+cliente);
        ArrayList<DetallePedido> lista= DetallePedido.desserializarDetallePedido("Detalle.ser");        
        System.out.println("Lista de productos existentes");
        for(DetallePedido d : lista)        
            System.out.println("Productos=>"+d.getProducto().getNombre());        
        
        System.out.println("Eliminando el detalle de la lista");
        Iterator itr = lista.iterator(); 
        while (itr.hasNext()) 
        { 
            DetallePedido det = (DetallePedido)itr.next();  
            Date fechaP = det.getCabecera().getFecha();
            int num     = det.getCabecera().getNumeroMesa();
            String mes  = det.getCabecera().getMesero().getUsuario();                        
            if(fechaP.equals(fecha) && num == numero && 
               mesero.equalsIgnoreCase(mes) && 
               det.producto.getNombre().equals(detalle.producto.getNombre()))                                    
                itr.remove(); 
        } 
                        
        System.out.println("Cantidad de productos al final de la eliminacion "+lista.size());  
        DetallePedido.serializarDetallePedido(lista, "Detalle.ser");        
    }

    @Override
    public int compareTo(DetallePedido o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }           
}
