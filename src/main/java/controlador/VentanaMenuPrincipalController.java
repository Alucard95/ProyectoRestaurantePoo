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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import modelo.Empleado;
import modelo.Mesero;
import modelo.TipoEmpleado;

public class VentanaMenuPrincipalController implements Initializable {

    @FXML
    private Label lbl_regresar;
    @FXML
    private Menu menu_opciones;
    @FXML
    private MenuItem menu_monitoreo;
    @FXML
    private MenuItem menu_gestion_menu;
    @FXML
    private MenuItem menu_reporte;
    @FXML
    private MenuItem menu_plano_restaurante;
    @FXML
    private MenuItem menu_tomar_pedido;
    
    private TipoEmpleado tipoEmpleado;
    private Empleado empleado;
    private Mesero mesero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void menu(ActionEvent event) 
    {
        try
        {                        
            //Muestra la pantalla del Menu Principal            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaLogin" + ".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();              
            stage.setTitle("Login ");
            stage.setMaxWidth(800);
            stage.setMaxHeight(600);
            stage.setScene(new Scene(root));  
            stage.show(); 
            
            Stage stageActual = (Stage) lbl_regresar.getScene().getWindow();            
            stageActual.close();                       
        }
        catch(Exception e)
        {
                       
        }  
    }

    public void cargarMenuTipoEmpleado(Empleado emp,boolean mostrarPlano)
    {   
        empleado = emp;
        menu_opciones.setVisible(true);
        
        menu_monitoreo.setVisible(false);
        menu_gestion_menu.setVisible(false);
        menu_plano_restaurante.setVisible(false);
        menu_reporte.setVisible(false);
        menu_tomar_pedido.setVisible(false);
        
        if(empleado.getTipoEmpleado().equals(TipoEmpleado.ADMINISTRADOR))
        {
            menu_monitoreo.setVisible(true);
            menu_gestion_menu.setVisible(true);
            menu_plano_restaurante.setVisible(true);
            menu_reporte.setVisible(true);            
        }
        else        
            menu_tomar_pedido.setVisible(true);                                    
    }
    
    @FXML
    private void salir(ActionEvent event)
    {
        System.exit(0);
    }

    @FXML
    private void mostrarMonitoreo(ActionEvent event) {
    }

    @FXML
    private void mostrar_menu_productos(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaGestionMenu" + ".fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();  
        stage.setTitle("Gestión del Menu");
        stage.setMaxWidth(750);
        stage.setMaxHeight(500);
        stage.setScene(new Scene(root));  
        stage.show(); 
    }

    @FXML
    private void mostrar_Reporte_Ventas(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaReporteVentas" + ".fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();  
        stage.setTitle("Reporte de Ventas");
        stage.setWidth(840);
        stage.setHeight(440);
        stage.setScene(new Scene(root));  
        stage.show(); 
    }

    @FXML
    private void mostrar_menu_plano(ActionEvent event) {
    }

    @FXML
    private void mostrar_toma_pedido(ActionEvent event) {
    }
    
}
