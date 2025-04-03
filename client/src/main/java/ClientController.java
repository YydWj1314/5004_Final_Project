import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientSendThread;

public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private String message;
    private ClientSendThread clientSendThread;

    public ClientController(String message, ClientSendThread clientSendThread) {
        this.message = message;
        this.clientSendThread = clientSendThread;

    }
}
