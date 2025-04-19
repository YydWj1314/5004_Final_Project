import javax.swing.SwingUtilities;
import view.LoginFrame;
import view.MainFrame;

/**
 * ClientApp is the main entry point for the client application.
 * <p>
 * This class contains the main method that initializes and launches
 * the client application, starting with the login screen.
 */
public class ClientApp {

  /**
   * The main method that starts the application.
   * <p>
   * Creates and displays the login frame, which is the first screen
   * users see when starting the application. The login frame will
   * handle user authentication and transition to the main game screen
   * after successful login.
   *
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
    new LoginFrame();
  }
}
