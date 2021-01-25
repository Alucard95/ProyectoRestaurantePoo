package modelo;

public class Mesero extends Empleado
{
    public Mesero()
    {
        super("","","","","",TipoEmpleado.MESERO);
    }
    
    public Mesero(String nombre, String apellido, String usuario, String contrasenia,String email,TipoEmpleado tipo) {
        super(nombre,apellido,usuario,contrasenia,email,tipo);
    }   
}
