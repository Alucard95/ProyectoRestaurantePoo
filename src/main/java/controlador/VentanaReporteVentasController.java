package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.ReporteVentas;

public class VentanaReporteVentasController implements Initializable {

    @FXML
    private TextField txt_FechaInicio;
    @FXML
    private TextField txt_FechaFin;
    @FXML
    private TableView<TablaReporte> tbl_Reportes;
    @FXML
    private Label lbl_lista_reportes;        
    
    ArrayList<ReporteVentas> reportes;
    private ObservableList<TablaReporte> reportList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TablaReporte,String> tbl_col_fecha;
    @FXML
    private TableColumn<TablaReporte,String> tbl_col_mesa;
    @FXML
    private TableColumn<TablaReporte,String> tbl_col_mesero;
    @FXML
    private TableColumn<TablaReporte,String> tbl_col_cuenta;
    @FXML
    private TableColumn<TablaReporte,String> tbl_col_cliente;
    @FXML
    private TableColumn<TablaReporte,String> tbl_col_total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cargar el archivo por defecto          
        cargarFormatoTabla();         
    }    

    @FXML
    private void consultarReportes(ActionEvent event) 
    {
        String fechaInicial = txt_FechaInicio.getText().trim();
        String fechaFinal = txt_FechaFin.getText().trim();
        if(fechaInicial.isEmpty() || fechaFinal.isEmpty())
            Helper.mostrarMensaje(new Alert(Alert.AlertType.WARNING),"Consulta de reportes",null,"Debe registrar la fecha inicial y final!");
        else            
        {
            tbl_Reportes.getItems().clear();
            Date fechaIn = Helper.StringToDate(fechaInicial);
            Date fechaFin = Helper.StringToDate(fechaFinal);
            reportes = Helper.consultarReportesFecha(fechaIn,fechaFin);
            reportes.sort(new OrdenadorFecha());
            String fecha,mesa,mesero,numCuenta,cliente,total;            
            for(ReporteVentas reporte : reportes)
            {            
                fecha = Helper.dateTimeToString(reporte.getFechaPedido());
                mesa = String.valueOf(reporte.getNumeroMesa());
                mesero = String.valueOf(reporte.getMesero());
                numCuenta = String.valueOf(reporte.getNumeroCuenta());
                cliente = reporte.getCliente();
                total = String.valueOf(reporte.getTotal());
                reportList.add(new TablaReporte(fecha,mesa,mesero,numCuenta, cliente,total));
            }           
            tbl_Reportes.setItems(reportList);
        }
    }

    @FXML
    private void accionRegresar(ActionEvent event) throws IOException
    {
        Stage stageActual = (Stage) txt_FechaInicio.getScene().getWindow();            
        stageActual.close();
    }
    
    public void cargarReporteArchivo()
    {
        reportes = ReporteVentas.desserializarReporte("Reporte.ser");        
        String fecha,mesa,mesero,numCuenta,cliente,total;  
        for(ReporteVentas reporte : reportes)
        {            
            fecha = Helper.dateTimeToString(reporte.getFechaPedido());
            mesa = String.valueOf(reporte.getNumeroMesa());
            mesero = String.valueOf(reporte.getMesero());
            numCuenta = String.valueOf(reporte.getNumeroCuenta());
            cliente = reporte.getCliente();
            total = String.valueOf(reporte.getTotal());
            reportList.add(new TablaReporte(fecha,mesa,mesero,numCuenta, cliente,total));
        }
        tbl_Reportes.setItems(reportList);
    }
    
    public void cargarFormatoTabla()
    {
        cargarReporteArchivo();
        tbl_col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));        
        tbl_col_mesa.setCellValueFactory(new PropertyValueFactory<>("mesa"));
        tbl_col_mesero.setCellValueFactory(new PropertyValueFactory<>("mesero"));
        tbl_col_cuenta.setCellValueFactory(new PropertyValueFactory<>("numCuenta"));
        tbl_col_cliente.setCellValueFactory(new PropertyValueFactory<>("cliente")); 
        tbl_col_total.setCellValueFactory(new PropertyValueFactory<>("total")); 
        tbl_Reportes.setItems(reportList);
    }
    
}
