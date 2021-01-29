package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.CategoriaProducto;
import modelo.Producto;

public class VentanaRegistrarProductoController implements Initializable {
    
    
    Producto producto;
    String categoria;
    String codigo;
    String nombre;
    String descripcion;
    String precio;    
    String urlImagen;    
    String modo;
    private File imageFile;
    private Path origen,destino;
    
    @FXML
    private TextField txt_precio;
    @FXML
    private Button btnRegistrar;
    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descripcion;
    @FXML
    private TextField txt_nombre;
    @FXML
    private ComboBox<String> cmb_categoria_producto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        añadirBoxCategoria();
    }    
    
    public void cargarPanelProducto(Producto prod,String modo)
    {
        this.modo = modo;
        if(modo.equals("INSERT"))
        {            
            btnRegistrar.setText("Registrar");
            cmb_categoria_producto.getSelectionModel().select("SOPAS");
        }
        else
        {
            producto = prod;         
            btnRegistrar.setText("Actualizar");
            cargarDatosProducto(producto);
        }
    }
    
    public void cargarDatosProducto(Producto prod)
    {
        categoria = prod.getCategoria().name();
        codigo = prod.getCodigo().trim();
        nombre = prod.getNombre();
        descripcion = prod.getDescripcion().trim();
        precio = String.valueOf(prod.getPrecio());
        urlImagen = prod.getURLImagen();
        cmb_categoria_producto.getSelectionModel().select(categoria);
        txt_codigo.setText(codigo);
        txt_nombre.setText(nombre);
        txt_descripcion.setText(descripcion);
        txt_precio.setText(precio);     
        btnRegistrar.setText("Actualizar");
    }
    
    @FXML
    private void registrarProducto(MouseEvent event) 
    {
        try
        {
            codigo = txt_codigo.getText().trim();
            nombre = txt_nombre.getText().trim();
            descripcion = txt_descripcion.getText().trim();
            String categoriaString = cmb_categoria_producto.getSelectionModel().getSelectedItem().trim();
            double precio = Helper.validarNumeroDouble(txt_precio.getText().trim());
            urlImagen = (imageFile != null ? imageFile.getName() : "");
            CategoriaProducto categoria = Helper.getCategoriaProducto(categoriaString);
            
            if(codigo.isEmpty() || nombre.isEmpty() ||precio == 0 )
                Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de Producto",null,"Debe ingresar el código, el nombre del producto y el precio!");
            else
            {
                Producto producto;
                if(modo.equals("INSERT"))
                {
                    if(Helper.verificarExistenciaProducto(codigo))
                    Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR),"Creación de Producto",null,"El código del producto ya existe!");
                    else
                    {
                        producto = new Producto(codigo,nombre,descripcion,urlImagen,precio,categoria); 
                        if(origen!=null && destino != null) 
                            Files.copy(origen, destino, REPLACE_EXISTING);            
                        Producto.guardarProducto(producto);                
                        regresar();
                    }
                }
                else
                {
                        producto = new Producto(codigo,nombre,descripcion,urlImagen,precio,categoria); 
                        if(origen!=null && destino != null) 
                            Files.copy(origen, destino, REPLACE_EXISTING);            
                        Producto.eliminarProducto(producto);
                        Producto.actualizarProducto(producto);              
                        regresar();
                }
            }
                                        
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Registro de Producto", "Error", "Hubo problemas al registrarse, procure \nIngresar correctamente los datos.");
        }finally{
            //btnRegistrar.setDisable(true);
        }
    }
    
    @FXML
    public void regresar()
    {
        Stage stageActual = (Stage) txt_codigo.getScene().getWindow();            
        stageActual.close();
    }

    private void regresar(MouseEvent event) throws IOException
    {
        regresar();
        
    }
    
    @FXML
    private void adjuntarFoto(MouseEvent event) 
    {
        try{            
            imageFile = (new FileChooser()).showOpenDialog(null);
            if(imageFile.getName().endsWith(".jpg")||imageFile.getName().endsWith(".png")){
                origen = Paths.get(imageFile.getPath());
                destino = Paths.get(System.getProperty("user.dir")+"/src/main/resources/imageHelper/"+imageFile.getName());
                Helper.mostrarMensaje(new Alert(Alert.AlertType.INFORMATION), "Cargar una imagen", "Listo!", imageFile.getName()/*"Se ha cargado correctamente la imagen"*/);
                btnRegistrar.setDisable(false);
            }
        }catch(Exception e){
            Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Cargar una imagen", "Carga Fallida!", "Algo debió ocurrir con el Sistema,\nPor favor, envía un correo notificando tu problema (Ayuda).");
        }
    }
    
    private void añadirBoxCategoria(){
        ArrayList<String> listaCategoria = new ArrayList<>();
        listaCategoria.add("RAPIDA");
        listaCategoria.add("SOPAS");
        listaCategoria.add("ARROZ");
        listaCategoria.add("BEBIDAS");
        listaCategoria.add("POSTRE");        
        cmb_categoria_producto.setItems(FXCollections.observableArrayList(listaCategoria));        
    }     
}
