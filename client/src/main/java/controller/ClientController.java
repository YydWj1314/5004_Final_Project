package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientSendThread;
import utils.ClientMessageBuffer;


public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private String message;
    private ClientSendThread clientSendThread;

    public ClientController(String message, ClientSendThread clientSendThread) {
        this.message = message;
        this.clientSendThread = clientSendThread;
        startMessageThread();



    }

    /**
     * Starting message thread and polling messages
     */
    private void startMessageThread() {
        // Starting Message thread and taking messages
        new Thread(() -> {
            log.info("ClientController Listening for Messages...");
            while (true) {
                ClientMessageBuffer.MessageEntry entry = ClientMessageBuffer.takeMessage();
                if (entry != null) {
                    log.info("CC has taken message: {}", entry.message);
                    // Handling messages
                    handleMessage(entry.message);
                }
            }
        }).start();
    }

    private void handleMessage(String message) {
        System.out.println("======= handleMessage =======");

        log.info("CC starts processing message: {}", message);

    }


}
