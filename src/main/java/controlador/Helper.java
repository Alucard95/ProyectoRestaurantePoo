package controlador;

import com.project.proyectorestaurante.App;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import modelo.CategoriaProducto;
import modelo.Empleado;
import modelo.Producto;
import modelo.ReporteVentas;

public class Helper 
{
    public static void showMessage(Alert show,String titulo, String encabezado, String mensaje){
        show.setHeaderText(encabezado);
        show.setTitle(titulo);
        show.setContentText(mensaje);
        show.showAndWait();
    }
 
    public static boolean verificarExistenciaEmpleado(String usuario, String contrasenia)
    {
        //Este método permite verificar si el usuario a crear es vendedor, comprador o ninguno. Validacion en el main y para el metodo que agrega los tipo de usuario
        ArrayList<Empleado> empleados = Empleado.desserializarEmpleado("Empleado.ser");
        for(Empleado emp: empleados)
        {
            if(emp.getUsuario().equals(usuario) && 
               emp.getContrasenia().equals(contrasenia))                        
                return true;
        }
        return false;
    }
    
    public static boolean verificarExistenciaEmpleado(Empleado empleado)
    {
        //Este método permite verificar si el usuario a crear es vendedor, comprador o ninguno. Validacion en el main y para el metodo que agrega los tipo de usuario
        ArrayList<Empleado> empleados = Empleado.desserializarEmpleado("Empleado.ser");
        for(Empleado emp: empleados)
        {
            if(emp.getUsuario().equals(empleado.getUsuario()) && 
               emp.getContrasenia().equals(empleado.getContrasenia()) &&
               emp.getTipoEmpleado().equals(empleado.getTipoEmpleado()))                        
                return true;
        }
        return false;
    }
    
    public static Empleado obtenerEmpleado(String usuario, String contrasenia)
    {       
        ArrayList<Empleado> empleados = Empleado.desserializarEmpleado("Empleado.ser");
        for(Empleado emp: empleados)
        {
            if(emp.getUsuario().equals(usuario) && 
               emp.getContrasenia().equals(contrasenia))                        
                return emp;
        }
        return null;
    }
    
    //Permite convertir la fecha de tipo String a Date para ser guardada en el archivo
    public static Date StringToDateTime(String fecha)
    {        
        try {  
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return new Date();
    }
    
    public static Date StringToDate(String fecha)
    {        
        try {  
            return new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return new Date();
    }
    
    //Permite convertir la fecha de tipo Date a String para ser guardada en el archivo
    public static String dateTimeToString(Date fecha)
    {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(fecha);
    }
    
    public static ArrayList<ReporteVentas> consultarReportesFecha(Date fechaInicio, Date fechaFin)
    {
        ArrayList<ReporteVentas> reportes = new ArrayList<>();
        ArrayList<ReporteVentas> reportesTemp = ReporteVentas.desserializarReporte("Reporte.ser");
        for(ReporteVentas reporte: reportesTemp)
        {
            if(reporte.getFechaPedido().after(fechaInicio) && reporte.getFechaPedido().before(fechaFin))
                reportes.add(reporte);
        }
        return reportes;
    }
    
    public static ArrayList<Producto> productosBuscados(String nombre)
    {
        ArrayList<Producto> productos = Producto.desserializarProducto("Producto.ser");
        System.out.println("Lista de productos=>"+productos.size());
        ArrayList<Producto> productosTemp = new ArrayList<>();
        for(Producto prod : productos)
        {
            if(prod.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            {
                System.out.println("Si contiene al producto");
                productosTemp.add(prod);
            }
        }
        return productosTemp;
    }
    
    public static ArrayList<Producto> cargarListadoProductos()
    {       
        return Producto.desserializarProducto("Producto.ser");                
    }
    
     public static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }
     
    public static int validarNumeroEntero(String valor)
    {
        try
        {
            return Integer.parseInt(valor);
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    
    public static double validarNumeroDouble(String valor)
    {
        try
        {
            return Double.parseDouble(valor);
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    
    public static boolean verificarExistenciaProducto(String codigo)
    {     
        ArrayList<Producto> productos = Producto.desserializarProducto("Producto.ser");
        for(Producto prod: productos)
        {
            if(prod.getCodigo().equals(codigo))                        
                return true;
        }
        return false;
    }
    
    public static CategoriaProducto getCategoriaProducto(String categoria)
    {
       CategoriaProducto categoriaProducto = CategoriaProducto.SOPAS;
       switch(categoria)
       {
           case "RAPIDA":
               categoriaProducto = CategoriaProducto.RAPIDA;
               break;
            
           case "SOPAS":
                categoriaProducto = CategoriaProducto.SOPAS;
                break;
            
            case "ARROZ":
               categoriaProducto = CategoriaProducto.ARROZ;
               break;
            
            case "BEBIDAS":
               categoriaProducto = CategoriaProducto.BEBIDAS;
               break;
            
            case "POSTRE":
               categoriaProducto = CategoriaProducto.POSTRE;
               break;           
       }
       return categoriaProducto;       
    }
    
    public static String getCategoriaProducto(CategoriaProducto categoria)
    {
       String categoriaProducto = "SOPA";
       switch(categoria)
       {
           case RAPIDA:
               categoriaProducto =  "RAPIDA";
               break;
            
           case SOPAS:
                categoriaProducto = "SOPAS";
                break;
            
            case ARROZ:
               categoriaProducto = "ARROZ";
               break;
            
            case BEBIDAS:
               categoriaProducto = "BEBIDAS";
               break;
            
            case POSTRE:
               categoriaProducto = "POSTRE";
               break;           
       }                                   
       return categoriaProducto;       
    }
}
