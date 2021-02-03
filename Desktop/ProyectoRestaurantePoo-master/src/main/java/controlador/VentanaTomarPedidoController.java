package controlador;

import com.project.proyectorestaurante.App;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.CabeceraPedido;
import modelo.Cliente;
import modelo.DetalleListener;
import modelo.DetallePedido;
import modelo.Empleado;
import modelo.Mesero;
import modelo.Producto;
import modelo.ProductoListener;

public class VentanaTomarPedidoController implements Initializable {

    @FXML
    private GridPane grid_Productos;

    private ProductoListener productListener;
    private DetalleListener detalleListener;
    ArrayList<Producto> productos;
    int numeroMesa;
    Mesero mesero;
    Cliente cliente;
    int numeroCuenta;
    CabeceraPedido cabeceraP;
    ArrayList<DetallePedido> detalleP;
    @FXML
    private VBox vbox_LineasPedido;
    @FXML
    private Label lbl_totalPedido;

    double totalPedido = 0;
    String totalString = "";
    @FXML
    private Label lbl_tituloPedido;
    @FXML
    private TextField lbl_buscar_productos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {        
    }    

    public void cargarDatosPedido(int numMesa, Mesero mes, Cliente cli,CabeceraPedido cab)
    {           
        numeroMesa = numMesa;
        mesero = mes;
        cliente = cli; 
        numeroCuenta = 1;
        lbl_tituloPedido.setText("Mesa "+numeroMesa+", Cliente: "+cliente.getNombre()+" "+cliente.getApellido()+", Mesero "+mesero.getUsuario());
        cargarListadoProductos();
        cargarPedido(cab);
    }
 
    private void asignarOpcion(Empleado emp) throws IOException
    {
        FXMLLoader vc = Helper.loadFXML("VentanaMenuPrincipal");
        Parent form = vc.load();
        VentanaMenuPrincipalController vcc = vc.getController();
        App.setRoot(form);
        vcc.cargarMenuTipoEmpleado(emp,false);
    }
    
    public void cargarListadoProductos()
    {
        productos = Helper.cargarListadoProductos();
        cargarListenerProductos();
        cargarListenerDetalles();
        cargarGrid();                
    }
    
    public void cargarListenerProductos()
    {
        productListener = new ProductoListener() {            
            @Override
            public void onClickListener(Producto producto) {
                agregarProductoPedido(producto);
            }
        };
    }
    
    public void cargarListenerDetalles()
    {
        detalleListener = new DetalleListener() {
            @Override
            public void onClickListener(DetallePedido detalle) 
            {
                eliminarDetallePedido(detalle);
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

                if (column == 4) {
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
    
    public void cargarPedido(CabeceraPedido cab)
    {        
        try
        {
            cargarCabeceraPedido(cab);            
            cargarDetallePedido();             
        }
        catch(Exception e)
        {            
        }        
    }
    
    public void cargarCabeceraPedido(CabeceraPedido cab) throws Exception
    {
        if(cab == null)
        {
            System.out.println("Se crea el pedido de cero");
            cabeceraP = Helper.cargarPedido(numeroMesa, mesero, cliente);        
            CabeceraPedido.guardarCabeceraPedido(cabeceraP);
        }
        else
        {
            System.out.println("Se carga al pedido existente");
            cabeceraP = cab;
        }
            
    }
    
    public void cargarDetallePedido()
    {
        detalleP = Helper.cargarDetallePedido(cabeceraP);   
        cargarLineasPedido(detalleP);
    }
    
    public void agregarProductoPedido(Producto producto)
    {
        DetallePedido detalle;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Mensaje del Sistema");
        dialog.setHeaderText("Cantidad del Producto");
        dialog.setContentText("Ingrese la cantidad:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        int cantidad = 0;
        double subtotalLinea,ivaLinea,totalLinea = 0;
        if (result.isPresent())
        {            
            cantidad = Helper.validarNumeroEntero(result.get());
            if(cantidad == 0)            
                Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Mensaje de Sistema", "Cantidad ingresada", "Favor ingresar una cantidad válida mayor a 0");                
            else
            {
                try
                {
                    subtotalLinea    = producto.getPrecio() * cantidad;
                    ivaLinea         = subtotalLinea * 0.12;
                    totalLinea       = subtotalLinea + ivaLinea;    
                    
                    if(detalleP == null)                    
                    {
                        System.out.println("El detalle es vacio");
                        detalleP = new ArrayList<>();
                    }                       
                                        
                    detalle = Helper.verificarProductoEnDetalle(detalleP, producto);
                    int indiceDetalle = 0;
                    //Actualizar los datos de la linea
                    if(detalle != null)
                    {
                        indiceDetalle = detalleP.indexOf(detalle);
                        detalleP.get(indiceDetalle).setCantidad(cantidad);
                        detalleP.get(indiceDetalle).setSubtotalLinea(subtotalLinea);
                        detalleP.get(indiceDetalle).setIvaLinea(ivaLinea);
                        detalleP.get(indiceDetalle).setTotalLinea(totalLinea);
                        System.out.println("Entro a actualizar");
                        DetallePedido.eliminarDetallePedido(detalle,cabeceraP);
                        DetallePedido.actualizarDetallePedido(detalle);
                    }
                    else 
                    {
                        System.out.println("Entro a crear detalle");
                        detalle = new DetallePedido(cabeceraP, producto, cantidad,subtotalLinea, ivaLinea, totalLinea);
                        detalleP.add(detalle);                                
                        DetallePedido.guardarDetallePedido(detalle);
                    }
                    
                    cargarLineasPedido(detalleP);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }            
        }
    }       
    
    public void cargarLineasPedido(ArrayList<DetallePedido> detalles)
    {               
       totalPedido = 0;         
       vbox_LineasPedido.getChildren().clear();
       for(DetallePedido detalle: detalles)
       {
           try  
           {
                FXMLLoader vc = Helper.loadFXML("itemPedido");                
                AnchorPane anchorPane = vc.load();
                ItemPedidoController itemPedidoController = vc.getController();
                itemPedidoController.setItemPedido(detalle,detalleListener);                                
                totalPedido += detalle.getTotalLinea();
                Platform.runLater(() -> actualizaTotalPedido(totalPedido,anchorPane));                
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }            
       }              
    }
    
    public void actualizaTotalPedido(double total, AnchorPane anchorPane)
    {
        vbox_LineasPedido.getChildren().add(anchorPane); 
        totalString = String.format("%.2f", total);
        lbl_totalPedido.setText("Total: $"+totalString);
    }
    
    public void eliminarDetallePedido(DetallePedido detalle) 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje de Sistema");
        alert.setHeaderText("Eliminacion del producto "+detalle.getProducto().getNombre().trim());
        alert.setContentText("Desea proceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            try
            {
                //Eliminar la linea
                DetallePedido.eliminarDetallePedido(detalle,cabeceraP);
                cargarDetallePedido();
                cargarLineasPedido(detalleP);            
            }
            catch(Exception e)
            {
                
            }
        }                
    }

    @FXML
    private void accionFinalizarPedido(ActionEvent event) 
    {
        double subtotal = 0,iva = 0,total = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje de Sistema");
        alert.setHeaderText("Finalizar el pedido ");
        alert.setContentText("Desea proceder?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            try
            {
                for(DetallePedido det : detalleP)
                {
                    subtotal += det.getSubtotalLinea();
                    iva += det.getIvaLinea();
                    total += det.getTotalLinea();                            
                }
                //Finalizar Pedido
                cabeceraP.setCerrado(true);
                cabeceraP.setSubtotal(subtotal);
                cabeceraP.setIva(iva);
                cabeceraP.setTotal(total);                
                Helper.finalizarPedido(numeroCuenta,cabeceraP, detalleP);
                regresar();
            }
            catch(Exception e)
            {
                
            }
        }   
    }
    
    public void regresar()          
    {    
        Stage stageActual = (Stage) lbl_tituloPedido.getScene().getWindow();            
        stageActual.close(); 
    }

    @FXML
    private void accionRegresar(ActionEvent event) 
    {
        regresar();
    }

    @FXML
    private void accionBuscarProductos(ActionEvent event) 
    {
        String productoBuscar = lbl_buscar_productos.getText().trim();       
        productos = Helper.productosBuscados(productoBuscar);
        cargarListenerProductos();
        cargarListenerDetalles();
        cargarGrid();        
    }
}
