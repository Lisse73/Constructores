module Constructores {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 

   
    exports application to javafx.graphics, javafx.fxml;

    
    opens application to javafx.base, javafx.graphics, javafx.fxml;
}
