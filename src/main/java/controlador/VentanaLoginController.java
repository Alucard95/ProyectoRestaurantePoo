package controlador;

import com.project.proyectorestaurante.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import modelo.Empleado;

public class VentanaLoginController implements Initializable {

    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_contrasenia;
    Empleado empleado;
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txt_contrasenia.setTextFormatter(new TextFormatter<>(condicion -> (condicion.getControlNewText().matches("\\w{0,15}"))? condicion: null ));
    }    

    @FXML
    private void accionLogin(ActionEvent event) 
    {
        try
        {            
            String usuario = txt_usuario.getText().trim();
            String contrasenia = txt_contrasenia.getText().trim();            
            if(Helper.verificarExistenciaEmpleado(usuario,contrasenia))          
            {
                Helper.showMessage(new Alert(Alert.AlertType.INFORMATION), "Sesión", "Sesión iniciada", "Los datos fueron correctamente cargados");                
                empleado = Helper.obtenerEmpleado(usuario, contrasenia);                
                if(empleado != null)                                    
                    asignarOpcion();
                else                   
                    Helper.showMessage(new Alert(Alert.AlertType.ERROR), "Sesión", "Error al iniciar", "No existe un usuario con esos datos.\nIngrese correctamente los datos");
            }
            else
                    Helper.showMessage(new Alert(Alert.AlertType.ERROR), "Sesión", "Error al iniciar", "No existe un usuario con esos datos.\nIngrese correctamente los datos");
        }
        catch(Exception e)
        {
            Helper.showMessage(new Alert(Alert.AlertType.ERROR), "Sesión", "Error al iniciar", "ERROR EN EL SISTEMA");
        }     
        
    }
    
    private void asignarOpcion()
    {
        try
        {                       
            //Muestra la pantalla del Menu Principal            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaMenuPrincipal" + ".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();  
            VentanaMenuPrincipalController controlador = fxmlLoader.<VentanaMenuPrincipalController>getController();                                
            controlador.cargarMenuTipoEmpleado(empleado,false);
            stage.setTitle("Menu Principal");
            stage.setMaxWidth(800);
            stage.setMaxHeight(600);
            stage.setScene(new Scene(root));  
            stage.show(); 
            
            Stage stageActual = (Stage) txt_usuario.getScene().getWindow();            
            stageActual.close();                       
        }
        catch(Exception e)
        {
                       
        }                
    } 

    @FXML
    private void accionRegistrar(ActionEvent event) throws Exception
    {
        //App.setRoot("Sesion");
    }

    @FXML
    private void accionSalir(ActionEvent event) 
    {
        System.exit(0);
    }

}
