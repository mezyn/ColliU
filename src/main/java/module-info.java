module com.colliu.colliu {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.google.gson;
  requires java.desktop;

  opens com.colliu.colliu.controllers to javafx.fxml;
  opens com.colliu.colliu to javafx.fxml;
  opens user to com.google.gson;
  opens event to com.google.gson;
  opens miscellaneous to com.google.gson;

  exports com.colliu.colliu;
  exports user;
  exports event;
}
