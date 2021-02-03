/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.project.proyectorestaurante.App;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Cita;

/**
 *
 * @author bleac
 */
public class VentanaRegistroCitaController implements Initializable {
    ArrayList<Cita> citas = Cita.desserializarCita("Cita.ser");
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_mesa;
    Cita cita;
    
    @FXML
    private Label lbl_cita;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    private void accionRegistrar(ActionEvent event) 
    {
        try{            
            String nombre = txt_nombre.getText().trim();
            String nmesa = txt_mesa.getText().trim();
            
            cita = new Cita(nombre,Integer.valueOf(nmesa));
            
            Cita.guardarCita(cita);
            
            ArrayList<Cita> citas = Cita.desserializarCita("Cita.ser");
            
            lbl_cita = new Label(citas.get(0).getNombre() + citas.get(0).getnMesa());
            for(Cita c:citas){
                System.out.println("Citas=" + c.getNombre()+","+c.getnMesa());
            }
                                                                                                 
        }
        catch(Exception e)
        {            
           Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de cuenta",null,"Wow! No se ha podido crear el empleado");
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) 
    {
        System.exit(0);
    }
}
