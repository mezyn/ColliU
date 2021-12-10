module com.colliu.colliu {
  requires javafx.controls;
  requires javafx.fxml;
  requires gson;


  opens com.colliu.colliu to javafx.fxml;
  exports com.colliu.colliu;
}