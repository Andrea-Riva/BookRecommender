module org.BookRecommender {
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;
    requires javafx.fxml;
    requires java.desktop;
    opens org.BookRecommender to javafx.fxml;
    exports org.BookRecommender;
    exports org.BookRecommender.controllers;
    opens org.BookRecommender.controllers to javafx.fxml;
}