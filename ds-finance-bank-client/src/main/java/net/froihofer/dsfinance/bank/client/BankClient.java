package net.froihofer.dsfinance.bank.client;

import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import at.ac.csdc23vz_02.common.BankServer;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import net.froihofer.dsfinance.bank.client.utils.MessageType;
import net.froihofer.dsfinance.bank.client.utils.UserInterface;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * initial BankClient for User Communication
 */
public class BankClient {

  /**
   * Logger
   */
  private static Logger log = LoggerFactory.getLogger(BankClient.class);

  /**
   * BankServer Object to be communicated with
   */
  private static BankServer bankServer;

  /**
   * to be used UserInterface Object
   */
  UserInterface userInterface;

  /**
   * method for performing an RMI lookup
   * @param userName Username for the Authentication at the BankServer
   * @param userPassword Password for the Authentication at the BankServer
   * @return returns a boolean: true=successful authentication / false=unsuccessful authentication
   */
  public boolean getRmiProxy(String userName, String userPassword) {
    AuthCallbackHandler.setUsername(userName);
    AuthCallbackHandler.setPassword(userPassword);
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
    try {
      WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
      bankServer = jndiHelper.lookup("BankServer", BankServer.class);
      return true;
    }
    catch (NamingException e) {
      //This line is not used since the Naming Exception gets thrown when the user is not logged in correctly!
      //log.error("Failed to initialize InitialContext.",e);
      return false;
    }
  }

  /**
   * method to initialise the UserInterface and set all the credentials
   * @throws BankServerException throws an exception if the UserInterface init()-Method throws an exception
   */
  public void run() throws BankServerException {
    userInterface = new UserInterface();
    List<String> credentials = userInterface.startLogin();
    while(!getRmiProxy(credentials.get(0), credentials.get(1))) {
      userInterface.showResponseMessage("Wrong Login Credentials!", MessageType.ERROR);
      credentials = userInterface.startLogin();
    }
    userInterface.init(bankServer, credentials, this);
  }

  /**
   * main method to start the client
   * @param args main
   * @throws BankServerException inherited from client.run()
   */
  public static void main(String[] args) throws BankServerException {
    BankClient client = new BankClient();
    client.run();
  }
}
