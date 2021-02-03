package controlador;

import com.project.proyectorestaurante.App;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import modelo.Administrador;
import modelo.CabeceraPedido;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Disponibilidad;
import modelo.Empleado;
import modelo.Mesa;
import modelo.Mesero;
import modelo.ReporteVentas;
import modelo.TipoEmpleado;

public class VentanaMapaRestauranteController implements Initializable {

    @FXML
    private Rectangle rect_Informacion;
    @FXML
    private Label lbl_numeroComensales;
    @FXML
    private Label lbl_totalFacturado;
    @FXML
    private Pane pane_restaurante;

    Mesero mesero;
    Administrador administrador;
    Empleado empleado;
    ArrayList<Mesa> mesas;
    ArrayList<ReporteVentas> reportes;
    int cantComensales = 0;
    double totalRecaudado = 0;

    boolean monitorearMapa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarPanelRestaurante(Empleado emp, boolean monitoreo) {
        pane_restaurante.setVisible(true);
        this.monitorearMapa = monitoreo;
        this.empleado = emp;
        cargarMesasExistentes();
        if (!monitorearMapa) {
            lbl_numeroComensales.setVisible(false);
            lbl_totalFacturado.setVisible(false);
            rect_Informacion.setVisible(false);

            if (empleado.getTipoEmpleado().equals(TipoEmpleado.ADMINISTRADOR)) {
                administrador = Helper.convertirEmpleadoAAdministrador(empleado);
                cargarPaneListener();
            } else {
                mesero = Helper.convertirEmpleadoAMesero(emp);
            }
        } else {
            lbl_numeroComensales.setVisible(true);
            lbl_totalFacturado.setVisible(true);
            rect_Informacion.setVisible(true);
            ejecutarHilo(this::mostrarInformacionMesas, true);
        }
    }

    private void ejecutarHilo(Runnable r, boolean daemon) {
        Thread t = new Thread(r);
        if (daemon) {
            t.setDaemon(true);
        }
        t.start();
    }

    public void mostrarInformacionMesas() {
        for (Mesa mesa : mesas) {
            if (!mesa.getMesero().getUsuario().isEmpty()) {
                cantComensales += 1;
            }
        }
        reportes = ReporteVentas.desserializarReporte("Reporte.ser");
        for (ReporteVentas rep : reportes) {
            totalRecaudado += rep.getTotal();
        }
        Platform.runLater(() -> mostrarValores(cantComensales, totalRecaudado));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    public void mostrarValores(int comensales, double total) {
        lbl_numeroComensales.setText("Número de Comensales: " + comensales);
        lbl_totalFacturado.setText("$" + total);
    }

    public void cargarMesasExistentes() {
        pane_restaurante.getChildren().clear();
        mesas = Helper.obtenerMesasExistentes();
        for (Mesa mesa : mesas) {
            System.out.println("Existen mesas ");
            int numMesa = mesa.getNumero();
            int capacidad = mesa.getCapacidad();
            double x = mesa.getCoordenadaX();
            double y = mesa.getCoordenadaY();
            cargarDibujoMesa(numMesa, capacidad, x, y);
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
    }

    public void cargarDibujoMesa(int numMesa, int capacidad, double x, double y) {
        //Dibujando la mesa
        String descripcionMesa = "Mesa " + numMesa + "\nCapacidad " + capacidad;
        Text descripcion = new Text(descripcionMesa);
        Circle circle = crearEstructuraMesa(descripcion, numMesa);
        StackPane layout = new StackPane();
        layout.getChildren().addAll(
                circle,
                descripcion
        );
        layout.setTranslateX(x);
        layout.setTranslateY(y);
        layout.setPadding(new Insets(20));
        layout.setId(String.valueOf(numMesa));
        layout.setOnMouseClicked(e
                -> {
            if (e.getButton() == MouseButton.PRIMARY && !monitorearMapa) {
                //Acciones Boton Principal
                //Administrador                                 
                if (empleado.getTipoEmpleado().equals(TipoEmpleado.ADMINISTRADOR)) {
                    actualizarMesa(layout.getId(), x, y);
                } //Mesero
                //Si la mesa es del mesero se abre para tomar pedido
                //Si la mesa es de otro mesero no puede tomar pedido y se muestra mensaje de error
                else if (empleado.getTipoEmpleado().equals(TipoEmpleado.MESERO)) {
                    mesero = Helper.convertirEmpleadoAMesero(empleado);
                    System.out.println("mesero=>" + mesero.getUsuario());
                    if (Helper.verificarMeseroEnMesa(mesero, numMesa)) {
                        //Abrir pedido existente o crear pedido
                        aperturarPedido(Helper.validarNumeroEntero(layout.getId()));
                    } else {
                        Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Apertura de Pedido ", null, "No se puede aperturar el pedido porque pertenece a otro mesero!");
                    }
                }
            } else if (e.getButton() == MouseButton.SECONDARY && !monitorearMapa) {
                //Elimina la mesa 
                if (empleado.getTipoEmpleado().equals(TipoEmpleado.ADMINISTRADOR)) {
                    Mesa mesa = Helper.obtenerMesa(Helper.validarNumeroEntero(layout.getId()));
                    if (mesa != null && !mesa.getEstadoDisponible().equals(Disponibilidad.DISPONIBLE)) {
                        Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Eliminacion de Mesa ", null, "No se puede eliminar mesa ocupada!");
                    } else {
                        System.out.println("Se presiono el boton secundario");
                        pane_restaurante.getChildren().remove(layout);
                        eliminarMesa(Helper.validarNumeroEntero(layout.getId()));
                    }
                }
            }

        });
        Platform.runLater(() -> pane_restaurante.getChildren().add(layout));
    }

    public void obtenerEmpleado(Empleado emp) {
        if (emp.getTipoEmpleado().equals(TipoEmpleado.ADMINISTRADOR)) {
            administrador = Helper.convertirEmpleadoAAdministrador(emp);
        } else {
            mesero = Helper.convertirEmpleadoAMesero(emp);
        }
    }

    private Circle crearEstructuraMesa(Text text, int numMesa) {
        obtenerEmpleado(empleado);
        Circle circle = new Circle();
        Color c = Color.rgb(255, 255, 255);
        Mesa mesa = Helper.obtenerMesa(numMesa);
        if (mesa != null) {
            System.out.println("Mesa si existe");
            if (administrador != null) {
                System.out.println("Es administrador");
                switch (mesa.getEstadoDisponible()) {
                    case DISPONIBLE:
                        c = Color.rgb(255, 235, 59);
                        break;

                    default:
                        c = Color.rgb(213, 0, 0);
                        break;

                }
            } else if (mesero != null) {
                System.out.println("Es mesero");
                if (mesa.getEstadoDisponible().equals(Disponibilidad.DISPONIBLE)) {
                    c = Color.rgb(255, 235, 59);
                } else if (mesero.getUsuario().equals(mesa.getMesero().getUsuario())) {
                    c = Color.rgb(129, 199, 132);
                } else {
                    c = Color.rgb(213, 0, 0);
                }
            }
            circle.setFill(c);
            final double PADDING = 20;
            circle.setRadius(obtenerAncho(text) / 2 + PADDING);
        }
        return circle;
    }

    private double obtenerAncho(Text text) {
        new Scene(new Group(text));
        text.applyCss();

        return text.getLayoutBounds().getWidth();
    }

    public void aperturarPedido(int numMesa) {
        //Si la mesa esta disponible se debe abrir para crear cuenta
        Mesa mesa = Helper.obtenerMesa(numMesa);
        if (mesa != null) {
            System.out.println("Mesa Existe y se va a crear pedido abrir el pedido");
            if (mesa.getEstadoDisponible().equals(Disponibilidad.DISPONIBLE)) {
                aperturarCuenta(numMesa, mesero);
            } else {
                System.out.println("Cargar el ultimo pedido");
                //Cargar el ultimo pedido abierto de la mesa de ese mesero                
                CabeceraPedido cab = Helper.getUltimoPedidoAbiertoMesero(numMesa, mesero);
                if (cab != null) {
                    cargarPedido(numMesa, mesero, cab.getCliente(), cab);
                } else {
                    System.out.println("NO existe un pedido actual disponible");
                }
            }
        }
    }

    public void eliminarMesa(int numMesa) {
        try {
            Mesa mesa = Helper.obtenerMesa(numMesa);
            Mesa.eliminarMesa(mesa);
        } catch (Exception e) {

        }
    }

    public void cargarPedido(int numMesa, Mesero mesero, Cliente cliente, CabeceraPedido cab) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("VentanaTomarPedido" + ".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            VentanaTomarPedidoController controlador = fxmlLoader.<VentanaTomarPedidoController>getController();
            controlador.cargarDatosPedido(numMesa, mesero, cliente, cab);
            stage.setTitle("Tomar Pedido");
            stage.setMaxWidth(1000);
            stage.setMaxHeight(600);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {

        }
    }

    public void aperturarCuenta(int numMesa, Mesero mesero) {
        Cuenta cuenta;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Mensaje del Sistema");
        dialog.setHeaderText("Cliente a tomar Pedido");
        dialog.setContentText("Ingrese el nombre del cliente:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        String cliente;
        if (result.isPresent()) {
            cliente = result.get().trim();
            if (cliente.isEmpty()) {
                Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Mensaje de Sistema", "Cliente a tomar Pedido", "Favor ingresar el nombre del cliente!");
            } else {
                try {
                    //Crear cuenta
                    cuenta = new Cuenta(numMesa, mesero);
                    Cuenta.guardarCuenta(cuenta);

                    //Crear Cliente
                    Cliente cli = new Cliente(cliente, cliente);

                    //Reservo mesa
                    Mesa mesac = Helper.obtenerMesa(numMesa);
                    if (mesac != null) {
                        mesac.setMesero(mesero);
                        mesac.setEstadoDisponible(Disponibilidad.OCUPADA_MISMO_MESERO);
                        Mesa.eliminarMesa(mesac);
                        Mesa.actualizarMesa(mesac);
                    }

                    //Creo el pedido
                    cargarPedido(numMesa, mesero, cli, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cargarPaneListener() {
        if (monitorearMapa) {
            return;
        }

        pane_restaurante.setOnMouseClicked(e
                -> {
            //Administrador 
            //Verificar que la mesa  no exista para crear la mesa            
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!Helper.verificarMesaCreada(e.getX(), e.getY())) {
                    cargarMesa(e.getX(), e.getY());
                }
            }
        });
    }

    public void cargarMesa(double x, double y) {
        actualizarMesa("", x, y);
    }

    public void actualizarMesa(String numMesaS, double x, double y) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Editar Mesa");
        dialog.setHeaderText("Favor ingrese el número de la mesa y la capacidad");

        ButtonType aceptarBoton = new ButtonType("SI", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(aceptarBoton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numMesa = new TextField();
        numMesa.setPromptText("");
        TextField capacidadMesa = new TextField();
        numMesa.setPromptText("");

        if (!numMesaS.trim().isEmpty()) {
            Mesa mesa = Helper.obtenerMesa(Helper.validarNumeroEntero(numMesaS));
            if (mesa != null) {
                numMesa.setText(String.valueOf(mesa.getNumero()));
                capacidadMesa.setText(String.valueOf(mesa.getCapacidad()));
            }
        }

        grid.add(new Label("Número de Mesa:"), 0, 0);
        grid.add(numMesa, 1, 0);
        grid.add(new Label("Capacidad de Mesa: "), 0, 1);
        grid.add(capacidadMesa, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> numMesa.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton
                -> {
            if (dialogButton == aceptarBoton) {
                return new Pair<>(numMesa.getText(), capacidadMesa.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(numMesaCapacidad
                -> {
            int mesa = Integer.parseInt(numMesaCapacidad.getKey());
            int capacidad = Integer.parseInt(numMesaCapacidad.getValue());
            if (Helper.obtenerMesa(mesa) != null) {
                Helper.mostrarMensaje(new Alert(Alert.AlertType.ERROR), "Creación de Mesa", null, "La mesa ya existe!");
            } else {
                try {
                    Mesa mesaC;
                    mesero = Helper.convertirEmpleadoAMesero(empleado);
                    System.out.println("mesero a crear mesa=>" + mesero.getUsuario());
                    mesaC = new Mesa(mesa, capacidad, x, y, Disponibilidad.DISPONIBLE, "");
                    if (numMesaS.trim().isEmpty()) {
                        System.out.println("Creando mesa");
                        Mesa.guardarMesa(mesaC);
                        cargarDibujoMesa(mesa, capacidad, x, y);
                    } else {
                        System.out.println("Actualizando mesa");
                        Mesa.eliminarMesa(mesaC);
                        Mesa.actualizarMesa(mesaC);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
