module org.BookRecommender {
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;
    requires javafx.fxml;
    requires java.desktop;

    opens org.BookRecommender to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.BookRecommender;
    exports org.BookRecommender.Controller;
    opens org.BookRecommender.Controller to javafx.fxml;
    exports org.BookRecommender.Controller.dettagliLibro;
    opens org.BookRecommender.Controller.dettagliLibro to javafx.fxml;
    exports org.BookRecommender.Controller.reviews;
    opens org.BookRecommender.Controller.reviews to javafx.fxml;
    exports org.BookRecommender.Controller.library;
    opens org.BookRecommender.Controller.library to javafx.fxml;
}