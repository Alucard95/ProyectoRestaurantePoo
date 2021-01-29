package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Producto;
import modelo.ProductoListener;

public class ItemProductoController implements Initializable {

    @FXML
    private Label lbl_NombreProducto;
    @FXML
    private Label lbl_PrecioProducto;
    @FXML
    private ImageView img_Producto;
    
    private Producto producto;
    private ProductoListener myListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {         
    }    

    @FXML
    private void click(MouseEvent event) 
    {
        myListener.onClickListener(producto);
    }
    
    public void setItemProducto(Producto producto, ProductoListener myListener) {
        this.producto = producto;
        this.myListener = myListener;
        lbl_NombreProducto.setText(producto.getNombre());
        lbl_PrecioProducto.setText("$" + producto.getPrecio());                                        
        String imagenProducto = "src/main/resources/imageHelper/"+producto.getURLImagen();           
        img_Producto.setImage(new Image(new File(imagenProducto).toURI().toString()));                         
    }
}
