package controlador;

import com.project.proyectorestaurante.App;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import modelo.Producto;
import modelo.ProductoListener;

public class VentanaGestionMenuController implements Initializable {

    @FXML
    private GridPane grid_Productos;
    @FXML
    private TextField lbl_buscar_productos;

    ArrayList<Producto> productos;
    private ProductoListener productListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListadoProductos();
    }    

    @FXML
    private void accionBuscarProductos(ActionEvent event) 
    {
        String productoBuscar = lbl_buscar_productos.getText().trim();       
        productos = Helper.productosBuscados(productoBuscar);
        cargarListenerProductos();        
        cargarGrid(); 
    }

    @FXML
    private void accionAgregarProducto(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaRegistrarProducto" + ".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();  
            stage.setTitle("Registro de Producto");
            VentanaRegistrarProductoController controlador = fxmlLoader.<VentanaRegistrarProductoController>getController();                                
            controlador.cargarPanelProducto(null,"INSERT");  
            stage.setMaxWidth(800);
            stage.setMaxHeight(400);
            stage.setScene(new Scene(root));  
            stage.show(); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
 
    public void cargarListadoProductos()
    {
        productos = Helper.cargarListadoProductos();
        cargarListenerProductos();        
        cargarGrid();                
    }
    
    public void cargarListenerProductos()
    {
        productListener = new ProductoListener() {            
            @Override
            public void onClickListener(Producto producto) {
                editarProducto(producto);
            }
        };
    }
    
    public void cargarGrid()
    {
        grid_Productos.getChildren().clear();
        int column = 0;
        int row = 1;
        try 
        {
            for(Producto producto : productos)
            {
                FXMLLoader vc = Helper.loadFXML("itemProducto");                
                AnchorPane anchorPane = vc.load();

                ItemProductoController itemProductoController = vc.getController();
                itemProductoController.setItemProducto(producto, productListener);

                if (column == 5) {
                    column = 0;
                    row++;
                }

                grid_Productos.add(anchorPane, column++, row); //(child,column,row)
                
                grid_Productos.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_Productos.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_Productos.setMaxWidth(Region.USE_PREF_SIZE);
                
                grid_Productos.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_Productos.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_Productos.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void editarProducto(Producto producto)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaRegistrarProducto" + ".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();  
            stage.setTitle("Modificar Producto");
            VentanaRegistrarProductoController controlador = fxmlLoader.<VentanaRegistrarProductoController>getController();                                
            controlador.cargarPanelProducto(producto,"UPDATE");  
            stage.setMaxWidth(800);
            stage.setMaxHeight(400);
            stage.setScene(new Scene(root));  
            stage.show(); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
