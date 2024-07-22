module com.jamesgardner.fileorganiser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.jamesgardner.fileorganiser to javafx.fxml;
    exports com.jamesgardner.fileorganiser;
}