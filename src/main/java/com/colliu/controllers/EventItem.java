package com.colliu.controllers;

import com.colliu.PageController;
import com.colliu.event.Event;
import java.time.LocalDate;
import java.util.ArrayList;
import com.colliu.user.Staff;
import com.colliu.user.UserMethods;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import com.colliu.miscellaneous.Info;
import com.colliu.miscellaneous.Style;
import com.colliu.user.User;


/**
 * This is the controller for each event's UI.
 * It lets the user manipulate information in the event object.
 */
public class EventItem {
  private double fullHeight; // The full height when extra details are not hidden.
  private double detailsHeight; // The height of the details that will be hidden on load.
  private boolean attending;
  private Event event;
  private SequentialTransition emojiAnimation;
  private SequentialTransition activeReaction;
  private ImageView activeReactionImage;
  private ImageView imgUserReaction;
  private PageController master;
  private User currentUser;
  private UserMethods userMethods;

  @FXML
  private Label lblAttendees;

  @FXML
  private Label lblDate;

  @FXML
  private Label lblLocation;

  @FXML
  private Label lblTime;

  @FXML
  private Label lblTitle;

  @FXML
  private TextArea txtDescription;

  @FXML
  private Button btnShowDetails;

  @FXML
  private Pane pnEventDetails;

  @FXML
  private StackPane spAllEvent;

  @FXML
  private ToggleButton btnAttend;

  @FXML
  private ImageView imgReactionOne;

  @FXML
  private ImageView imgReactionTwo;

  @FXML
  private ImageView imgReactionThree;

  @FXML
  private ImageView imgReactionFour;

  @FXML
  private Label lblReactions;

  @FXML
  private VBox vbReactions;

  @FXML
  private StackPane stpReactions;

  @FXML
  private StackPane stpAttendees;

  @FXML
  private VBox vbAttendees;


  /**
   * Toggles setAttending method with opposite of current status.
   */

  @FXML
 private void attendEvent() {
    setAttending(!attending);
    master.saveEvents();
    addAttendees();
  }

  /**
   * This method spins the reaction image when the user hovers over it.
   - @Param event is to identify which emoji that triggered the event.
   */

  @FXML
  private void onEmojiMouseEntered(MouseEvent event) {
    // Gets the image that is hovered
    ImageView reaction = ((ImageView) event.getSource());
    spinEmoji(reaction);
  }

  /**
   * This method stops the reaction image to spin if it is not hovered over again.
   -@param event identifies which emoji triggered the event.
   */
  @FXML
  private void onEmojiMouseExited(MouseEvent event) {
    // Get image that was hovered
    ImageView reaction = ((ImageView) event.getSource());
    stopEmoji(reaction);
  }

  /**
   * Increases the height of the stack-pane to display more information-
   * in the loaded event_design.fxml.
  */
  @FXML
  private void showDetails() {
    boolean showDetails = !(pnEventDetails.isVisible());
    spAllEvent.setMaxHeight((showDetails ? fullHeight : fullHeight - detailsHeight));
    btnShowDetails.setText((showDetails ? "Hide Details" : "Show Details"));
    pnEventDetails.setVisible(showDetails);
  }

  /**
   * Placing the mouse on top of a button triggers this event.
   * Then change the opacity of the button/togglebutton.
   - @param event is helping identify which button was hovered.
   */
  @FXML
  private void buttonOnMouseEnter(MouseEvent event) {
    if (event.getSource() instanceof Button) {
      ((Button) event.getSource()).setOpacity(0.8);
    } else {
      ((ToggleButton) event.getSource()).setOpacity(0.8);
    }
  }

  /**
   * Resets the opacity of the Button that was hovered.
   - @param event identifies which button is no longer hovered.
   */

  @FXML
  private void buttonOnMouseExited(MouseEvent event) {
    if (event.getSource() instanceof Button) {
      ((Button) event.getSource()).setOpacity(1);
    } else {
      ((ToggleButton) event.getSource()).setOpacity(1);
    }
  }

  /**
   * When clicking an emoji add this emoji to the event's reactiona arraylist.
   * Also starts an animation to help the user keep track of which reaction they pressed.
   - @param event identifies which of the 4 emojis were clicked on.
   */

  @FXML
  private void onEmojiClicked(MouseEvent event) {
    ImageView reactionImage = ((ImageView) event.getSource());
    String userEmail = currentUser.getEmail();
    String name = currentUser.getFirstName() + " " + currentUser.getLastName();

    int reaction;
    if (reactionImage.equals(imgReactionOne)) {
      reaction = 1;
    } else if (reactionImage.equals(imgReactionTwo)) {
      reaction = 2;
    } else if (reactionImage.equals(imgReactionThree)) {
      reaction = 3;
    } else {
      reaction = 4;
    }

    if (activeReaction != null) { // If there's an ongoing animation stop it
      stopEmoji(activeReactionImage);
    }

    // Add, remove or modify reaction to the event.
    this.event.addReaction(userEmail, reaction, name);
    // Update the list of reactions.
    addReactions();
    // Save event data.
    master.saveEvents();
  }

  /**
   * Display either who is attending the event or what reactions have been pressed.
   - @param event helps idenitfy if the user wants to display reactions/attending list.
   */

  @FXML
  private void showInfo(MouseEvent event) {
    // If hovering the reaction counter/reaction list
    if (event.getSource() == lblReactions || event.getSource() == stpReactions) {
      stpReactions.setVisible(true);
    } else { // The user clicked attending label or hovered Attending list.
      stpAttendees.setVisible(true);
    }
  }

  /**
   * This method hides the popup of who is attending/has reactied to an event.
   - @param event helps identify which info to hide.
   */
  @FXML
  private void closeInfo(MouseEvent event) {
    // If user is no longer hovering the reaction counter or list of reactions.
    if (event.getSource() == lblReactions || event.getSource() == stpReactions) {
      stpReactions.setVisible(false);
    } else { // If the user clicked on attending label again.
      stpAttendees.setVisible(false);
    }
  }

  /**
   * When user clicks on the attendees button.
   */
  @FXML
  private void toggleAttend() {
    stpAttendees.setVisible(!stpAttendees.isVisible());
  }

  /*
  *******************
  * FUNCTIONALITY
  *******************
   */

  /**
   * Sets all required information for the event loaded.
   - @param event Is a copy of the event object.
   */

  public void load(Event event) {
    // Sets a reference to the logged in user.
    currentUser = master.getCurrentUser();
    userMethods = master.getUserReference();
    setEvent(event);
    setEventInfo();
    addReactions();
    addAttendees();
  }

  /**
   * All visuals from the event object displayed in the ui.
   */
  private void setEventInfo() {
    String title = event.getTitle();
    this.lblTitle.setText(title);

    // Grabs only the date value DD/MM/YYYY from LocalDate.
    String date = String.valueOf(event.getDate()).substring(0, 10);
    this.lblDate.setText(date);

    String location = event.getLocation();
    this.lblLocation.setText(location);

    String time = event.getTime();
    this.lblTime.setText(time);

    // Displays the number of attendees.
    String attendees = String.valueOf(event.getAttending().size());
    this.lblAttendees.setText(attendees);

    String details = event.getDescription();
    this.txtDescription.setText(details);

    // Get full height of event and only detail's height.
    fullHeight = spAllEvent.getPrefHeight();
    detailsHeight = pnEventDetails.getPrefHeight();

    // Remove event detail's height from the event.
    double smallHeight = fullHeight - detailsHeight;
    spAllEvent.setMaxHeight(smallHeight);

    // Controlling functionality of event buttons
    boolean eventPassed = event.getDate().isBefore(LocalDate.now());
    btnAttend.setDisable(eventPassed);
    imgReactionOne.setDisable(eventPassed);
    imgReactionTwo.setDisable(eventPassed);
    imgReactionThree.setDisable(eventPassed);
    imgReactionFour.setDisable(eventPassed);

    // Staff cannot attend their own events.
    boolean isStudent = !(currentUser instanceof Staff);
    btnAttend.setVisible(isStudent);

    String userEmail = currentUser.getEmail();
    attending = event.getAttending().contains(userEmail);

    // Toggles the attend button if user is already attending the event.
    setAttending(attending);

    // Hides the detailed info.
    pnEventDetails.setVisible(false);
  }

  /**
   * Assigns the mastercontroller reference to already created object.
   - @param master is the reference to our mastercontroller.
   */

  public void setMaster(PageController master) {
    this.master = master;
  }

  /**
   * Sets a reference to the event that is loaded into our controller.
   - @param event The event that is displayed to the user.
   */

  private void setEvent(Event event) {
    this.event = event;
  }

  /**
   * Updates the attend button with styling depending on if user is attending or not.
   - @param status is the new status of the button "attend"/"cancel".
   */

  private void setAttending(boolean status) {
    this.attending = status;
    String userEmail = currentUser.getEmail();
    if (attending) {
      event.addAttendee(userEmail);
      btnAttend.setStyle(Style.BUTTON_RED);
      btnAttend.setText(Info.CANCEL);
    } else {
      event.delAttendee(userEmail);
      btnAttend.setStyle(Style.BUTTON_GREEN);
      btnAttend.setText(Info.ATTEND);
    }
  }

  /**
   * Sets the reaction counter to number of reactions in the arraylist.
   * Then loads the list of all reactions.
   */
  private void addReactions() {
    int reactions = event.getReactions().size();
    lblReactions.setText("" + reactions);
    loadReactionList();
  }

  /**
   * Loads all the reactions to this event.
   */
  private void loadReactionList() {
    // Retrieve all reactions in a sorted ArrayList.
    ArrayList<String[]> reactions = sortReactions(event.getReactions());
    int size = reactions.size();
    // when this method is called we need to stop the ongoing animations.
    // to prevent multiple emojis being animated at once.

    // If there's an ongoing indefinite reaction, stop it.
    if (activeReaction != null) {
      activeReaction.stop();
      emojiAnimation.stop();
    }

    // If user has reacted to the event imgUserReaction will be set method.
    if (imgUserReaction != null && imgUserReaction != activeReactionImage) {
      // Stop the animation.
      stopEmoji(imgUserReaction);
      // create a new animation and play it
      activeReaction = spinEmoji(imgUserReaction);
      activeReaction.play();
      // assign the active emoji to a global variable for toggle controlling
      activeReactionImage = imgUserReaction;
    }

    // Create a new array of nodes with either 2 slots or the size of arraylist.
    Node[] reactionList = new Node[(size == 0 ? 1 : size)];

    // Loop through all the reactions if any
    for (int i = 0; i < size; i++) {
      String name = reactions.get(i)[2];
      String img = reactions.get(i)[1];
      reactionList[i] = infoPane(name, img, false);
    }

    // If there are no reactions we let the user know
    // If there's only one reaction we'll add an empty one to make colors look good.
    if (size == 0) {
      final boolean empty = true;

      reactionList[0] = infoPane(Info.EMPTY_REACTIONS, null, empty);
    }

    vbReactions.setSpacing(2);
    vbReactions.getChildren().setAll(reactionList);
    int height = size > 2 ? (size > 5 ? 5 * 20 : size * 20) : 40;
    double layoutY = 100 - height;
    stpReactions.setLayoutY(layoutY);
  }

  /**
   * This method will first loop through 1-4 (The reactions available)
   * It will in each iteration loop through all the reactions
   * It will then check if the reaction of the event is the same as the reaction we're
   * -checking(1 through 4).
   * If it is a match it will add that reaction to another arraylist and go to the next one.
   * Thus, we are sorting them based on reaction 1, 2, 3 and 4.
   -@param reactions Is the list of reactions that are sorted.
   */

  private ArrayList<String[]> sortReactions(ArrayList<String[]> reactions) {
    ArrayList<String[]> sortedReactions = new ArrayList<>();
    String userEmail = currentUser.getEmail();
    for (int i = 1; i < 5; i++) {
      for (String[] reaction : reactions) {
        switch (i) {
          case 1:
            if (reaction[1].equals(Info.REACTION_SMILE)) {
              sortedReactions.add(reaction);
              if (reaction[0].equals(userEmail)) {
                imgUserReaction = imgReactionOne;
              }
            }
            break;
          case 2:
            if (reaction[1].equals(Info.REACTION_HAPPY)) {
              sortedReactions.add(reaction);
              if (reaction[0].equals(userEmail)) {
                imgUserReaction = imgReactionTwo;
              }
            }
            break;
          case 3:
            if (reaction[1].equals(Info.REACTION_SHOCK)) {
              sortedReactions.add(reaction);
              if (reaction[0].equals(userEmail)) {
                imgUserReaction = imgReactionThree;
              }
            }
            break;
          default:
            if (reaction[1].equals(Info.REACTION_LOVE)) {
              sortedReactions.add(reaction);
              if (reaction[0].equals(userEmail)) {
                imgUserReaction = imgReactionFour;
              }
            }
        }
      }
    }
    return sortedReactions;
  }

  /**
   * Display the size of users attending this event.
   */

  private void addAttendees() {
    String attendees = "" + event.getAttending().size();
    lblAttendees.setText(attendees);
    loadAttendeesList();
  }

  /**
   * Loads all names into a VBox of panes with the student's names who are attending the event.
   */

  private void loadAttendeesList() {
    // Retrieve all attendees for event
    ArrayList<String> attendees = event.getAttending();
    int size = attendees.size();
    Node[] attendeeList = new Node[(size > 0 ? size : 1)];
    for (int i = 0; i < size; i++) {
      String name = userMethods.getUserByEmail(attendees.get(i)).toString();
      attendeeList[i] = infoPane(name, Info.REACTION_BLANK, false);
    }
    if (size == 0) {
      attendeeList[0] = infoPane(Info.EMPTY_ATTENDEES,  Info.REACTION_BLANK,  true);
    }

    vbAttendees.setSpacing(2);
    vbAttendees.getChildren().setAll(attendeeList);
    int height = size > 2 ? (size > 15 ? 15 * 14 : size * 14) : 0;
    double layoutY = 210 - height;
    stpAttendees.setLayoutY(layoutY);
  }

  /**
   * Creates a pane for displaying reactions/attending information.
   - @param text The name/text to display.
   - @param react The reaction image to dispaly
   - @param empty If there are no reactions or attendees
   - @return A custom created pane containing the information to display.
   */

  private Pane infoPane(String text, String react, boolean empty) {
    Pane pnReaction = new Pane();
    pnReaction.setStyle(Style.BACKGROUND_WHITE);
    Label lblReactionText = new Label(text);

    if (empty) {
      lblReactionText.setStyle(Style.LABEL_NORMAL_FAT);
      lblReactionText.setPrefWidth(150);
      lblReactionText.setAlignment(Pos.TOP_CENTER);
    } else {
      String img = switch (react) {
        case Info.REACTION_SMILE ->
            String.valueOf(PageController.class.getResource(Info.RESOURCE_IMAGE_SMILE));
        case Info.REACTION_HAPPY ->
            String.valueOf(PageController.class.getResource(Info.RESOURCE_IMAGE_HAPPY));
        case Info.REACTION_SHOCK ->
            String.valueOf(PageController.class.getResource(Info.RESOURCE_IMAGE_SHOCK));
        default ->
            String.valueOf(PageController.class.getResource(Info.RESOURCE_IMAGE_LOVE));
      };

      ImageView ivEmoji = new ImageView(img);
      ivEmoji.maxWidth(16);
      ivEmoji.maxHeight(16);
      ivEmoji.setFitHeight(16);
      ivEmoji.setFitWidth(16);
      ivEmoji.setLayoutX(5);
      lblReactionText.setLayoutX(26);
      pnReaction.getChildren().add(ivEmoji);
    }
    pnReaction.getChildren().add(lblReactionText);
    return pnReaction;
  }

  /**
   * Creates an animation on the emojis and starts it.
   - @param emoji the image to apply the animation on.
   - @return a copy of the sequentialtransition/animation for toggling purposes.
   */

  private SequentialTransition spinEmoji(ImageView emoji) {
    if (activeReaction != null) {
      stopEmoji(imgUserReaction);
    }
    // Our animation for rotating picture left
    RotateTransition rotateLeft = new RotateTransition(Duration.millis(100), emoji);
    rotateLeft.setByAngle(15); // Rotate 15 degrees to left
    rotateLeft.setCycleCount(1); // Rotate once
    rotateLeft.setInterpolator(Interpolator.LINEAR);

    // Our animation for rotating picture right
    RotateTransition rotateRight = new RotateTransition(Duration.millis(100), emoji);
    rotateRight.setByAngle(-30); // Rotates 30 degrees to the right(15 degrees from 0)
    rotateRight.setCycleCount(1); // Rotate once
    rotateRight.setInterpolator(Interpolator.LINEAR);

    // Slight pause in animation for smoothness
    RotateTransition rotatePause = new RotateTransition(Duration.millis(1000), emoji);
    rotatePause.setByAngle(0); // Does not rotate at all
    rotateRight.setCycleCount(1); // Pauses only once

    // Create a new rotation animation, play it once and make it return to 0 degrees angle.
    RotateTransition evenOut = new RotateTransition(Duration.millis(100), emoji);
    evenOut.setByAngle(15);
    evenOut.setCycleCount(1);
    evenOut.setInterpolator(Interpolator.LINEAR);

    // First rotate left, then right, then pause
    SequentialTransition emojiSequence = new SequentialTransition(rotateLeft, rotateRight, evenOut);
    emojiSequence.setCycleCount(3); // rotate 3 times then pause
    emojiAnimation = new SequentialTransition(emojiSequence, rotatePause);
    emojiAnimation.setCycleCount(Timeline.INDEFINITE);
    SequentialTransition copyEmojiAnimation = emojiAnimation;
    emojiAnimation.play(); // Start sequence.
    return copyEmojiAnimation;
  }

  /**
   * Stop the if any ongoing reaction on the emoji requested.
   - @param image the emoji which animation should be stopped.
   */
  private void stopEmoji(ImageView image) {
    if (emojiAnimation != null && emojiAnimation != activeReaction) {
      // Stop the sequence of rotations
      emojiAnimation.stop();
    }
    // Get image's current rotation
    double yx = image.getLocalToSceneTransform().getMyx();
    double yy = image.getLocalToSceneTransform().getMyy();

    // Then calculate the angle that needs to be reverse for rotation
    double angle = -(Math.atan2(yx, yy) * 100) / 2;

    // Create a new rotation animation, play it once and make it return to 0 degrees angle.
    RotateTransition evenOut = new RotateTransition(Duration.millis(10), image);
    evenOut.setByAngle(angle);
    evenOut.setCycleCount(1);
    evenOut.setInterpolator(Interpolator.LINEAR);
    evenOut.play();
  }

}