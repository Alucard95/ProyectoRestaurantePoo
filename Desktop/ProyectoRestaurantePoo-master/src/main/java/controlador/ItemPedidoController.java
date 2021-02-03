package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.DetalleListener;
import modelo.DetallePedido;

public class ItemPedidoController implements Initializable {

   @FXML
    private Label lbl_LineName;
    @FXML
    private Label lbl_lineDescription;
    @FXML
    private Label lbl_lineTotal;        
    
    private DetalleListener myListener;
    private DetallePedido detalle;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        
    @FXML
    private void click(MouseEvent event) 
    {
        myListener.onClickListener(detalle);
    }
    
     public void setItemPedido(DetallePedido detalle,DetalleListener listener) 
     {
         System.out.println("Setear detalle");
        this.detalle = detalle;
        this.myListener = listener;
        lbl_LineName.setText(detalle.getProducto().getNombre().trim());
        lbl_lineDescription.setText(detalle.getCantidad()+" Unidades a $"+detalle.getProducto().getPrecio());
        lbl_lineTotal.setText("$"+detalle.getSubtotalLinea());        
    } 
    
}
