package controlador;

import com.project.proyectorestaurante.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import modelo.Empleado;
import modelo.TipoEmpleado;

public class VentanaSesionController implements Initializable {

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellidos;
    @FXML
    private TextField txt_contrasenia;
    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_email;
    @FXML
    private CheckBox chk_administrador;
    @FXML
    private CheckBox chk_mesero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void accionRegistrar(ActionEvent event) 
    {
        try{            
            String nombre = txt_nombre.getText().trim();
            String apellido = txt_apellidos.getText().trim();
            String usuario = txt_usuario.getText().trim();
            String contrasenia = txt_contrasenia.getText().trim();
            String email = txt_email.getText().trim();
            TipoEmpleado tipo = TipoEmpleado.MESERO;
            
            //Validar que se seleccione un solo tipo de Empleado
            if(!chk_administrador.isSelected() && !chk_mesero.isSelected())
                Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de cuenta",null,"Debe seleccionar al menos un tipo de Empleado!");
            else if(chk_administrador.isSelected() && chk_mesero.isSelected())
                Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de cuenta",null,"Debe estar seleccionado sólo un tipo de Empleado!");
            else
            {
                if(chk_administrador.isSelected())
                    tipo = TipoEmpleado.ADMINISTRADOR;
                else
                    tipo = TipoEmpleado.MESERO;
                
                //Cargar el empleado
                Empleado empleado = new Empleado(nombre,apellido,usuario,contrasenia,email,tipo);                    
                
                //Validar que el empleado no exista
                boolean verificarEstadoEmpleado = Helper.verificarExistenciaEmpleado(empleado);
                if(verificarEstadoEmpleado)
                    Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de cuenta",null,"El empleado ya existe!");                                                
                
                empleado.guardarEmpleado(empleado);
                App.setRoot("VentanaLogin");                                                
            }                                                                            
        }
        catch(Exception e)
        {            
           Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de cuenta",null,"Wow! No se ha podido crear el empleado");
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) throws Exception
    {
        App.setRoot("VentanaLogin");
    }
    
}
