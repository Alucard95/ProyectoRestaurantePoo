package controlador;

import javafx.scene.control.Alert;

public class Helper 
{
    public static void showMessage(Alert show,String titulo, String encabezado, String mensaje){
        show.setHeaderText(encabezado);
        show.setTitle(titulo);
        show.setContentText(mensaje);
        show.showAndWait();
    }
    
}
