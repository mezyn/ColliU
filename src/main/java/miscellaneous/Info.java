package miscellaneous;

public class Info {
    public static final String INCORRECT_PASSWORD = "Invalid password.";
    public static final String INCORRECT_EMAIL = "Email doesn't match any users.";
    public static final String INCORRECT_LOGIN = "Email or Password is incorrect.";
    public static final String FAIL_SAVE_USERS = "Could not save users to: Students.JSON and/or Staff.JSON.";
    public static final String FAIL_SAVE_EVENTS = "Could not save events to: Event.JSON.";
    public static final String NO_USER_FILE = "Missing file(s): Student.JSON or Staff.JSON in documents/ColliU/ directory.";
    public static final String USER_FILE_CORRUPT = "User file(s): Student.JSON or Staff.JSON are corrupt and can not be loaded.";
    public static final String CANT_PROMOTE_STUDENT = "An error was caught when promoting user: ";
    public static final String CANT_DEMOTE_ADMIN = "An error was caught when demoting user: ";
    public static final String CANT_CHANGE_STATUS ="An error was caught when changing user's status: ";
    public static final String CANT_CREATE_STAFF = "An error was caught when creating new staff: ";
    public static final String CANT_CREATE_STUDENT = "An error was caught when creating new student: ";
    public static final String NO_EVENT_FILE = "Missing file: Event.JSON in documents/ColliU/ directory." + System.lineSeparator() + "A blank Event file is loaded. All previous events are lost.";
    public static final String EVENT_FILE_CORRUPT = "Event file: Event.JSON are corrupt and can not be loaded." + System.lineSeparator() + "A blank Event file is loaded. All previous events are lost.";
    public static final String CANT_REMOVE_USER = "An error was caught when trying to remove a user: ";
    public static final String ATTEND = "Attend";
    public static final String CANCEL = "Cancel";
    public static final String DELETE = "Delete";
    public static final String UNDO = "Undo";
    public static final String PROMOTE = "Promote";
    public static final String DEMOTE = "Demote";
    public static final String SEM = "Software engineering and management";
    public static final String KOG = "Cognitive science";
    public static final String SVET = "Systems science";
    public static final String DVET = "Computer science";
    public static final int UPCOMING_EVENTS = 1;
    public static final int PAST_EVENTS = 2;
    public static final int ATTENDING_EVENTS = 3;

    public static final String ERROR_PAGE = "ErrorPage.fxml";
    public static final String ACCOUNT_SETTINGS = "newAccountSettings.fxml";
    public static final String EVENT_CREATION = "create-event.fxml";
    public static final String HOMEPAGE = "EventPage.fxml";
    public static final String LOGIN = "LoginPage.fxml";
    public static final String REGISTER_STAFF = "staff-registration.fxml";
    public static final String REGISTER_STUDENT = "student-registration.fxml";
}
