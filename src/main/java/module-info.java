module com.alfonsopb.puretask {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.alfonsopb.puretask to javafx.fxml;
    exports com.alfonsopb.puretask;
}
