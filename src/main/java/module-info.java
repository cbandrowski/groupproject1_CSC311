module org.example.groupproject1_csc311 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.groupproject1_csc311 to javafx.fxml;
    exports org.example.groupproject1_csc311;
}