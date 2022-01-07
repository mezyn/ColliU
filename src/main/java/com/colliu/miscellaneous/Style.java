package com.colliu.miscellaneous;

/**
 * This is a static class for easily setting different CSS styles.
 * The group did not have time to learn CSS implementation in FXML(using stylesheets).
 * So we resorted to inline CSS for FXML and then this external class working as "classes"-
 * that we can add dynamically.
 */

public class Style {
  public static final String BUTTON_GREEN = "-fx-background-color: rgb(192,236,204);";
  public static final String BUTTON_RED = "-fx-background-color:rgb(246, 168, 166);";
  public static final String BUTTON_NORMAL = "-fx-background-color:#DDD;";

  public static final String LABEL_GREEN = "-fx-color: rgb(192,236,204);";
  public static final String LABEL_RED = "-fx-color: rgb(246, 168, 166);";
  public static final String LABEL_NORMAL = "-fx-color: #333;";

  public static final String LABEL_GREEN_FAT = LABEL_GREEN + "-fx-font-weight: bold;";
  public static final String LABEL_RED_FAT = LABEL_RED + "-fx-font-weight:bold;";
  public static final String LABEL_NORMAL_FAT = LABEL_NORMAL + "-fx-font-weight:bold;";

  public static final String TEXTFIELD_GREEN = "-fx-background-color:#FFF; -fx-border-color:rgb(192,236,204); -fx-border-radius: 5; -fx-text-fill:#333;";
  public static final String TEXTFIELD_RED = "-fx-background-color:#FFF; -fx-border-color:rgb(246, 168, 166); -fx-border-radius: 5; -fx-text-fill:#333;";
  public static final String TEXTFIELD_NORMAL = "-fx-background-color:#FFF; -fx-border-color:#DDD; -fx-border-radius: 5; -fx-text-fill:#333;";

  public static final String TOGGLESWITCH = "-fx-border-radius:15;";
  public static final String BACKGROUND_BLUE = "-fx-background-color:  rgb(165,200,228);";
  public static final String BACKGROUND_LIGHTGRAY = "-fx-background-color: #FAFAFA;";
  public static final String BACKGROUND_NONE = "-fx-background: none;";
  public static final String BACKGROUND_WHITE = "-fx-background: #FFF;";
}
