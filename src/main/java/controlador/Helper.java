package controlador;

import com.project.proyectorestaurante.App;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import modelo.Administrador;
import modelo.CabeceraPedido;
import modelo.CategoriaProducto;
import modelo.Cliente;
import modelo.DetallePedido;
import modelo.Disponibilidad;
import modelo.Empleado;
import modelo.Mesa;
import modelo.Mesero;
import modelo.OrdenadorFechaPedido;
import modelo.Producto;
import modelo.ReporteVentas;
import modelo.TipoEmpleado;

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
    
    public static CabeceraPedido cargarPedido(int numMesa, Mesero mesero, Cliente cliente)
    {
        Date fechaPedido = new Date();    
        ArrayList<CabeceraPedido> cabecerasPedido = CabeceraPedido.desserializarCabeceraPedido("Cabecera.ser");        
        int nMesa;
        String usuario, nombre, apellido;
        
        if(cabecerasPedido.size() > 0)
        {
            for(CabeceraPedido cabecera : cabecerasPedido)
            {
                nMesa = cabecera.getNumeroMesa();
                usuario = cabecera.getMesero().getUsuario();
                nombre = cabecera.getCliente().getNombre();
                apellido = cabecera.getCliente().getApellido();
                if(nMesa == numMesa && usuario.equalsIgnoreCase(mesero.getUsuario()) && 
                   nombre.equalsIgnoreCase(cliente.getNombre()) && 
                   apellido.equalsIgnoreCase(cliente.getApellido()) &&
                   !cabecera.isCerrado())                                
                {
                   return cabecera;
                }
            }     
            
            //Crear Pedido
            return new CabeceraPedido(fechaPedido, numMesa, mesero, cliente, 0, 0, 0,false);
        }
        else
            //Crear Pedido
            return new CabeceraPedido(fechaPedido, numMesa, mesero, cliente, 0, 0, 0,false);    
    }
    
    public static ArrayList<DetallePedido> cargarDetallePedido(CabeceraPedido cabecera)
    {          
        Date fecha = cabecera.getFecha();
        int numero = cabecera.getNumeroMesa();
        String mesero = cabecera.getMesero().getUsuario();
        
        ArrayList<DetallePedido> detallesPedido = DetallePedido.desserializarDetallePedido("Detalle.ser");
        ArrayList<DetallePedido> cargarDetalles = new ArrayList<>();        
        if(detallesPedido.size() > 0)
        {
            for(DetallePedido det : detallesPedido)
            {
                Date fechaP = det.getCabecera().getFecha();
                int num     = det.getCabecera().getNumeroMesa();
                String mes  = det.getCabecera().getMesero().getUsuario(); 
                if(fechaP.equals(fecha) && num == numero && 
                   mesero.equalsIgnoreCase(mes))    
                    cargarDetalles.add(det);
            }
            return cargarDetalles;            
        }
        else            
            return cargarDetalles;
    }
    
    public static DetallePedido verificarProductoEnDetalle(ArrayList<DetallePedido> detalles, Producto producto)
    {
        for(DetallePedido detalle : detalles)
        {
            if(detalle.getProducto().getCodigo().equalsIgnoreCase(producto.getCodigo()))
                return detalle;
        }
        return null;
    }
    
    public static void finalizarPedido(int numCuenta, CabeceraPedido cabecera, ArrayList<DetallePedido> detallesPedido)
    {
        Iterator itr;
        Date fecha = cabecera.getFecha();
        int numero = cabecera.getNumeroMesa();
        String mesero = cabecera.getMesero().getUsuario();  
        String cliente = cabecera.getCliente().getNombre()+" "+cabecera.getCliente().getApellido();
        double total = cabecera.getTotal();
        ArrayList<CabeceraPedido> cabeceras = CabeceraPedido.desserializarCabeceraPedido("Cabecera.ser");        
        
        try
        {
            //Eliminar la cabecera de Pedido
            itr = cabeceras.iterator(); 
            while (itr.hasNext()) 
            { 
                CabeceraPedido cab = (CabeceraPedido)itr.next();  
                Date fechaP = cab.getFecha();
                int num     = cab.getNumeroMesa();
                String mes  = cab.getMesero().getUsuario();                        
                if(fechaP.equals(fecha) && num == numero && 
                   mesero.equalsIgnoreCase(mes))
                    itr.remove(); 
            }
            cabeceras.add(cabecera);
            CabeceraPedido.guardarCabeceraPedido(cabecera);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //Eliminar los detalles de esa cabecera
        ArrayList<DetallePedido> detalles = DetallePedido.desserializarDetallePedido("Detalle.ser");
        itr = detalles.iterator(); 
        while (itr.hasNext()) 
        { 
            DetallePedido det = (DetallePedido)itr.next();  
            Date fechaP = det.getCabecera().getFecha();
            int num     = det.getCabecera().getNumeroMesa();
            String mes  = det.getCabecera().getMesero().getUsuario();                        
            if(fechaP.equals(fecha) && num == numero && 
               mesero.equalsIgnoreCase(mes))                                    
                itr.remove(); 
        } 
        
        try
        {
            //Agregar Detalles al archivo de Detalles
            for(DetallePedido detalle : detallesPedido)
                DetallePedido.guardarDetallePedido(detalle);
        }
        catch(Exception e)
        {
            
        }
        try
        {
            //CrearReporte
            ReporteVentas reporte = new ReporteVentas(fecha,numCuenta,numero,mesero,cliente,total);
            ReporteVentas.guardarReporte(reporte);
            
            //Actualizar mesa a disponible
            Mesa mesa = Helper.obtenerMesa(numero);
            if(mesa != null)
            {
                mesa.setEstadoDisponible(Disponibilidad.DISPONIBLE);
                Mesa.eliminarMesa(mesa);
                Mesa.actualizarMesa(mesa);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static Mesa obtenerMesa(int numMesa)
    {        
        ArrayList<Mesa> mesas = Mesa.desserializarMesa("Mesa.ser");
        for(Mesa mesa : mesas)
        {
            if(mesa.getNumero() == numMesa)
                return mesa;
        }                
        return null;
    }
    
    public static ArrayList<Mesa> obtenerMesasExistentes()
    {
        return Mesa.desserializarMesa("Mesa.ser");
    }
    
    public static CabeceraPedido getUltimoPedidoAbiertoMesero(int numMesa, Mesero mesero)
    {        
        ArrayList<CabeceraPedido> cabeceras =  CabeceraPedido.desserializarCabeceraPedido("Cabecera.ser");
        cabeceras.sort(new OrdenadorFechaPedido());
        for(CabeceraPedido cabe : cabeceras)
        {
            if(cabe.getNumeroMesa() == numMesa && 
               cabe.getMesero().getUsuario().equals(mesero.getUsuario()) &&
               !cabe.isCerrado())
                return cabe;
        }
        return null;
    }    
    
    public static Mesero convertirEmpleadoAMesero(Empleado emp)
    {
        String nombre      = emp.getNombre();
        String apellido    = emp.getApellido();
        String usuario     = emp.getUsuario();
        String contrasenia = emp.getContrasenia();
        String email       = emp.getEmail();       
        return new Mesero(nombre, apellido, usuario, contrasenia, email, TipoEmpleado.MESERO);
    }
    
    public static Administrador convertirEmpleadoAAdministrador(Empleado emp)
    {
        String nombre      = emp.getNombre();
        String apellido    = emp.getApellido();
        String usuario     = emp.getUsuario();
        String contrasenia = emp.getContrasenia();
        String email       = emp.getEmail();       
        return new Administrador(nombre, apellido, usuario, contrasenia, email, TipoEmpleado.ADMINISTRADOR);
    }
    
    public static boolean verificarMeseroEnMesa(Mesero mesero, int numMesa)
    {   
        System.out.println("Mesero verificar=>"+mesero.getUsuario());
        try
        {
            ArrayList<Mesa> mesas = Mesa.desserializarMesa("Mesa.ser");
            for(Mesa mesa : mesas)
            {
                System.out.println("Mostrar mesa=>"+mesa.getNumero());
                System.out.println("Mostrar mesa=>"+mesa.getMesero().getUsuario());
                if(mesa.getNumero() == numMesa)
                    if(mesa.getMesero().getUsuario().isEmpty())
                        return true;
                    else
                        if(mesa.getMesero().getUsuario().equals(mesero.getUsuario()))                    
                            return true;                
            }                
            return false;
        }
        catch(Exception e)
        {
            System.out.println("error cargado=>"+e.getLocalizedMessage());            
        }
        return false;
    }
    
    public static boolean verificarMesaCreada(double x, double y)
    {        
        ArrayList<Mesa> mesas = Mesa.desserializarMesa("Mesa.ser");
        for(Mesa mesa : mesas)
        {
            if(mesa.getCoordenadaX() == x && mesa.getCoordenadaY() == y)
                return true;
        }                
        return false;
    }
}
