module com.example.prod {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prod to javafx.fxml;
    exports com.example.prod;
}