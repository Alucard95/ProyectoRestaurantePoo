package controlador;

import java.util.ArrayList;
import javafx.scene.control.Alert;
import modelo.Empleado;

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
    
}
