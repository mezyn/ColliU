package miscellaneous;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.awt.event.MouseEvent;

public class Handle {

	@FXML
	public void btnHoverOn(MouseEvent event) {
		((Button) event.getSource()).setOpacity(0.8);
  }

	public void btnHoverOff(MouseEvent event) {
		((Button) event.getSource()).setOpacity(1);
	}
}
