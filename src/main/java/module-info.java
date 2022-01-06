module com.colliu.colliu {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.google.gson;
  requires java.desktop;
    requires org.controlsfx.controls;
  requires org.apache.commons.io;

  opens com.colliu.controllers to javafx.fxml;

  opens com.colliu to javafx.fxml;

  opens com.colliu.user to com.google.gson;
  opens com.colliu.event to com.google.gson;
  opens com.colliu.miscellaneous to com.google.gson, javafx.fxml;

  exports com.colliu;
  exports com.colliu.user;
  exports com.colliu.event;
}
