module com.project.proyectorestaurante {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.project.proyectorestaurante to javafx.fxml;
    exports com.project.proyectorestaurante;
}