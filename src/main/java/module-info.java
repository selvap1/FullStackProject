module edu.farmingdale.fullstackproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.farmingdale.fullstackproject to javafx.fxml;
    exports edu.farmingdale.fullstackproject;
}