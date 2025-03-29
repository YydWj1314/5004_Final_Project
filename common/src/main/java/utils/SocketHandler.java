package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHandler {
  public static final Logger log = LoggerFactory.getLogger(SocketHandler.class);
  private Socket socket;

  private DataOutputStream dataOutputStream;

  private DataInputStream dataInputStream;

  /**
   * Constructor of the SocketHandler with given socket
   *
   * @param socket given socket
   */
  public SocketHandler(Socket socket) {
    try {
      this.socket = socket;
      this.dataInputStream = new DataInputStream(socket.getInputStream());
      this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
      log.info("SocketHandler Initialized Successfully: {}", socket.getInetAddress());
    } catch (IOException e) {
      log.error("SocketHandler Initialization Error", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Encapsulating message sending util using UTF-8 encoding
   *
   * @param msg String message to be sent
   */
  public void sendMessage(String msg){
    if(this.socket == null){
      log.warn("Message Send Error: Socket is null");
      return;
    }
    if(dataOutputStream == null){
      log.warn("Message Send Error: Stream not initialized");
      return;
    }
    try {
      dataOutputStream.writeUTF(msg);
      dataOutputStream.flush();  // refreshing cache
      log.info("Message Send Successfully: {}", msg);
    } catch (IOException e) {
      log.error("Message Send Error", e);
    }
  }

  /**
   * Encapsulating message reading util using UTF-8 encoding
   *
   * @return message received in UTF-8 Binary String,
   * or null if error occurs
   */
  public String receiveMessage(){
    if(this.socket == null){
      log.warn("Message Receive Error: Socket is null");
    }
    if(dataInputStream == null){
      log.warn("Message Receive Error: InputStream is null");
    }
    try {
      log.info("SocketHandler Receiving...");
      return dataInputStream.readUTF();
    } catch (IOException e) {
      log.error("Message Receive Error", e);
      return null;
    }
  }

  /**
   * Closing socket of the SocketHandler
   */
  public void close(){
    try {
      if (socket != null) socket.close();
      log.info("SocketHandler Closed Successfully");
    } catch (IOException e) {
      log.error("SocketHandler Closed Error" , e);
    }
  }

}