import javax.swing.SwingUtilities;
import view.LoginFrame;
import view.MainFrame;

public class ClientApp {
    public static void main(String[] args) {

      //SwingUtilities.invokeLater(() -> new MainFrame());
      new LoginFrame();
    }
}
