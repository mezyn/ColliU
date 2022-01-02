module com.colliu.colliu {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.google.gson;
  requires java.desktop;
    requires org.controlsfx.controls;

  opens com.colliu.colliu.controllers to javafx.fxml;

  opens com.colliu.colliu to javafx.fxml;

  opens user to com.google.gson;
  opens event to com.google.gson;
  opens miscellaneous to com.google.gson, javafx.fxml;

  exports com.colliu.colliu;
  exports user;
  exports event;
}
