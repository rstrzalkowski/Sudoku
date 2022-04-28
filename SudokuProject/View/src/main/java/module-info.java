module View {
    requires javafx.controls;
    requires javafx.fxml;

    requires Model;
    requires java.desktop;
    requires java.logging;
    requires logback.classic;
    requires org.slf4j;
    opens com.example.view;
}