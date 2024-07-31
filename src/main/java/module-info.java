module com.jamesgardner.fileorganiser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.jamesgardner.fileorganiser to javafx.fxml;
    exports com.jamesgardner.fileorganiser;
    exports com.jamesgardner.fileorganiser.enums;
    opens com.jamesgardner.fileorganiser.enums to javafx.fxml;
}