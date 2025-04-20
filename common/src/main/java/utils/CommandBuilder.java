package utils;


import enumeration.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandBuilder {

  private static final Logger log = LoggerFactory.getLogger(CommandBuilder.class);


  /**
   * Encapsulating standard command with params
   *
   * @param commandType enum representing command type
   * @param params      changeable params adding to the command
   * @return standard command
   */
  public static String buildCommand(CommandType commandType, String... params) {
    String command = commandType.getMessage() + " " + String.join(" ", params);
    log.info("Command built successfully: {}", command);
    return command;
  }
}
